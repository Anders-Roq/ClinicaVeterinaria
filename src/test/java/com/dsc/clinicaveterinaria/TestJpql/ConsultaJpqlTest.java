package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Consulta;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultaJpqlTest extends BaseTest {

    @Test
    public void consultasPorPeriodoTest() {

        String jpql = "SELECT c FROM Consulta c "
                + "WHERE FUNCTION('DATE', c.dataConsulta) BETWEEN :inicio AND :fim "
                + "ORDER BY c.dataConsulta";

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);
        query.setParameter("inicio", LocalDate.of(2024, 1, 1));
        query.setParameter("fim", LocalDate.of(2024, 12, 31));

        List<Consulta> consultas = query.getResultList();

        assertNotNull(consultas);
        assertFalse(consultas.isEmpty());
    }

    @Test
    public void consultaSemPrescricaoTest() {

        String jpql = """
                SELECT COUNT(c)
                FROM Consulta c
                WHERE NOT EXISTS (
                    SELECT p FROM Prescricao p
                    WHERE p.consulta = c
                )
                """;

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);

        Long qtd = query.getSingleResult();

        // De acordo com o dataset: 6 consultas sem prescrição
        assertEquals(6L, qtd);
    }

    @Test
    public void consultaComPrescricaoTest() {

        String jpql = """
                SELECT DISTINCT c
                FROM Consulta c
                WHERE EXISTS (
                    SELECT p FROM Prescricao p
                    WHERE p.consulta = c
                )
                """;

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);

        List<Consulta> consultas = query.getResultList();

        assertNotNull(consultas);
        assertTrue(consultas.size() >= 1);
    }

    @Test
    public void consultaComJoinFetchAnimalClienteVeterinarioTest() {
        //JOIN FETCH em várias associações: Animal, Cliente, Veterinário
        String jpql = """
                SELECT DISTINCT c
                FROM Consulta c
                JOIN FETCH c.animal a
                JOIN FETCH a.cliente cli
                JOIN FETCH c.veterinario v
                """;

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);
        List<Consulta> consultas = query.getResultList();

        assertNotNull(consultas);
        assertFalse(consultas.isEmpty());

        Consulta c = consultas.get(0);
        assertNotNull(c.getAnimal());
        assertNotNull(c.getAnimal().getCliente());
        assertNotNull(c.getVeterinario());
    }

    @Test
    public void contarConsultasPorVeterinarioTest() {

        String jpql = """
                SELECT c.veterinario.nome, COUNT(c)
                FROM Consulta c
                GROUP BY c.veterinario.nome
                ORDER BY COUNT(c) DESC
                """;

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);

        List<Object[]> resultados = query.getResultList();

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());

        Object[] primeiro = resultados.get(0);
        String nomeVet = (String) primeiro[0];
        Long qtd = (Long) primeiro[1];

        assertNotNull(nomeVet);
        assertNotNull(qtd);
        assertTrue(qtd >= 1);
    }

    /**
     * Uso de IN com subquery para buscar consultas de clientes que têm mais de
     * um animal.
     */
    @Test
    public void consultasDeClientesComMaisDeUmAnimalTest() {

        String jpql = """
                SELECT c
                FROM Consulta c
                WHERE c.animal.cliente IN (
                    SELECT cli
                    FROM Cliente cli
                    WHERE SIZE(cli.animais) > 1
                )
                """;

        TypedQuery<Consulta> query = em.createQuery(jpql, Consulta.class);

        List<Consulta> consultas = query.getResultList();

        assertNotNull(consultas);
        assertTrue(consultas.size() >= 1);
    }
}

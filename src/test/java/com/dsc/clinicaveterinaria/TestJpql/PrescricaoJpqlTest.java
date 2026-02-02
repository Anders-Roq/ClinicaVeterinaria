package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrescricaoJpqlTest extends BaseTest {

    @Test
    public void medicamentosPermanentesTeste() {

        Query query = em.createQuery(
                "SELECT p.medicamento, p.frequencia "
                + "FROM Prescricao p "
                + "WHERE UPPER(p.duracao) LIKE '%PERMANENTE%' "
                + "ORDER BY p.medicamento"
        );

        List<Object[]> resultados = query.getResultList();

        assertNotNull(resultados);
        assertEquals(3, resultados.size());

        assertEquals("Ração especializada", resultados.get(0)[0]);
        assertEquals("3 vez ao dia", resultados.get(0)[1]);

        assertEquals("Ração hipoalergênica", resultados.get(1)[0]);
        assertEquals("2 vezes ao dia", resultados.get(1)[1]);

        assertEquals("Suplementos", resultados.get(2)[0]);
        assertEquals("1 vez ao dia", resultados.get(2)[1]);
    }

    @Test
    public void medicamentosMaisPrescritosTeste() {

        String jpql = """
                SELECT p.medicamento, COUNT(p)
                FROM Prescricao p
                GROUP BY p.medicamento
                HAVING COUNT(p) >= 2
                ORDER BY COUNT(p) DESC
                """;

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);

        List<Object[]> resultados = query.getResultList();

        assertNotNull(resultados);
        assertTrue(resultados.size() >= 1);

        for (Object[] linha : resultados) {
            String medicamento = (String) linha[0];
            Long qtd = (Long) linha[1];

            assertNotNull(medicamento);
            assertTrue(qtd >= 2);
        }
    }

    @Test
    public void prescricoesDeClienteEspecificoTest() {

        String jpql = """
                SELECT p
                FROM Prescricao p
                WHERE p.consulta.animal.cliente.nome = :nomeCliente
                """;

        TypedQuery<com.dsc.clinicaveterinaria.Prescricao> query
                = em.createQuery(jpql, com.dsc.clinicaveterinaria.Prescricao.class);

        query.setParameter("nomeCliente", "José");

        List<com.dsc.clinicaveterinaria.Prescricao> prescricoes = query.getResultList();

        assertNotNull(prescricoes);
        // Pelo menos 1 prescrição para consultas dos animais de José
        assertTrue(prescricoes.size() >= 1);
    }
}

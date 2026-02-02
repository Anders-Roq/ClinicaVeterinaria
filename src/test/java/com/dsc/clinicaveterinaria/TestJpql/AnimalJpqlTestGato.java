package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Animal;
import com.dsc.clinicaveterinaria.Gato;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalJpqlTestGato extends BaseTest {

    @Test
    public void GatoQuantidadePorSexoTeste() {

        char sexo = 'F';
        //Count
        String jpqlContar = "SELECT COUNT(g) FROM Gato g WHERE g.sexo = :sexo";
        TypedQuery<Long> queryContar = em.createQuery(jpqlContar, Long.class)
                .setParameter("sexo", sexo);

        Long quantidade = queryContar.getSingleResult();

        assertEquals(3L, quantidade);
    }

    @Test
    public void GatoPorClienteTeste() {

        String jpql = "SELECT a FROM Animal a JOIN a.cliente c WHERE c.nome = :nomeCliente";
        TypedQuery<Animal> query = em.createQuery(jpql, Animal.class);
        query.setParameter("nomeCliente", "Carlos");
        List<Animal> animais = query.getResultList();

        // Carlos possui 3 animais
        assertEquals(3, animais.size());
    }

    @Test
    public void GatoIdadeMenorQueDoisAnosTeste() {

        String jpql = """
                SELECT g.nome
                FROM Gato g
                WHERE FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', g.dataNascimento) < 2
                ORDER BY g.dataNascimento DESC
                """;

        TypedQuery<String> query = em.createQuery(jpql, String.class);
        List<String> nomes = query.getResultList();

        // Apenas Josephine (2025) tem idade < 2
        assertEquals(1, nomes.size());
    }

    @Test
    public void GatosNascidosEmIntervaloDeDatasTeste() {
        //BETWEEN em data de nascimento de gatos.
        String jpql = "SELECT g FROM Gato g WHERE g.dataNascimento BETWEEN :inicio AND :fim ORDER BY g.dataNascimento";

        LocalDate inicio = LocalDate.of(2020, 1, 1);
        LocalDate fim = LocalDate.of(2022, 12, 31);

        TypedQuery<Gato> query = em.createQuery(jpql, Gato.class);
        query.setParameter("inicio", inicio);
        query.setParameter("fim", fim);

        List<Gato> gatos = query.getResultList();

        assertNotNull(gatos);
        // de acordo com o dataset, há alguns gatos nesse intervalo
        assertTrue(gatos.size() >= 1);
    }

    @Test
    public void GatosComNomeContendoLetraTeste() {
        //LIKE, LOWER, ORDER BY, CONCAT
        String letra = "a";

        String jpql = "SELECT g FROM Gato g "
                + "WHERE LOWER(g.nome) LIKE CONCAT('%', :letra, '%') "
                + "ORDER BY g.nome";

        TypedQuery<Gato> query = em.createQuery(jpql, Gato.class);
        query.setParameter("letra", letra);

        List<Gato> gatos = query.getResultList();

        assertNotNull(gatos);
        assertFalse(gatos.isEmpty());
        assertTrue(gatos.stream().allMatch(
                g -> g.getNome().toLowerCase().contains("a")
        ));
    }
}

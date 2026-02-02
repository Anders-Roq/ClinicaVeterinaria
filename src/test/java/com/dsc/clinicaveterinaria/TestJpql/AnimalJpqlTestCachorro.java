package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Cachorro;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalJpqlTestCachorro extends BaseTest {

    /**
     * Filtro simples por atributo.
     */
    @Test
    public void cachorroGrandeTeste() {
        String porte = "Grande";

        String jpql = "SELECT c FROM Cachorro c WHERE c.porte = :porte";

        TypedQuery<Cachorro> queryCachorro = em.createQuery(jpql, Cachorro.class)
                .setParameter("porte", porte);

        List<Cachorro> cachorroGrande = queryCachorro.getResultList();

        assertEquals(3, cachorroGrande.size());
    }

    @Test
    public void cachorroAdestradoTeste() {

        String jpql = "SELECT c FROM Cachorro c WHERE c.adestrado = true";

        TypedQuery<Cachorro> query = em.createQuery(jpql, Cachorro.class);
        List<Cachorro> cachorrosAdestrados = query.getResultList();

        assertNotNull(cachorrosAdestrados);
        // No dataset há 5 cachorros adestrados
        assertEquals(5, cachorrosAdestrados.size());
    }

    @Test
    public void cachorroIdadeMaiorQueDezTeste() {

        String jpql = """
                SELECT c.nome
                FROM Cachorro c
                WHERE FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', c.dataNascimento) > 10
                ORDER BY c.dataNascimento
                """;
        TypedQuery<String> query = em.createQuery(jpql, String.class);
        List<String> nomes = query.getResultList();

        assertEquals(2, nomes.size());
    }

    @Test
    public void cachorrosNascidosEntreAnosTeste() {

        String jpql = "SELECT c FROM Cachorro c WHERE c.dataNascimento BETWEEN :inicio AND :fim";

        LocalDate inicio = LocalDate.of(2018, 1, 1);
        LocalDate fim = LocalDate.of(2022, 12, 31);

        TypedQuery<Cachorro> query = em.createQuery(jpql, Cachorro.class);
        query.setParameter("inicio", inicio);
        query.setParameter("fim", fim);

        List<Cachorro> cachorros = query.getResultList();

        assertNotNull(cachorros);
        assertTrue(cachorros.size() >= 1);
    }

    @Test
    public void cachorrosComNomeCurtoTeste() {

        String jpql = "SELECT c FROM Cachorro c WHERE LENGTH(c.nome) <= 4 ORDER BY c.nome";

        TypedQuery<Cachorro> query = em.createQuery(jpql, Cachorro.class);
        List<Cachorro> cachorros = query.getResultList();

        assertNotNull(cachorros);
        assertFalse(cachorros.isEmpty());
        assertTrue(cachorros.stream().allMatch(
                c -> c.getNome().length() <= 4
        ));
    }
}

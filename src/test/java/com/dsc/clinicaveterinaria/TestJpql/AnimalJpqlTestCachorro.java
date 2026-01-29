package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Cachorro;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalJpqlTestCachorro extends BaseTest {

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

        assertEquals(5, cachorrosAdestrados.size());

    }

    @Test
    public void cachorroIdadeTeste() {

        String jpql = """
            SELECT c.nome, c.dataNascimento, 
                   FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', c.dataNascimento) as idade
            FROM Cachorro c 
            WHERE FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', c.dataNascimento) > 10
            ORDER BY idade
            """;
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        List<Long> resultados = query.getResultList();

        assertEquals(2L, resultados.size());

    }
}

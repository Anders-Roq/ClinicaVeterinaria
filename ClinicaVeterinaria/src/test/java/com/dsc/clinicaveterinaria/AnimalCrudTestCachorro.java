package com.dsc.clinicaveterinaria;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class AnimalCrudTestCachorro extends BaseTest {

    @Test
    @SuppressWarnings("UnusedAssignment")
    public void atualizaCachorroMerge() {

        String novoNome = "Matador";
        String novaRaca = "Great Dane";
        Cachorro cachorro = em.find(Cachorro.class, 2L);

        cachorro.setNome(novoNome);
        cachorro.setRaca(novaRaca);

        em.clear();

        cachorro = em.merge(cachorro);

        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        cachorro = em.find(Cachorro.class, 2L, props);

        assertEquals(novoNome, cachorro.getNome());
        assertEquals(novaRaca, cachorro.getRaca());
    }

    @Test
    public void atualizarCachorro() {

        String novoNome = "Gangster";
        String novaRaca = "Pincher";
        Long id = 4L;
        Cachorro cachorro = em.find(Cachorro.class, id);
        cachorro.setNome(novoNome);
        cachorro.setRaca(novaRaca);
        em.flush();
        String jpql = "SELECT c FROM Cachorro c WHERE c = ?1";
        TypedQuery<Cachorro> query = em.createQuery(jpql, Cachorro.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1,cachorro);
        cachorro = query.getSingleResult();
        assertEquals(novoNome, cachorro.getNome());
        assertEquals(novaRaca, cachorro.getRaca());
    }

    @Test
    public void removerCachorro() {
        Long id = 6L;
        Cachorro cachorro = em.find(Cachorro.class, id);
        em.remove(cachorro);
        cachorro = em.find(Cachorro.class, id);
        assertNull(cachorro);

    }
}

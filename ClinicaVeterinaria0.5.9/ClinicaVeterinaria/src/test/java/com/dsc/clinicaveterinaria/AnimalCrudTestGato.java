package com.dsc.clinicaveterinaria;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class AnimalCrudTestGato extends BaseTest {

    @Test
    @SuppressWarnings("UnusedAssignment")
    public void atualizaGatoMerge() {

        String novoNome = "Maracuja";
        String novaRaca = "Maine Coon";
        Gato gato = em.find(Gato.class, 1L);

        gato.setNome(novoNome);
        gato.setRaca(novaRaca);

        em.clear();

        gato = em.merge(gato);

        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        gato = em.find(Gato.class, 1L, props);

        assertEquals(novoNome, gato.getNome());
        assertEquals(novaRaca, gato.getRaca());
    }

    @Test
    public void atualizarGato() {

        String novoNome = "Kiwi";
        String novaRaca = "Persa";
        Long id = 3L;
        Gato gato = em.find(Gato.class, id);
        gato.setNome(novoNome);
        gato.setRaca(novaRaca);
        em.flush();
        String jpql = "SELECT g FROM Gato g WHERE g = ?1";
        TypedQuery<Gato> query = em.createQuery(jpql, Gato.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, gato);
        gato = query.getSingleResult();
        assertEquals(novoNome, gato.getNome());
        assertEquals(novaRaca, gato.getRaca());
    }
    
    @Test
    public void removerGato() {
        Long id = 7L;
        Gato gato = em.find(Gato.class, id);
        em.remove(gato);
        gato = em.find(Gato.class, id);
        assertNull(gato);

    }

}

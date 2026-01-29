package com.dsc.clinicaveterinaria.TestCrud;

import com.dsc.clinicaveterinaria.Test.BaseTest;
import com.dsc.clinicaveterinaria.Veterinario;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class ConsultaCrudTest extends BaseTest {

    @Test
    @SuppressWarnings("UnusedAssignment")
    public void atualizarConsultaMerge() {

        String novoCrmv = "CRMV-69696";
        String telefone = "(81) 990901010";
        Veterinario veterinario = em.find(Veterinario.class, 1L);
        veterinario.setCrmv(novoCrmv);
        veterinario.setTelefone(telefone);
        em.clear();
        veterinario = (Veterinario) em.merge(veterinario);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        veterinario = em.find(Veterinario.class, 1L, properties);
        assertEquals(novoCrmv, veterinario.getCrmv());
        assertEquals(telefone, veterinario.getTelefone());
    }

    @Test
    public void atualizarConsulta() {

        String novoCrmv = "CRMV-55555";
        String telefone = "(81) 990902020";
        Long id = 2L;
        Veterinario veterinario = em.find(Veterinario.class, id);
        veterinario.setCrmv(novoCrmv);
        veterinario.setTelefone(telefone);
        em.flush();
        String jpql = "SELECT v FROM Veterinario v WHERE v = ?1";
        TypedQuery<Veterinario> query = em.createQuery(jpql, Veterinario.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, veterinario);
        veterinario = query.getSingleResult();
        assertEquals(novoCrmv, veterinario.getCrmv());
        assertEquals(telefone, veterinario.getTelefone());
    }

    @Test

    public void removerConsulta() {
        Long id = 3L;
        Veterinario veterinario = em.find(Veterinario.class, id);
        em.remove(veterinario);
        veterinario = em.find(Veterinario.class, id);
        assertNull(veterinario);

    }

}

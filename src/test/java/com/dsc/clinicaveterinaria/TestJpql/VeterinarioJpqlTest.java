package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Veterinario;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VeterinarioJpqlTest extends BaseTest {

   
    @Test
    public void veterinarioPorEspecialidadeTest() {

        TypedQuery<Object[]> query = em.createQuery(
                "SELECT v.nome, COUNT(c) " +
                        "FROM Veterinario v JOIN v.consultas c " +
                        "GROUP BY v.nome " +
                        "ORDER BY COUNT(c) DESC",
                Object[].class
        );

        List<Object[]> resultados = query.getResultList();

        assertNotNull(resultados);

        // Dataset contém 6 veterinários no total
        assertTrue(resultados.size() <= 6);

        assertEquals("Dr. João Silva", resultados.get(0)[0]);
        assertEquals(5L, resultados.get(0)[1]);
    }

    
    @Test
    public void veterinarioSemConsultaTest() {

        TypedQuery<Veterinario> query = em.createQuery(
                "SELECT v FROM Veterinario v WHERE SIZE(v.consultas) = 0",
                Veterinario.class
        );

        List<Veterinario> vets = query.getResultList();

        assertNotNull(vets);
        // No dataset há 2 veterinários sem consulta
        assertEquals(2, vets.size());
    }

   
    @Test
    public void veterinarioPorCrmvTest() {

        TypedQuery<Veterinario> query = em.createQuery(
                "SELECT v FROM Veterinario v WHERE v.crmv = :crmv",
                Veterinario.class
        );
        query.setParameter("crmv", "CRMV-12345");

        List<Veterinario> vets = query.getResultList();

        assertNotNull(vets);
        assertFalse(vets.isEmpty());
        assertEquals("CRMV-12345", vets.get(0).getCrmv());
    }

   
    @Test
    public void veterinariosComConsultaEmPeriodo() {

        TypedQuery<Veterinario> query = em.createQuery(
                "SELECT v " +
                        "FROM Veterinario v " +
                        "WHERE v IN (" +
                        "   SELECT c.veterinario " +
                        "   FROM Consulta c " +
                        "   WHERE FUNCTION('DATE', c.dataConsulta) BETWEEN :inicio AND :fim" +
                        ") " +
                        "ORDER BY v.nome",
                Veterinario.class
        );

        query.setParameter("inicio", "2024-01-01");
        query.setParameter("fim", "2024-12-31");

        List<Veterinario> vets = query.getResultList();

        assertNotNull(vets);
        assertFalse(vets.isEmpty());
    }

   
    @Test
    public void contarVeterinariosTest() {

        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(v) FROM Veterinario v",
                Long.class
        );

        Long total = query.getSingleResult();

        assertNotNull(total);
        assertEquals(6L, total);
    }

   
    @Test
    public void veterinarioComMaisConsultasTest() {

        TypedQuery<Veterinario> query = em.createQuery(
                "SELECT v " +
                        "FROM Veterinario v " +
                        "WHERE SIZE(v.consultas) = (" +
                        "   SELECT MAX(SIZE(v2.consultas)) FROM Veterinario v2" +
                        ")",
                Veterinario.class
        );

        List<Veterinario> vets = query.getResultList();

        assertNotNull(vets);
        assertFalse(vets.isEmpty());
        assertTrue(
                vets.stream().anyMatch(v -> "Dr. João Silva".equals(v.getNome()))
        );
    }

    

}

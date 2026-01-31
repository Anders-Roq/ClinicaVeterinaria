package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class VeterinarioJpqlTest extends BaseTest {

    @Test
    public void veterinarioPorEspecialidadeTest() {

        TypedQuery<Object[]> query = em.createQuery(
                "SELECT v.nome, COUNT(c) "
                + "FROM Veterinario v JOIN v.consultas c "
                + "GROUP BY v.nome "
                + "ORDER BY COUNT(c) DESC",
                Object[].class
        );

        List<Object[]> resultados = query.getResultList();

        assertNotNull(resultados);

        assertEquals(4, resultados.size());

        assertEquals("Dr. João Silva", resultados.get(0)[0]);
        assertEquals(5L, resultados.get(0)[1]);
    }

    @Test
    public void veterinarioSemConsultaTest() {
        
        Query query = em.createQuery("");
          
    }
    
    
    
}

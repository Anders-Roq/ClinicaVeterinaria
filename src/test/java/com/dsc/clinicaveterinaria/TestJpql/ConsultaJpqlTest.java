package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class ConsultaJpqlTest extends BaseTest {

    @Test
    public void consultaSemPrescricaoTest() {

        String jpql = "SELECT COUNT(c) FROM Consulta c WHERE "
                + "NOT EXISTS (SELECT 1 FROM Prescricao p WHERE p.consulta = c)";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);

        Long resultados = query.getSingleResult();

        assertNotNull(resultados);
        assertEquals(6L, resultados);

    }

    @Test
    public void consultaMaisCaraTest() {

        String jpql = "SELECT p.valorTotal FROM Consulta c JOIN c.pagamento p "
                + "WHERE p.valorTotal = (SELECT MAX(p2.valorTotal) FROM Pagamento p2)";

        TypedQuery<Double> query = em.createQuery(jpql, Double.class);
        Double resultado = query.getSingleResult();

        assertEquals(510.00, resultado, 0.01);

    }

    @Test
    public void consultaComMaisPrescricaoTest() {
        
        Query query=em.createQuery("SELECT c.motivo, COUNT(p) "
                + "FROM Consulta c LEFT JOIN c.prescricoes p "
                + "GROUP BY c.motivo ORDER BY "
                + "COUNT(p) DESC");
        
        List<Object[]> consultas = query.getResultList();
        
        assertNotNull(consultas);
        assertEquals(3, consultas.size());
        

    }

}

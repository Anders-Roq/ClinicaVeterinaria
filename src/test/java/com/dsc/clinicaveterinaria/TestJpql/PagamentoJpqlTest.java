package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PagamentoJpqlTest extends BaseTest {

    @Test
    public void mediaPagamentosPorMetodoTest() {
        String metodo = "PIX";

        String jpqlAvg = "SELECT AVG(p.valorTotal) FROM Pagamento p "
                + "WHERE LOWER(p.metodoPagamento) = LOWER(:metodo)";

        TypedQuery<Double> queryValor = em.createQuery(jpqlAvg, Double.class)
                .setParameter("metodo", metodo);

        Double mediaMetodo = queryValor.getSingleResult();

        assertNotNull(mediaMetodo);
        assertEquals(145, mediaMetodo);
    }

    @Test
    public void somaPagamentosTest() {
        //SUM
        String jpqlSum = "SELECT SUM(p.valorTotal) FROM Pagamento p";
        TypedQuery<Double> querySoma = em.createQuery(jpqlSum, Double.class);

        double somaTotal = querySoma.getSingleResult();

        assertNotNull(somaTotal);
        // conforme dataset
        assertEquals(2580.00, somaTotal);
    }

    @Test
    public void maiorPagamentoPorIntervaloTest() {
        //MAX BETWEEN
        String jpqlBetween = "SELECT MAX(p.valorTotal) "
                + "FROM Pagamento p "
                + "WHERE p.dataPagamento BETWEEN :inicio AND :fim";

        TypedQuery<Double> queryEntre = em.createQuery(jpqlBetween, Double.class);
        queryEntre.setParameter("inicio", LocalDateTime.of(2024, 1, 15, 10, 30, 0));
        queryEntre.setParameter("fim", LocalDateTime.of(2024, 7, 29, 16, 10, 0));

        double maiorValor = queryEntre.getSingleResult();

        assertEquals(510.00, maiorValor);
    }

    @Test
    public void menorPagamentoRealizadoTest() {
        //MIN
        String jpqlMin = "SELECT MIN(p.valorTotal) FROM Pagamento p";
        TypedQuery<Double> queryMinimo = em.createQuery(jpqlMin, Double.class);

        Double menorPagamento = queryMinimo.getSingleResult();

        assertNotNull(menorPagamento);
        assertEquals(110.00, menorPagamento);
    }

    @Test
    public void totalPorMetodoPagamentoTest() {
        //GROUPBY HAVING
        String jpql = """
                SELECT p.metodoPagamento, SUM(p.valorTotal)
                FROM Pagamento p
                GROUP BY p.metodoPagamento
                HAVING SUM(p.valorTotal) > 200
                ORDER BY SUM(p.valorTotal) DESC
                """;

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        var resultados = query.getResultList();

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());

        for (Object[] linha : resultados) {
            String metodo = (String) linha[0];
            Double soma = (Double) linha[1];
            assertNotNull(metodo);
            assertTrue(soma > 200);
        }
    }
}

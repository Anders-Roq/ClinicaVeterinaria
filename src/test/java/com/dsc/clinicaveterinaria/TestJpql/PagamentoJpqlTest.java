package com.dsc.clinicaveterinaria.TestJpql;

import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class PagamentoJpqlTest extends BaseTest {

    @Test
    public void mediaPagamentos() {
        String metodo = "PIX";

        String jpqlAvg = "SELECT AVG(p.valorTotal) FROM Pagamento p WHERE p.metodoPagamento = :metodo";
        TypedQuery<Double> queryValor = em.createQuery(jpqlAvg, Double.class)
                .setParameter("metodo", metodo);

        Double mediaMetodo = queryValor.getSingleResult();

        assertNotNull(mediaMetodo);


        assertEquals(145.00, mediaMetodo, 0.01);

    }
    
    @Test
    public void somaPagamentos(){
        
        String jpqlSum = "SELECT SUM (p.valorTotal) FROM Pagamento p";
        TypedQuery<Double> querySoma = em.createQuery(jpqlSum, Double.class);
        
        double somaTotal = querySoma.getSingleResult();

        assertNotNull(somaTotal);
        
        assertEquals(2580.00, somaTotal, 0.01);
        
    }
    
    @Test
    public void maiorPagamentoPorIntervalo(){
        
        String jpqlBetween = "SELECT MAX(p.valorTotal) FROM Pagamento p WHERE p.dataPagamento BETWEEN :inicio AND :fim";
              
        TypedQuery<Double> queryEntre = em.createQuery(jpqlBetween, Double.class);
        queryEntre.setParameter("inicio", LocalDateTime.of(2024,01,15,10,30,00));
        queryEntre.setParameter("fim", LocalDateTime.of(2024,07,29,16,10,00));
        
        double maiorValor = queryEntre.getSingleResult();

        
        assertEquals(510.00, maiorValor);
        
    }
    
    @Test
    public void menorPagamentoRealizado(){
    
        String jpqlMin = "SELECT MIN (p.valorTotal) FROM Pagamento p";
        TypedQuery<Double> queryMinimo = em.createQuery(jpqlMin, Double.class);
        
        Double menorPagamento = queryMinimo.getSingleResult();
        
        assertNotNull(menorPagamento);
        
        assertEquals(110.00,menorPagamento,0.01);
        
    }

}

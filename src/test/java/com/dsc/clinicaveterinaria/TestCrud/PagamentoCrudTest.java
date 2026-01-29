
package com.dsc.clinicaveterinaria.TestCrud;

import com.dsc.clinicaveterinaria.Pagamento;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;


public class PagamentoCrudTest extends BaseTest{
    
    @Test
    @SuppressWarnings("UnusedAssignment")
    public void atualizarPagamentoMerge() {

        Double novoValor = 200.00;
        String novoMetodo = "Cartão";
        Pagamento pagamento = em.find(Pagamento.class, 1L);

        pagamento.setValorTotal(novoValor);
        pagamento.setMetodoPagamento(novoMetodo);

        em.clear(); 

        pagamento = em.merge(pagamento); 

        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        pagamento = em.find(Pagamento.class, 1L, props);

        assertEquals(novoValor, pagamento.getValorTotal(), 0.001);
        assertEquals(novoMetodo, pagamento.getMetodoPagamento());
    }

    @Test
    public void atualizarPagamento() {

        Double novoValor = 155.35;
        String novoMetodo = "CARTAO";

        Pagamento pagamento = em.find(Pagamento.class, 2L);

        pagamento.setValorTotal(novoValor);
        pagamento.setMetodoPagamento(novoMetodo);

        em.flush(); 

        String jpql = "SELECT p FROM Pagamento p WHERE p = ?1";
        TypedQuery<Pagamento> q = em.createQuery(jpql, Pagamento.class);
        q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        q.setParameter(1, pagamento);
        pagamento = q.getSingleResult();

        assertEquals(novoValor, pagamento.getValorTotal(), 0.001);
        assertEquals(novoMetodo, pagamento.getMetodoPagamento());
    }

    @Test
    public void removerPagamento() {

        Long id = 3L;

        Pagamento pagamento = em.find(Pagamento.class, id);
        assertNotNull(pagamento);

        em.remove(pagamento);

        pagamento = em.find(Pagamento.class, id);

        assertNull(pagamento);
    }

}

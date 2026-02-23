package com.dsc.clinicaveterinaria.TestCrud;

import com.dsc.clinicaveterinaria.Cliente;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class ClientCrudTest extends BaseTest {

    @Test
    @SuppressWarnings("UnusedAssignment")
    public void atualizarClienteMerge() {

        String novoEmail = "fulano_de_tal2@gmail.com";
        String telefone = "990901010";
        Cliente cliente = em.find(Cliente.class, 1L);
        cliente.setEmail(novoEmail);
        cliente.setTelefone(telefone);
        em.clear();
        cliente = (Cliente) em.merge(cliente);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        cliente = em.find(Cliente.class, 1L, properties);
        assertEquals(novoEmail, cliente.getEmail());
        assertEquals(telefone, cliente.getTelefone());
    }

    @Test
    public void atualizarCliente() {

        String novoEmail = "cicrano_de_tal@gmail.com";
        String telefone = "984844599";
        Long id = 2L;
        Cliente cliente = em.find(Cliente.class, id);
        cliente.setEmail(novoEmail);
        cliente.setTelefone(telefone);
        em.flush();
        String jpql = "SELECT c FROM Cliente c WHERE c = ?1";
        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, cliente);
        cliente = query.getSingleResult();
        assertEquals(novoEmail, cliente.getEmail());
        assertEquals(telefone, cliente.getTelefone());
    }

    @Test
    public void removerCliente() {
        Long id = 5L;
        Cliente cliente = em.find(Cliente.class, id);
        em.remove(cliente);
        cliente = em.find(Cliente.class, id);
        assertNull(cliente);

    }

}

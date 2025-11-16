package com.dsc.clinicaveterinaria;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest extends BaseTest {

    @Test
    public void testPersistirCliente() {
        Cliente cliente = new Cliente(
                "Novo Cliente Teste",
                "054.218.290-49",
                "123456789",
                "teste@mail.com",
                "Rua de Teste, 123"
        );

        em.persist(cliente);
        em.flush(); // Força o insert e a geração do ID

        assertNotNull(cliente.getIdCliente());
    }

    @Test
    public void testConsultarClientePorId() {
        // ID 1L ("José") foi carregado pelo DBUnit
       Cliente cliente = em.find(Cliente.class, 1L);
        
        
        assertNotNull(cliente);
        assertEquals("José", cliente.getNome());
        assertEquals("925.225.900-73", cliente.getCpf());
        assertEquals("987654321", cliente.getTelefone());
        assertEquals("jose@mail.com", cliente.getEmail());
        assertEquals("Rua A, 100", cliente.getEndereco());
        // Verifica se o relacionamento de animais foi carregado
        assertNotNull(cliente.getAnimais());
        assertEquals(2, cliente.getAnimais().size()); // José tem "Fofinho" e "Rex"
    }
}

package com.dsc.clinicaveterinaria.Test;

import com.dsc.clinicaveterinaria.Veterinario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VeterinarioTest extends BaseTest {

    @Test
    public void testPersistirVeterinario() {
        Veterinario veterinario = new Veterinario(
                "Novo Veterinario Teste", 
                "CRM-PE-99999", 
                "Clínica Geral", 
                "121212121", 
                "vet.vet@vet.com"
        );
        
        em.persist(veterinario);
        em.flush(); // Força o insert e a geração do ID

        assertNotNull(veterinario.getIdVeterinario());
    }

    @Test
    public void testConsultarVeterinarioPorId() {
        // ID 1L ("Quico") foi carregado pelo DBUnit
        Veterinario veterinario = em.find(Veterinario.class, 1L);
        
        assertNotNull(veterinario);
        assertEquals("Dr. João Silva", veterinario.getNome());
        assertEquals("CRM-PE-12345", veterinario.getCrmv());
        // Verifica se veterinario tem alguma consulta
        assertNotNull(veterinario.getConsultas());
        assertEquals(5, veterinario.getConsultas().size()); // nenhuma consulta marcada
    }
}
package com.dsc.clinicaveterinaria.Test;

import com.dsc.clinicaveterinaria.Animal;
import com.dsc.clinicaveterinaria.Consulta;
import com.dsc.clinicaveterinaria.Pagamento;
import com.dsc.clinicaveterinaria.Veterinario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class PagamentoTest extends BaseTest {

    @Test
    public void testPersistirPagamento() {

        // Criar consulta nova (sem pagamento associado)
        Animal animal = em.find(Animal.class, 1L);
        Veterinario vet = em.find(Veterinario.class, 1L);

        Consulta consulta = new Consulta(
                LocalDateTime.of(2025, 1, 10, 14, 0),
                "Retorno pós-cirurgia",
                "Recuperação evoluindo bem",
                animal,
                vet
        );

        em.persist(consulta);
        em.flush();

        Pagamento pagamento = new Pagamento(
                320.00,
                "Cartão de Crédito",
                LocalDateTime.of(2025, 2, 1, 9, 45),
                consulta
        );

        em.persist(pagamento);
        em.flush();

        assertNotNull(pagamento.getId());
        assertEquals(320.00, pagamento.getValorTotal(), 0.001);
        assertEquals("Cartão de Crédito", pagamento.getMetodoPagamento());
        assertEquals(consulta.getIdConsulta(), pagamento.getConsulta().getIdConsulta());
    }

    @Test
    public void testConsultarPagamentoPorId() {

        Pagamento pagamento = em.find(Pagamento.class, 1L);

        assertNotNull(pagamento);
        assertEquals(150.00, pagamento.getValorTotal(), 0.001);
        assertEquals("Dinheiro", pagamento.getMetodoPagamento());
        assertEquals(LocalDateTime.of(2024, 1, 15, 10, 30, 0), pagamento.getDataPagamento());

        // Verifica relacionamento
        assertNotNull(pagamento.getConsulta());
        assertEquals(1L, pagamento.getConsulta().getIdConsulta());
    }

    @Test
    public void testPagamentoRelacionamentoConsulta() {

        Pagamento pagamento = em.find(Pagamento.class, 1L);
        Consulta consulta = pagamento.getConsulta();

        assertNotNull(consulta);
        assertEquals("Check-up anual", consulta.getMotivo());
        assertEquals("Animal saudável, vacinação em dia", consulta.getDiagnostico());

        assertNotNull(consulta.getAnimal());
        assertEquals("Fofinho", consulta.getAnimal().getNome());
    }

    @Test
    public void testPagamentoValoresDiferentes() {

        Pagamento pagamento2 = em.find(Pagamento.class, 2L);
        assertNotNull(pagamento2);
        assertEquals(200.00, pagamento2.getValorTotal(), 0.001);
        assertEquals("Cartão de Débito", pagamento2.getMetodoPagamento());

        Pagamento pagamento3 = em.find(Pagamento.class, 3L);
        assertNotNull(pagamento3);
        assertEquals(500.00, pagamento3.getValorTotal(), 0.001);
        assertEquals("Cartão de Crédito", pagamento3.getMetodoPagamento());
    }
}

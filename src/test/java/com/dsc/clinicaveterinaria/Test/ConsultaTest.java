package com.dsc.clinicaveterinaria.Test;

import com.dsc.clinicaveterinaria.Animal;
import com.dsc.clinicaveterinaria.Consulta;
import com.dsc.clinicaveterinaria.Pagamento;
import com.dsc.clinicaveterinaria.Prescricao;
import com.dsc.clinicaveterinaria.Veterinario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaTest extends BaseTest {

    @Test
    public void testPersistirConsulta() {
        Animal animal = em.find(Animal.class, 1L); // já populado no DBUnit
        Veterinario vet = em.find(Veterinario.class, 1L);

        Consulta consulta = new Consulta(
                LocalDateTime.of(2025, 1, 10, 14, 0),
                "Avaliação clínica",
                "Paciente apresentou melhora significativa.",
                animal,
                vet
        );

        em.persist(consulta);
        em.flush();

        assertNotNull(consulta.getIdConsulta());
        assertEquals("Avaliação clínica", consulta.getMotivo());
        assertEquals("Paciente apresentou melhora significativa.", consulta.getDiagnostico());
        assertNotNull(consulta.getAnimal());
        assertNotNull(consulta.getVeterinario());
    }

    @Test
    public void testConsultarConsultaPorId() {
        Consulta consulta = em.find(Consulta.class, 1L);

        assertNotNull(consulta);
        assertEquals(LocalDateTime.of(2024, 1, 15, 10, 0), consulta.getDataConsulta());
        assertEquals("Check-up anual", consulta.getMotivo());
        assertEquals("Animal saudável, vacinação em dia", consulta.getDiagnostico());

        assertNotNull(consulta.getAnimal());
        assertNotNull(consulta.getVeterinario());
    }

    @Test
    public void testConsultaRelacionamentoAnimal() {
        Consulta consulta = em.find(Consulta.class, 1L);
        Animal animal = consulta.getAnimal();

        assertNotNull(animal);
        assertEquals("Fofinho", animal.getNome());
        assertEquals("Gato", animal.getEspecie());
        assertEquals("Laranja", animal.getRaca());

        assertNotNull(animal.getCliente());
        assertEquals("José", animal.getCliente().getNome());
    }

    @Test
    public void testConsultaRelacionamentoVeterinario() {
        Consulta consulta = em.find(Consulta.class, 1L);
        Veterinario veterinario = consulta.getVeterinario();

        assertNotNull(veterinario);
        assertEquals("Dr. João Silva", veterinario.getNome());
        assertEquals("CRM-PE-12345", veterinario.getCrmv());
        assertEquals("Clínica Geral", veterinario.getEspecialidade());
    }

    @Test
    public void testConsultaRelacionamentoPrescricoes() {
        Consulta consulta = em.find(Consulta.class, 2L);
        List<Prescricao> prescricoes = consulta.getPrescricoes();

        assertNotNull(prescricoes);
        assertEquals(2, prescricoes.size());

        Prescricao primeiraPrescricao = prescricoes.get(0);
        assertEquals("Antibiótico", primeiraPrescricao.getMedicamento());
        assertEquals("250mg", primeiraPrescricao.getDosagem());
    }

    @Test
    public void testConsultaRelacionamentoPagamento() {
        Consulta consulta = em.find(Consulta.class, 1L);
        Pagamento pagamento = consulta.getPagamento();

        assertNotNull(pagamento);
        assertEquals(150.00, pagamento.getValorTotal(), 0.001);
        assertEquals("Dinheiro", pagamento.getMetodoPagamento());
    }

    @Test
    public void testConsultasPorVeterinario() {
        Veterinario veterinario = em.find(Veterinario.class, 1L);
        List<Consulta> consultas = veterinario.getConsultas();

        assertNotNull(consultas);
        assertEquals(5, consultas.size());

        for (Consulta consulta : consultas) {
            assertEquals(veterinario.getIdVeterinario(), consulta.getVeterinario().getIdVeterinario());
        }
    }

    @Test
    public void testConsultaComCachorro() {
        Consulta consulta = em.find(Consulta.class, 2L);
        Animal animal = consulta.getAnimal();

        assertNotNull(animal);
        assertEquals("Rex", animal.getNome());
        assertEquals("Cachorro", animal.getEspecie());
        assertEquals("Pastor Alemão", animal.getRaca());
    }

    @Test
    public void testConsultaComGato() {
        Consulta consulta = em.find(Consulta.class, 1L);
        Animal animal = consulta.getAnimal();

        assertNotNull(animal);
        assertEquals("Fofinho", animal.getNome());
        assertEquals("Gato", animal.getEspecie());
        assertEquals("Laranja", animal.getRaca());
    }
}

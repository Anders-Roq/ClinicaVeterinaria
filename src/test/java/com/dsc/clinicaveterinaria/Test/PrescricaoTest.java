package com.dsc.clinicaveterinaria.Test;

import com.dsc.clinicaveterinaria.Animal;
import com.dsc.clinicaveterinaria.Consulta;
import com.dsc.clinicaveterinaria.Prescricao;
import com.dsc.clinicaveterinaria.Veterinario;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrescricaoTest extends BaseTest {

    @Test
    public void testPersistirPrescricao() {

        // Carrega dependências existentes (já estão no dataset)
        Animal animal = em.find(Animal.class, 1L);       // Ex: "Rex"
        Veterinario vet = em.find(Veterinario.class, 1L); // Ex: "Dr. João Silva"

        assertNotNull(animal);
        assertNotNull(vet);

        // Criar consulta nova para vincular à prescrição
        Consulta consulta = new Consulta(
                LocalDateTime.of(2025, 1, 15, 10, 30),
                "Consulta de acompanhamento",
                "Animal apresenta boa evolução",
                animal,
                vet
        );

        em.persist(consulta);
        em.flush();
        assertNotNull(consulta.getIdConsulta());

        // Criar e persistir a prescrição
        Prescricao prescricao = new Prescricao(
                "Dipirona",
                "10mg por kg",
                "A cada 8 horas",
                "5 dias",
                consulta
        );

        em.persist(prescricao);
        em.flush();

        // Testes de verificação
        assertNotNull(prescricao.getId());
        assertEquals("Dipirona", prescricao.getMedicamento());
        assertEquals("10mg por kg", prescricao.getDosagem());
        assertEquals("A cada 8 horas", prescricao.getFrequencia());
        assertEquals("5 dias", prescricao.getDuracao());
        assertEquals(consulta.getIdConsulta(), prescricao.getConsulta().getIdConsulta());
    }

    @Test
    public void testConsultarPrescricaoPorId() {
        Prescricao prescricao = em.find(Prescricao.class, 1L);

        assertNotNull(prescricao);
        assertEquals("Antibiótico", prescricao.getMedicamento());
        assertEquals("250mg", prescricao.getDosagem());
        assertEquals("A cada 12 horas", prescricao.getFrequencia());
        assertEquals("7 dias", prescricao.getDuracao());

        // Relação com consulta (ManyToOne → EAGER)
        assertNotNull(prescricao.getConsulta());
        assertEquals(2L, prescricao.getConsulta().getIdConsulta());
    }

    @Test
    public void testPrescricaoRelacionamentoConsulta() {

        Prescricao prescricao = em.find(Prescricao.class, 1L);
        Consulta consulta = prescricao.getConsulta();

        assertNotNull(consulta);
        assertEquals("Ferida na pata", consulta.getMotivo());
        assertEquals("Ferida tratada e cicatrizando bem", consulta.getDiagnostico());

        // Verifica o animal da consulta
        assertNotNull(consulta.getAnimal());
        assertEquals("Rex", consulta.getAnimal().getNome());
    }

    @Test
    public void testPrescricoesPorConsulta() {

        Consulta consulta = em.find(Consulta.class, 2L);

        assertNotNull(consulta);
        assertNotNull(consulta.getPrescricoes());
        assertEquals(2, consulta.getPrescricoes().size());

        Prescricao primeira = consulta.getPrescricoes().get(0);
        Prescricao segunda = consulta.getPrescricoes().get(1);

        assertEquals("Antibiótico", primeira.getMedicamento());
        assertEquals("Anti-inflamatório", segunda.getMedicamento());
    }
}

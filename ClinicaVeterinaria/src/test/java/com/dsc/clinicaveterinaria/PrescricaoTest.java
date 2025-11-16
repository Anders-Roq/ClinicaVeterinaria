package com.dsc.clinicaveterinaria;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrescricaoTest extends BaseTest {

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

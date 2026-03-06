package com.dsc.clinicaveterinaria.TestBeanValidation;

import com.dsc.clinicaveterinaria.*;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ValidacaoConsultaTest extends BaseTest {

    @Test
    public void deveFalharAoPersistirConsultaParaDataInvalida() {
        Animal animal = em.find(Animal.class, 1L); // já populado no DBUnit
        Veterinario vet = em.find(Veterinario.class, 1L);

        Consulta consulta = new Consulta(
                LocalDateTime.of(2050, 1, 10, 14, 0),
                "Avaliação clínica",
                "Paciente apresentou melhora significativa.",
                animal,
                vet
        );
        try {
            em.persist(consulta);
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("deve ser uma data no passado ou no presente", violation.getMessage());
        }
    }

    @Test
    public void deveFalharAoPersistirConsultaSemMotivo() {

    Animal animal = em.find(Animal.class, 1L);
    Veterinario vet = em.find(Veterinario.class, 1L);

    String motivoInvalido = ""; // Vazio: viola @NotBlank e @Size

    Consulta consulta = new Consulta(
            LocalDateTime.now(),
            motivoInvalido,
            "Diagnóstico válido",
            animal,
            vet
    );

    ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
        em.persist(consulta);
        em.flush();
    });

    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
    
    // Nesse caso, esperamos 2 violações
    assertEquals(2, violations.size());

    // Extrai as mensagens
    Set<String> mensagens = violations.stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toSet());

    assertTrue(mensagens.contains("Motivo da Consulta é obrigatório"));
    assertTrue(mensagens.contains("Minimo de 10 caracteres"));
    }
    
     @Test
    public void deveFalharAoPersistirConsultaSemDiagnostico() {

    Animal animal = em.find(Animal.class, 1L);
    Veterinario vet = em.find(Veterinario.class, 1L);

    String diagnostico = ""; // Vazio: viola @NotBlank e @Size

    Consulta consulta = new Consulta(
            LocalDateTime.now(),
            "Avaliação clínica",
            diagnostico,
            animal,
            vet
    );

    ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
        em.persist(consulta);
        em.flush();
    });

    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
    
    // Nesse caso, esperamos 2 violações
    assertEquals(2, violations.size());

    // Extrai as mensagens
    Set<String> mensagens = violations.stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toSet());

    assertTrue(mensagens.contains("Diagnostico da COnsulta é obrigatorio"));
    assertTrue(mensagens.contains("Minimo de 10 caracteres"));
    }
}

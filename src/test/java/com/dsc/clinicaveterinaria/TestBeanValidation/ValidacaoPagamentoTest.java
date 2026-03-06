package com.dsc.clinicaveterinaria.TestBeanValidation;

import com.dsc.clinicaveterinaria.*;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ValidacaoPagamentoTest extends BaseTest {

    @Test
    public void deveFalharAoPersistirPagamentoValorZero() {

        Animal animal = em.find(Animal.class, 1L);
        Veterinario vet = em.find(Veterinario.class, 1L);

        Consulta consulta = new Consulta(
                LocalDateTime.of(2026, 2, 14, 14, 0),
                "Retorno pós-cirurgia",
                "Recuperação evoluindo bem",
                animal,
                vet
        );

        em.persist(consulta);
        em.flush();

        Pagamento pagamento = new Pagamento(
                0,
                "Pix",
                LocalDateTime.of(2026, 2, 14, 14, 0),
                consulta
        );
        
        try {
            em.persist(pagamento);
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("O valor deve ser maior que zero", violation.getMessage() );
        }

    }

    @Test
    public void deveFalharAoAoPersistirPagamentoSemMetodoPagamento() {
        
        Animal animal = em.find(Animal.class, 1L);
        Veterinario vet = em.find(Veterinario.class, 1L);

        Consulta consulta = new Consulta(
                LocalDateTime.of(2026, 2, 14, 14, 0),
                "Retorno pós-cirurgia",
                "Recuperação evoluindo bem",
                animal,
                vet
        );

        em.persist(consulta);
        em.flush();

        Pagamento pagamento = new Pagamento(
                350.00,
                " ",
                LocalDateTime.of(2026, 2, 14, 14, 0),
                consulta
        );
        
        try {
            em.persist(pagamento);
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("O metodo de pagamento é obrigatorio", violation.getMessage() );
        }

    }
    
    @Test
    public void deveFalharAoAoPersistirPagamentoComDataFutura() {
        
        Animal animal = em.find(Animal.class, 1L);
        Veterinario vet = em.find(Veterinario.class, 1L);

        Consulta consulta = new Consulta(
                LocalDateTime.of(2026, 2, 14, 14, 0),
                "Retorno pós-cirurgia",
                "Recuperação evoluindo bem",
                animal,
                vet
        );

        em.persist(consulta);
        em.flush();

        Pagamento pagamento = new Pagamento(
                350.00,
                "PIX",
                LocalDateTime.of(2027, 2, 14, 14, 0),
                consulta
        );
        
        try {
            em.persist(pagamento);
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("deve ser uma data no passado ou no presente", violation.getMessage() );
        }

    }
    
}

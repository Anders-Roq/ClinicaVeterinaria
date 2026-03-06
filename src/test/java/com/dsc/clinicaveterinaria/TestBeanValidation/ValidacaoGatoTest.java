package com.dsc.clinicaveterinaria.TestBeanValidation;

import com.dsc.clinicaveterinaria.*;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Classe de testes específica para validações da entidade Gato.
 *
 * Valida:
 * - @NotNull de gostaDeCaixa
 * - @NotNull de preguicoso
 * - Validações herdadas da classe Animal
 */
public class ValidacaoGatoTest extends BaseTest {

    @Test
    public void deveFalharQuandoGostaDeCaixaForNull() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Gato gato = new Gato(
                "Mingau",
                "Gato",
                "Persa",
                'M',
                LocalDate.of(2020, 1, 10),
                cliente,
                null,      // ❌ viola @NotNull
                true,
                "Curta"    // ✔ novo campo obrigatório
        );

        ConstraintViolationException ex = assertThrows(
                ConstraintViolationException.class,
                () -> {
                    em.persist(gato);
                    em.flush();
                }
        );

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        System.out.println("\n=== TESTE 1 - gostaDeCaixa NULL ===");
        System.out.println("Total de violações: " + violations.size());

        for (ConstraintViolation<?> v : violations) {
            System.out.println("Campo: " + v.getPropertyPath());
            System.out.println("Valor informado: " + v.getInvalidValue());
            System.out.println("Mensagem: " + v.getMessage());
            System.out.println("-----------------------------------");
        }

        assertEquals(1, violations.size());
        assertEquals("gostaDeCaixa",
                violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void deveFalharQuandoPreguicosoForNull() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Gato gato = new Gato(
                "Luna",
                "Gato",
                "Siamês",
                'F',
                LocalDate.of(2019, 5, 10),
                cliente,
                true,
                null,      // ❌ viola @NotNull
                "Longa"
        );

        ConstraintViolationException ex = assertThrows(
                ConstraintViolationException.class,
                () -> {
                    em.persist(gato);
                    em.flush();
                }
        );

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        System.out.println("\n=== TESTE 2 - preguicoso NULL ===");
        System.out.println("Total de violações: " + violations.size());

        for (ConstraintViolation<?> v : violations) {
            System.out.println("Campo: " + v.getPropertyPath());
            System.out.println("Valor informado: " + v.getInvalidValue());
            System.out.println("Mensagem: " + v.getMessage());
            System.out.println("-----------------------------------");
        }

        assertEquals(1, violations.size());
        assertEquals("preguicoso",
                violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void deveFalharQuandoDataNascimentoForFutura() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Gato gato = new Gato(
                "Lu",
                "Gato",
                "Persa",
                'F',
                LocalDate.now().plusDays(2), // ❌ inválido
                cliente,
                true,
                true,
                "Média"
        );

        assertThrows(ConstraintViolationException.class, () -> {
            em.persist(gato);
            em.flush();
        });
    }

    @Test
    public void deveCadastrarGatoValido() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Gato gatoValido = new Gato(
                "Nina",
                "Gato",
                "Siamês",
                'F',
                LocalDate.of(2018, 3, 15),
                cliente,
                true,
                false,
                "Curta"
        );

        em.persist(gatoValido);
        em.flush();

        System.out.println("\n=== TESTE 4 - GATO VÁLIDO ===");
        System.out.println("Total de violações: 0");
        System.out.println("ID gerado: " + gatoValido.getIdAnimal());
        System.out.println("-----------------------------------");

        assertNotNull(gatoValido.getIdAnimal());
        assertEquals("Nina", gatoValido.getNome());
    }
}
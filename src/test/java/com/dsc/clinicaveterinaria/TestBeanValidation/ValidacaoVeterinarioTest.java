/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dsc.clinicaveterinaria.TestBeanValidation;

import com.dsc.clinicaveterinaria.*;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ValidacaoVeterinarioTest extends BaseTest {

    
    // TESTE DE PERSISTÊNCIA INVÁLIDA
    
    @Test
    public void deveFalharAoPersistirVeterinarioInvalido() {

        Veterinario vet = new Veterinario(
                "",                 // nome inválido
                "123",              // crmv inválido
                "",                 // especialidade inválida
                "123",              // telefone inválido
                "email-invalido"    // email inválido
        );

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> {
                    em.persist(vet);
                    em.flush();
                }
        );

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        System.out.println("\n=== TESTE 1 - PERSISTÊNCIA INVÁLIDA ===");
        System.out.println("Total de violações: " + violations.size());

        for (ConstraintViolation<?> v : violations) {
            System.out.println("Campo: " + v.getPropertyPath());
            System.out.println("Valor informado: " + v.getInvalidValue());
            System.out.println("Mensagem: " + v.getMessage());
            System.out.println("-----------------------------------");
        }

        assertTrue(violations.size() >= 5);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nome")));

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("crmv")));

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("telefone")));

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));

        assertNull(vet.getIdVeterinario());
    }

    
    // TESTE DE PERSISTÊNCIA VÁLIDA
    
    @Test
    public void devePersistirVeterinarioValido() {

        Veterinario vet = new Veterinario(
                "Dr. João Silva",
                "CRMV-PE-1234",      // <-- corrigido (com "V")
                "Clínica Geral",
                "123456789",           // 9 dígitos
                "carlos@vet.com"
        );

        // Tenta persistir e captura exceção caso ocorra (para exibir detalhes)
        try {
            em.persist(vet);
            em.flush();

            System.out.println("\n=== TESTE 2 - PERSISTÊNCIA VÁLIDA ===");
            System.out.println("Total de violações: 0");
            System.out.println("ID gerado: " + vet.getIdVeterinario());
            System.out.println("-----------------------------------");

        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

            System.out.println("\n=== TESTE 2 - PERSISTÊNCIA VÁLIDA (FALHOU) ===");
            System.out.println("Total de violações: " + violations.size());

            for (ConstraintViolation<?> v : violations) {
                System.out.println("Campo: " + v.getPropertyPath());
                System.out.println("Valor informado: " + v.getInvalidValue());
                System.out.println("Mensagem: " + v.getMessage());
                System.out.println("-----------------------------------");
            }
            fail("Deveria persistir veterinário válido, mas houve violações.");
        }

        assertNotNull(vet.getIdVeterinario());
        assertEquals("Dr. João Silva", vet.getNome());
    }

    
    // TESTE DE ATUALIZAÇÃO INVÁLIDA
    
    @Test
    public void deveFalharAoAtualizarEmailParaInvalido() {

        Veterinario vet = new Veterinario(
                "Dra. Ana Souza",
                "CRMV-PE-5432",      // <-- corrigido
                "Cirurgia",
                "987654321",
                "ana@vet.com"
        );

        em.persist(vet);
        em.flush();

        System.out.println("\n=== TESTE 3 - ATUALIZAÇÃO INVÁLIDA ===");
        System.out.println("Email original: " + vet.getEmail());

        vet.setEmail("email-invalido");

        ConstraintViolationException ex = assertThrows(
                ConstraintViolationException.class,
                () -> em.flush()
        );

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        // Exibe detalhes da violação
        System.out.println("Total de violações: " + violations.size());
        for (ConstraintViolation<?> v : violations) {
            System.out.println("Campo: " + v.getPropertyPath());
            System.out.println("Valor informado: " + v.getInvalidValue());
            System.out.println("Mensagem: " + v.getMessage());
            System.out.println("-----------------------------------");
        }

        assertEquals(1, violations.size());
        assertEquals("email",
                violations.iterator().next().getPropertyPath().toString());

        System.out.println("Resultado: Atualização inválida corretamente bloqueada.\n");
    }

    
    // TESTE DE ATUALIZAÇÃO VÁLIDA
    
    @Test
    public void deveAtualizarTelefoneCorretamente() {

        Veterinario vet = new Veterinario(
                "Dr. Paulo Mendes",
                "CRMV-PE-6789",      // <-- corrigido
                "Dermatologia",
                "111111111",           // 9 dígitos
                "paulo@vet.com"
        );

        em.persist(vet);
        em.flush();

        Long id = vet.getIdVeterinario();

        System.out.println("\n=== TESTE 4 - ATUALIZAÇÃO VÁLIDA ===");

        vet.setTelefone("222222222"); // 9 dígitos
        em.flush();

        Veterinario atualizado = em.find(Veterinario.class, id);

        assertEquals("222222222", atualizado.getTelefone());

        System.out.println("Telefone atualizado para: " + atualizado.getTelefone());
        System.out.println("Resultado: Atualização realizada com sucesso.\n");
    }
}
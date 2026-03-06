package com.dsc.clinicaveterinaria.TestBeanValidation;

import com.dsc.clinicaveterinaria.Cliente;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ValidacaoClienteTest extends BaseTest {

    @Test
    public void deveFalharAoPersistirClienteComCpfEmailTelefoneInvalidos() {
        // Mantém nome/endereço válidos e quebra só CPF/telefone/email
        Cliente cliente = new Cliente(
                "Nome Valido",
                "000.000.000-00",       // CPF inválido
                "65656565",             // telefone inválido (8 dígitos)
                "TesteErradoGmail.com", // email inválido
                "Rua X, 123"            // endereço válido
        );

        ConstraintViolationException ex = assertConstraintViolation(() -> {
            em.persist(cliente);
            em.flush(); // valida no prePersist
        });

        assertTemViolacao(ex.getConstraintViolations(), "cpf");
        assertTemViolacao(ex.getConstraintViolations(), "telefone");
        assertTemViolacao(ex.getConstraintViolations(), "email");

        // garantindo que nome/endereco não são a causa (senão o teste fica “impuro”)
        assertNaoTemViolacao(ex.getConstraintViolations(), "nome");
        assertNaoTemViolacao(ex.getConstraintViolations(), "endereco");
    }

    @Test
    public void deveFalharAoAtualizarClienteComCpfEmailTelefoneInvalidos() {
        // 1) Persistir um cliente realmente válido
        Cliente cliente = new Cliente(
                "Cliente Valido",
                "529.982.247-25",      // CPF válido conhecido (ajuste se você preferir outro)
                "123456789",           // 9 dígitos (seu validator exige isso)
                "cliente@teste.com",
                "Rua Y, 456"
        );

        em.persist(cliente);
        em.flush();   // força INSERT (e validação do prePersist com dados válidos)
        em.clear();   // força cenário de atualização com entidade destacada

        // 2) Atualiza com dados inválidos
        cliente.setCpf("000.000.000-00");
        cliente.setTelefone("12ABC");
        cliente.setEmail("invalido@");

        ConstraintViolationException ex = assertConstraintViolation(() -> {
            em.merge(cliente);
            em.flush(); // valida no preUpdate (ou durante o merge/flush)
        });

        assertTemViolacao(ex.getConstraintViolations(), "cpf");
        assertTemViolacao(ex.getConstraintViolations(), "telefone");
        assertTemViolacao(ex.getConstraintViolations(), "email");
    }

    // ---------------- helpers ----------------

    private ConstraintViolationException assertConstraintViolation(Runnable action) {
        RuntimeException thrown = assertThrows(RuntimeException.class, action::run);

        ConstraintViolationException cve = findCause(thrown, ConstraintViolationException.class);
        if (cve == null) {
            fail("Esperava ConstraintViolationException, mas veio: "
                    + thrown.getClass().getName() + " - " + thrown.getMessage());
        }

        // debug útil (pode manter ou remover)
        cve.getConstraintViolations().forEach(v ->
                System.out.println(v.getPropertyPath() + " -> " + v.getMessage())
        );

        return cve;
    }

    private void assertTemViolacao(Set<? extends ConstraintViolation<?>> violations, String campo) {
        assertTrue(
                violations.stream().anyMatch(v -> campo.equals(v.getPropertyPath().toString())),
                "Esperava violação no campo '" + campo + "'. Violações: "
                        + violations.stream()
                        .map(v -> v.getPropertyPath() + "=" + v.getMessage())
                        .collect(Collectors.joining(", "))
        );
    }

    private void assertNaoTemViolacao(Set<? extends ConstraintViolation<?>> violations, String campo) {
        assertFalse(
                violations.stream().anyMatch(v -> campo.equals(v.getPropertyPath().toString())),
                "Não era para ter violação no campo '" + campo + "'"
        );
    }

    private static <T extends Throwable> T findCause(Throwable t, Class<T> type) {
        Throwable cur = t;
        while (cur != null) {
            if (type.isInstance(cur)) return type.cast(cur);
            cur = cur.getCause();
        }
        return null;
    }
}
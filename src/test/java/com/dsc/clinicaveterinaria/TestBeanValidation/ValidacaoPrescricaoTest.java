package com.dsc.clinicaveterinaria.TestBeanValidation;

import com.dsc.clinicaveterinaria.Cliente;
import com.dsc.clinicaveterinaria.Consulta;
import com.dsc.clinicaveterinaria.Prescricao;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import com.dsc.clinicaveterinaria.validation.PrescricaoMedicamentoValido;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de Bean Validation para:
 * - Cliente (campo cpf) -> cenário de persistência com dado inválido
 * - Prescricao (campo medicamento) -> cenário de atualização (merge) com dado inválido
 */
public class ValidacaoPrescricaoTest extends BaseTest {

//    //private final CPFValido clienteCpfValidatorService = new CPFValido();
//    private final PrescricaoMedicamentoValido prescricaoMedicamentoValidatorService =
//            new PrescricaoMedicamentoValido();
//
//    @Test
//    public void deveFalharAoPersistirClienteComCpfInvalido() {
//        // Mantém os outros campos "ok" e quebra apenas o CPF (campo específico).
//        Cliente cliente = new Cliente(
//                "Rodrigo da Silva",
//                "000.000.000-00",       // inválido (assumindo @CPF ou regra semelhante)
//                "11999999999",
//                "rodrigo@exemplo.com",
//                "Rua A, 123"
//        );
//
//        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
//            clienteCpfValidatorService.validarOuFalhar(cliente);
//            em.persist(cliente);
//            em.flush();
//        });
//
//        assertFalse(ex.getConstraintViolations().isEmpty());
//        assertTrue(
//                ex.getConstraintViolations().stream()
//                        .anyMatch(v -> "cpf".equals(v.getPropertyPath().toString())),
//                "Esperava violação especificamente no campo 'cpf'"
//        );
//    }
//
//    @Test
//    public void deveFalharAoAtualizarPrescricaoComMedicamentoInvalido() {
//        Consulta consulta = obterUmaConsultaExistente();
//        assertNotNull(consulta, "É necessário existir pelo menos uma Consulta no banco de testes.");
//
//        // 1) Cria uma prescrição válida (para conseguir persistir)
//        Prescricao prescricao = new Prescricao(
//                "Amoxicilina",
//                "10mg",
//                "2x ao dia",
//                "7 dias",
//                consulta
//        );
//
//        em.persist(prescricao);
//        em.flush();
//
//        // 2) Simula atualização via merge: destaca (detach) e tenta salvar inválida
//        em.clear();
//        prescricao.setMedicamento("   "); // inválido (assumindo @NotBlank)
//
//        ConstraintViolationException ex = assertThrows(ConstraintViolationException.class, () -> {
//            prescricaoMedicamentoValidatorService.validarOuFalhar(prescricao);
//            em.merge(prescricao);
//            em.flush();
//        });
//
//        assertFalse(ex.getConstraintViolations().isEmpty());
//        assertTrue(
//                ex.getConstraintViolations().stream()
//                        .anyMatch(v -> "medicamento".equals(v.getPropertyPath().toString())),
//                "Esperava violação especificamente no campo 'medicamento'"
//        );
//    }
//
//    /**
//     * Tenta obter uma Consulta existente para não precisar reconstruir todo o grafo
//     * (Cliente/Animal/Veterinário/etc.) dentro do teste.
//     */
//    private Consulta obterUmaConsultaExistente() {
//        List<Consulta> consultas = em.createQuery("select c from Consulta c", Consulta.class)
//                .setMaxResults(1)
//                .getResultList();
//
//        return consultas.isEmpty() ? null : consultas.get(0);
//    }
}
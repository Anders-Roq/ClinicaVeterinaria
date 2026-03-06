package com.dsc.clinicaveterinaria.TestBeanValidation;

import com.dsc.clinicaveterinaria.*;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Testes de validação da entidade Cachorro. Demonstra persistência e
 * atualização válidas e inválidas, exibindo mensagens do
 * ValidationMessages.properties.
 */
public class ValidacaoCachorroTest extends BaseTest {

    /*
     * TESTE 1
     * Deve falhar porque o porte está vazio.
     * Viola:
     * cachorro.porte.notblank
     */
    @Test
    public void deveFalharQuandoPorteForVazio() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Cachorro cachorro = new Cachorro(
                "Rex",
                "Cachorro",
                "Poodle",
                'M',
                LocalDate.of(2020, 1, 1),
                cliente,
                "", // inválido
                true
        );

        try {
            em.persist(cachorro);
            em.flush();
            fail("Deveria ter lançado ConstraintViolationException");
        } catch (ConstraintViolationException ex) {

            Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

            System.out.println("\n=== TESTE 1 - PORTE VAZIO ===");
            System.out.println("Total de violações encontradas: " + violations.size());
            System.out.println("-----------------------------------------");

            for (ConstraintViolation<?> v : violations) {

                System.out.println("Campo afetado: " + v.getPropertyPath());
                System.out.println("Valor informado: '" + v.getInvalidValue() + "'");
                System.out.println("Mensagem do ValidationMessages: " + v.getMessage());
                System.out.println("Motivo: Campo obrigatório não preenchido.");
                System.out.println("-----------------------------------------");

                assertEquals("porte", v.getPropertyPath().toString());
                assertEquals("O porte do cachorro é obrigatório.", v.getMessage());
            }

            assertEquals(1, violations.size());

            System.out.println("Resultado: Validação ocorreu corretamente.\n");
        }
    }

    /*
     * TESTE 2
     * Deve falhar porque o porte não está na lista válida.
     * Viola:
     * cachorro.porte.invalid
     */
    @Test
    public void deveFalharQuandoPorteNaoForPermitido() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Cachorro cachorro = new Cachorro(
                "Bolt",
                "Cachorro",
                "Husky",
                'M',
                LocalDate.of(2018, 5, 10),
                cliente,
                "Gigante", // inválido
                true
        );

        try {
            em.persist(cachorro);
            em.flush();
            fail("Deveria ter lançado ConstraintViolationException");
        } catch (ConstraintViolationException ex) {

            Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

            System.out.println("\n=== TESTE 2 - PORTE NÃO PERMITIDO ===");
            System.out.println("Total de violações encontradas: " + violations.size());
            System.out.println("-----------------------------------------");

            for (ConstraintViolation<?> v : violations) {

                System.out.println("Campo afetado: " + v.getPropertyPath());
                System.out.println("Valor informado: '" + v.getInvalidValue() + "'");
                System.out.println("Mensagem do ValidationMessages: " + v.getMessage());
                System.out.println("Motivo: Valor não pertence à lista permitida (Pequeno, Medio, Grande).");
                System.out.println("-----------------------------------------");

                assertEquals("porte", v.getPropertyPath().toString());
                assertEquals("Porte deve ser Pequeno, Medio ou Grande.", v.getMessage());
            }

            assertEquals(1, violations.size());

            System.out.println("Resultado: Validação ocorreu corretamente.\n");
        }
    }

    /*
     * TESTE 3
     * Deve persistir corretamente.
     * Nenhuma violação deve ocorrer.
     */
    @Test
    public void deveCadastrarCachorroValido() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Cachorro cachorro = new Cachorro(
                "Thor",
                "Cachorro",
                "Labrador",
                'M',
                LocalDate.of(2019, 5, 10),
                cliente,
                "Grande",
                true
        );

        em.persist(cachorro);
        em.flush();

        System.out.println("\n=== TESTE 3 - CADASTRO VÁLIDO ===");
        System.out.println("Total de violações encontradas: 0");
        System.out.println("Cadastro realizado com sucesso.");
        System.out.println("ID gerado: " + cachorro.getIdAnimal());
        System.out.println("-----------------------------------------");

        assertNotNull(cachorro.getIdAnimal());
        assertEquals("Grande", cachorro.getPorte());

        Cachorro banco = em.find(Cachorro.class, cachorro.getIdAnimal());
        assertEquals("Grande", banco.getPorte());

        System.out.println("Resultado: Persistência válida confirmada.\n");
    }

    /*
     * TESTE 4
     * Deve falhar ao atualizar porte para valor inválido.
     */
    @Test
    public void deveFalharAoAtualizarPorteParaInvalido() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Cachorro cachorro = new Cachorro(
                "Max",
                "Cachorro",
                "Beagle",
                'M',
                LocalDate.of(2021, 2, 2),
                cliente,
                "Pequeno",
                true
        );

        em.persist(cachorro);
        em.flush();

        cachorro.setPorte("Enorme");

        try {
            em.flush();
            fail("Deveria ter lançado ConstraintViolationException na atualização");
        } catch (ConstraintViolationException ex) {

            Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

            System.out.println("\n=== TESTE 4 - ATUALIZAÇÃO INVÁLIDA ===");
            System.out.println("Operação: Atualização de registro");
            System.out.println("Total de violações encontradas: " + violations.size());
            System.out.println("-----------------------------------------");

            for (ConstraintViolation<?> v : violations) {

                System.out.println("Campo afetado: " + v.getPropertyPath());
                System.out.println("Valor informado: '" + v.getInvalidValue() + "'");
                System.out.println("Mensagem do ValidationMessages: " + v.getMessage());
                System.out.println("Motivo: Valor não permitido.");
                System.out.println("-----------------------------------------");

                assertEquals("porte", v.getPropertyPath().toString());
                assertEquals("Porte deve ser Pequeno, Medio ou Grande.", v.getMessage());
            }

            assertEquals(1, violations.size());

            System.out.println("Resultado: Validação na atualização ocorreu corretamente.\n");
        }
    }

    /*
     * TESTE 5
     * Deve permitir atualização válida.
     */
    @Test
    public void deveAtualizarPorteComValorValido() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Cachorro cachorro = new Cachorro(
                "Luke",
                "Cachorro",
                "Border Collie",
                'M',
                LocalDate.of(2020, 6, 15),
                cliente,
                "Medio",
                true
        );

        em.persist(cachorro);
        em.flush();

        Long id = cachorro.getIdAnimal();
        String valorAnterior = cachorro.getPorte();// Guardando valor antes da atualização
        cachorro.setPorte("Grande");
        em.flush();

        System.out.println("\n=== TESTE 5 - ATUALIZAÇÃO VÁLIDA ===");
        System.out.println("Total de violações encontradas: 0");
        System.out.println("Campo alterado: porte");
        System.out.println("Valor anterior: " + valorAnterior);
        System.out.println("Novo valor: " + cachorro.getPorte());
        System.out.println("-----------------------------------------");

        //  Verifica se o valor foi alterado na entidade
        assertEquals("Grande", cachorro.getPorte());

        //  Verifica se realmente houve mudança
        assertNotEquals(valorAnterior, cachorro.getPorte());

        //  Busca novamente no banco para garantir persistência real
        Cachorro atualizado = em.find(Cachorro.class, id);
        assertEquals("Grande", atualizado.getPorte());

        System.out.println("Resultado: Atualização realizada com sucesso e persistida corretamente.\n");
    }
}

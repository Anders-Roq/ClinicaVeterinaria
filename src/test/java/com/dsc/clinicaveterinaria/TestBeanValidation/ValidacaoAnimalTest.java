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
 * Testes de validação da entidade Animal. Como Animal é abstrata, utilizamos
 * Gato e Cachorro.
 */
public class ValidacaoAnimalTest extends BaseTest {

    /**
     * Testa múltiplas violações herdadas da classe Animal usando Gato.
     */
    @Test
    public void deveFalharQuandoSexoForInvalidoGato() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Gato gato = new Gato(
                "Mimi", // válido
                "Gato", // válido
                "Siamês", // válido
                'X', //  inválido
                LocalDate.of(2020, 1, 10),// válido
                cliente,
                true,
                false,
                "Curta" // válido
        );

        try {
            em.persist(gato);
            em.flush();
            fail("Deveria ter lançado ConstraintViolationException");
        } catch (ConstraintViolationException ex) {

            Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

            System.out.println("\n=== TESTE - SEXO INVÁLIDO (GATO) ===");
            System.out.println("Total de violações encontradas: " + violations.size());

            for (ConstraintViolation<?> v : violations) {
                System.out.println("Campo: " + v.getPropertyPath());
                System.out.println("Valor informado: " + v.getInvalidValue());
                System.out.println("Mensagem: " + v.getMessage());
            }

            //  Deve existir apenas 1 erro
            assertEquals(1, violations.size());

            //  Campo deve ser sexo
            assertEquals("sexo", violations.iterator().next().getPropertyPath().toString());

            //  Mensagem correta
            assertEquals("Sexo deve ser 'M' ou 'F'.",
                    violations.iterator().next().getMessage());
        }
    }

    /**
     * Testa validação da classe Animal usando Cachorro.
     */
    @Test
    public void deveFalharQuandoCachorroTiverDadosInvalidos() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Cachorro cachorroInvalido = new Cachorro(
                "", // Nome inválido
                "", // Espécie inválida
                "", // Raça inválida
                'Z', // Sexo inválido
                LocalDate.now().plusDays(2), // Data futura
                cliente,
                "Gigante", // Porte inválido
                null // Adestrado inválido
        );

        System.out.println("\n=== TESTE 2 - ANIMAL INVÁLIDO (CACHORRO) ===");

        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> {
                    em.persist(cachorroInvalido);
                    em.flush();
                }
        );

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        System.out.println("Total de violações encontradas: " + violations.size());
        System.out.println("-----------------------------------------");

        for (ConstraintViolation<?> v : violations) {
            System.out.println("Campo afetado: " + v.getPropertyPath());
            System.out.println("Valor inserido: " + v.getInvalidValue());
            System.out.println("Mensagem: " + v.getMessage());
            System.out.println("-----------------------------------------");
        }
        //quantidade de violações
        assertTrue(violations.size() == 8);
        // ID não deve ter sido gerado
        assertNull(cachorroInvalido.getIdAnimal());

        System.out.println("Resultado: Validações acionadas corretamente.\n");
    }

    /**
     * Testa cadastro válido de Gato.
     */
    @Test
    public void deveCadastrarAnimalValido() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Gato animalValido = new Gato(
                "Mimi",
                "Gato",
                "Siamês",
                'F',
                LocalDate.of(2020, 1, 1),
                cliente,
                true,
                false,
                "Curta"
        );

        em.persist(animalValido);
        em.flush();

        System.out.println("\n=== TESTE 3 - ANIMAL VÁLIDO ===");
        System.out.println("Total de violações encontradas: 0");
        System.out.println("ID gerado: " + animalValido.getIdAnimal());
        System.out.println("-----------------------------------------");

        // Assert 1: ID não pode ser nulo
        assertNotNull(animalValido.getIdAnimal());

        // Assert 2: Nome persistido corretamente
        assertEquals("Mimi", animalValido.getNome());

        System.out.println("Resultado: Animal persistido com sucesso.\n");
    }

    @Test
    public void deveFalharAoAtualizarSexoParaInvalidoGato() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Gato gato = new Gato(
                "Luna",
                "Gato",
                "Siamês",
                'F', //  válido inicialmente
                LocalDate.of(2020, 1, 1),
                cliente,
                true,
                false,
                "Curta"
        );

        em.persist(gato);
        em.flush();

        System.out.println("\n=== TESTE 4 - ATUALIZAÇÃO INVÁLIDA (GATO) ===");
        System.out.println("Sexo original: " + gato.getSexo());

        //  Atualizando para inválido
        gato.setSexo('X');

        ConstraintViolationException ex = assertThrows(
                ConstraintViolationException.class,
                () -> em.flush()
        );

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        System.out.println("Novo valor informado: " + 'X');
        System.out.println("Total de violações: " + violations.size());

        for (ConstraintViolation<?> v : violations) {
            System.out.println("Campo afetado: " + v.getPropertyPath());
            System.out.println("Mensagem: " + v.getMessage());
        }

        assertEquals(1, violations.size());
        assertEquals("sexo",
                violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void deveAtualizarSexoCorretamenteGato() {

        Cliente cliente = em.find(Cliente.class, 1L);

        Gato gato = new Gato(
                "Nina",
                "Gato",
                "Persa",
                'F',
                LocalDate.of(2019, 5, 10),
                cliente,
                true,
                true,
                "Longa"
        );

        em.persist(gato);
        em.flush();

        Long id = gato.getIdAnimal();

        System.out.println("\n=== TESTE 5 - ATUALIZAÇÃO VÁLIDA (GATO) ===");
        System.out.println("Sexo original: " + gato.getSexo());

        //  Atualização válida
        gato.setSexo('M');
        em.flush();

        System.out.println("Novo sexo: " + gato.getSexo());

        // Assert 1: mudou na entidade
        assertEquals('M', gato.getSexo());

        // Assert 2: mudou no banco
        Gato atualizado = em.find(Gato.class, id);
        assertEquals('M', atualizado.getSexo());

        System.out.println("Resultado: Atualização realizada com sucesso.\n");
    }
    

    
}

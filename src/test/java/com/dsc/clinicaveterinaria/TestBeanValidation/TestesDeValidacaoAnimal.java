package com.dsc.clinicaveterinaria.TestBeanValidation;

import com.dsc.clinicaveterinaria.*;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Classe responsável por testar as validações da entidade Animal.
 * Como Animal é abstrata, utilizamos Gato para instanciá-la.
 */
public class TestesDeValidacaoAnimal extends BaseTest {

    /**
     * Testa se a persistência falha quando os dados do Animal são inválidos.
     */
    @Test
    public void deveFalharAoCadastrarAnimalInvalido() {

        // Busca um cliente válido do banco (necessário por causa do @ManyToOne)
        Cliente cliente = em.find(Cliente.class, 1L);

        // Criando um animal com múltiplos erros:
        // - Nome muito curto
        // - Espécie vazia
        // - Raça vazia
        // - Sexo inválido
        // - Data futura
        Gato animalInvalido = new Gato(
                "A",                       // inválido (min 3 caracteres)
                "",                        // inválido
                "",                        // inválido
                'X',                       // inválido (esperado M ou F)
                LocalDate.now().plusDays(1), // inválido (data futura)
                cliente,
                true,
                true
        );

        // Esperamos que a validação dispare uma exceção
        assertThrows(ConstraintViolationException.class, () -> {
            em.persist(animalInvalido);
            em.flush(); // força validação imediata
        });
    }

    
    @Test
    public void deveCadastrarAnimalValido() {
        //Testa se o Animal é persistido corretamente quando válido.
        Cliente cliente = em.find(Cliente.class, 1L);

        Gato animalValido = new Gato(
                "Mimi",
                "Gato",
                "Siamês",
                'F',
                LocalDate.of(2020, 1, 1),
                cliente,
                true,
                false
        );

        em.persist(animalValido);
        em.flush();

        // Passando na validação, o ID deve ter sido gerado
        assertNotNull(animalValido.getIdAnimal());
    }
}
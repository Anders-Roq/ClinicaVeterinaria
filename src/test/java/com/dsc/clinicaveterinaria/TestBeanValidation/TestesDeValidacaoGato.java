package com.dsc.clinicaveterinaria.TestBeanValidation;

import com.dsc.clinicaveterinaria.*;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
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
public class TestesDeValidacaoGato extends BaseTest {

   
    @Test
    public void deveFalharQuandoGostaDeCaixaForNull() {
        // Teste Deve falhar porque gostaDeCaixa está null
        Cliente cliente = em.find(Cliente.class, 1L);

        Gato gato = new Gato(
                "Mingau",
                "Gato",
                "Persa",
                'M',
                LocalDate.of(2020, 1, 10),
                cliente,
                null,   // ❌ viola @NotNull
                true
        );

        assertThrows(ConstraintViolationException.class, () -> {
            em.persist(gato);
            em.flush();
        });
    }

  
    @Test
    public void deveFalharQuandoPreguicosoForNull() {
        // Teste Deve falhar porque preguicoso está null.
        Cliente cliente = em.find(Cliente.class, 1L);

        Gato gato = new Gato(
                "Luna",
                "Gato",
                "Siamês",
                'F',
                LocalDate.of(2019, 5, 10),
                cliente,
                true,
                null   // ❌ viola @NotNull
        );

        assertThrows(ConstraintViolationException.class, () -> {
            em.persist(gato);
            em.flush();
        });
    }

    
    @Test
    public void deveFalharQuandoDataNascimentoForFutura() {
        // Teste Deve falhar por regra herdada da classe Animal. (data de nascimento no futuro)
        Cliente cliente = em.find(Cliente.class, 1L);

        Gato gato = new Gato(
                "Lu",
                "Gato",
                "Persa",
                'F',
                LocalDate.now().plusDays(2), // ❌ inválido
                cliente,
                true,
                true
        );

        assertThrows(ConstraintViolationException.class, () -> {
            em.persist(gato);
            em.flush();
        });
    }

   
    @Test
    public void deveCadastrarGatoValido() {
        // Teste Deve cadastrar corretamente quando todos os dados estão válidos.
        Cliente cliente = em.find(Cliente.class, 1L);

        Gato gatoValido = new Gato(
                "Nina",
                "Gato",
                "Siamês",
                'F',
                LocalDate.of(2018, 3, 15),
                cliente,
                true,
                false
        );

        em.persist(gatoValido);
        em.flush();

        assertNotNull(gatoValido.getIdAnimal());
    }
}
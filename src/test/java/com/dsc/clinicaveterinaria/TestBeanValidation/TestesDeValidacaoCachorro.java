package com.dsc.clinicaveterinaria.TestBeanValidation;

import com.dsc.clinicaveterinaria.*;
import com.dsc.clinicaveterinaria.Test.BaseTest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class TestesDeValidacaoCachorro extends BaseTest {

    @Test
    public void deveFalharQuandoPorteForVazio() {
        //Teste  - Deve falhar porque o porte está vazio.
        Cliente cliente = em.find(Cliente.class, 1L);

        Cachorro cachorro = new Cachorro(
                "Rex",
                "Cachorro",
                "Poodle",
                'M',
                LocalDate.of(2020, 1, 1),
                cliente,
                "", //  inválido: viola @NotBlank
                true
        );

        assertThrows(ConstraintViolationException.class, () -> {
            em.persist(cachorro);
            em.flush();
        });
    }



    @Test
    public void deveCadastrarCachorroComPorteValido() {
        //Teste  - Deve persistir corretamente.
        Cliente cliente = em.find(Cliente.class, 1L);

        Cachorro cachorro = new Cachorro(
                "Thor",
                "Cachorro",
                "Labrador",
                'M',
                LocalDate.of(2019, 5, 10),
                cliente,
                "Grande", // ✅ válido
                true
        );

        em.persist(cachorro);
        em.flush();

        assertNotNull(cachorro.getIdAnimal());
    }

    @Test
    public void deveFalharAoCadastrarCachorroInvalido() {
        //Testa falha ao cadastrar cachorro com porte inválido.
        Cliente cliente = em.find(Cliente.class, 1L);

        Cachorro cachorroInvalido = new Cachorro(
                "Rex",
                "Cachorro",
                "Poodle",
                'M',
                LocalDate.of(2020, 1, 1),
                cliente,
                "", // inválido: @NotBlank
                true
        );

        assertThrows(ConstraintViolationException.class, () -> {
            em.persist(cachorroInvalido);
            em.flush();
        });
    }

    @Test
    public void deveCadastrarCachorroValido() {
        //Testa persistência correta de Cachorro válido.
        Cliente cliente = em.find(Cliente.class, 1L);

        Cachorro cachorroValido = new Cachorro(
                "Thor",
                "Cachorro",
                "Labrador",
                'M',
                LocalDate.of(2019, 5, 10),
                cliente,
                "Grande",
                true
        );

        em.persist(cachorroValido);
        em.flush();

        assertNotNull(cachorroValido.getIdAnimal());
    }
}

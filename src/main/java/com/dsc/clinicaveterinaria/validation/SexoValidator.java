package com.dsc.clinicaveterinaria.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//Ela conecta a annotation @SexoValido com a regra de validação. 
public class SexoValidator implements ConstraintValidator<SexoValido, Character> {
    // @return true  → valor válido
    //         false → valor inválido
    @Override
    public boolean isValid(Character value, ConstraintValidatorContext context) {
        if (value == null) return true; // NotNull já trata isso
        // Sexo só pode ser 'M' (Masculino) ou 'F' (Feminino)
        return value == 'M' || value == 'F';
    }
}
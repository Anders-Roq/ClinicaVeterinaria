/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dsc.clinicaveterinaria.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class PelagemValidator implements ConstraintValidator<PelagemValido, String> {

    private static final Set<String> PELAGENS_VALIDAS =
            Set.of("Curta", "Longa", "Sem pelo");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true; 
            // @NotBlank já trata null
        }

        return PELAGENS_VALIDAS.contains(value);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dsc.clinicaveterinaria.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class CRMVValidator implements ConstraintValidator<CRMVValido, String> {

    private static final String CRMVPattern = "CRMV-[A-Z]{2}-\\d{4,6}";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true; // @NotBlank trata isso
        }

        return value.matches(CRMVPattern);
    }
}

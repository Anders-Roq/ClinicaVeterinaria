package com.dsc.clinicaveterinaria.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MedicamentoValidator implements ConstraintValidator<MedicamentoValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Deixa @NotBlank/@NotNull cuidarem de "obrigatório"
        if (value == null || value.isBlank()) return true;

        String v = value.trim();

        // tamanho mínimo/máximo (ajuste se quiser)
        if (v.length() < 2 || v.length() > 80) return false;

        // caracteres permitidos: letras/números + separadores comuns
        for (int i = 0; i < v.length(); i++) {
            char c = v.charAt(i);

            if (Character.isLetterOrDigit(c)) continue;
            if (c == ' ' || c == '.' || c == ',' || c == '\'' ||
                c == '(' || c == ')' || c == '-' || c == '+' || c == '/') {
                continue;
            }

            return false;
        }

        return true;
    }
}
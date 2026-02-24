package com.dsc.clinicaveterinaria.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PorteValidator implements ConstraintValidator<PorteValido, String> {

    private List<String> portesValidos;

    @Override
    public void initialize(PorteValido annotation) {
        // Inicializa lista de valores permitidos
        portesValidos = Arrays.asList("Pequeno", "Medio", "Grande");
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {

        if (valor == null) {
            return true; // @NotBlank trata null
        }

        return portesValidos.contains(valor);
    }
}
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

        // Se for null ou vazio, deixa o @NotBlank tratar
        if (valor == null || valor.trim().isEmpty()) {
            return true;
        }

        return portesValidos.contains(valor);
    }
}

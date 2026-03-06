package com.dsc.clinicaveterinaria.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MedicamentoValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MedicamentoValido {
    String message() default "Medicamento inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
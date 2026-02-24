package com.dsc.clinicaveterinaria.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

//Annotation customizada que será utilizada na entidade
//Essa annotation indica que o campo deve obedecer a regra implementada em SexoValidator.
@Constraint(validatedBy = SexoValidator.class) // Informa qual classe contém a lógica da validação
@Target({ ElementType.FIELD }) // Indica que essa annotation pode ser aplicada apenas em atributos
@Retention(RetentionPolicy.RUNTIME)
// A annotation deve estar disponível em tempo de execução
// para que o Hibernate consiga processá-la

public @interface SexoValido {

    String message() default "{animal.sexo.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
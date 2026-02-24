package com.dsc.clinicaveterinaria.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

//Annotation customizada que será utilizada na entidade
//Essa annotation indica que o campo deve obedecer a regra implementada em PorteValidator.
@Constraint(validatedBy = PorteValidator.class) // Informa qual classe contém a lógica da validação
@Target({ ElementType.FIELD }) // Indica que essa annotation pode ser aplicada apenas em atributos
@Retention(RetentionPolicy.RUNTIME)
// A annotation deve estar disponível em tempo de execução
// para que o Hibernate consiga processá-la

public @interface PorteValido {

     /*Define a mensagem padrão da validação.
     "{cachorro.porte.invalid}" é uma chave que será buscada no arquivo:
     ValidationMessages.properties
     Exemplo:
     cachorro.porte.invalid=Porte deve ser Pequeno, Médio ou Grande.
     */
    String message() default "{cachorro.porte.invalid}";
    Class<?>[] groups() default {}; //é obrigatório na definição da annotation.
    Class<? extends Payload>[] payload() default {}; //Também é obrigatório na estrutura da annotation, mesmo que não seja usado
}
package com.dsc.clinicaveterinaria.validation;

import com.dsc.clinicaveterinaria.Prescricao;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

/**
 * Validador focado em um campo específico da entidade {@link Prescricao}: "medicamento".
 */
public class PrescricaoMedicamentoValidator {

    private final Validator validator;

    public PrescricaoMedicamentoValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    /**
     * Valida apenas o campo "medicamento" da {@link Prescricao}.
     *
     * @param prescricao entidade a ser validada
     * @return violações encontradas (pode ser vazio)
     */
    public Set<ConstraintViolation<Prescricao>> validarMedicamento(Prescricao prescricao) {
        return validator.validateProperty(prescricao, "medicamento");
    }
}
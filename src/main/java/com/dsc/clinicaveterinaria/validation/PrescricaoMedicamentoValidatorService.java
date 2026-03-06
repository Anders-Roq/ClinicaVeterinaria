package com.dsc.clinicaveterinaria.validation;

import com.dsc.clinicaveterinaria.Prescricao;
import jakarta.validation.ConstraintViolationException;

/**
 * Service que chama {@link PrescricaoMedicamentoValidator} e lança
 * {@link ConstraintViolationException} se houver violações.
 */
public class PrescricaoMedicamentoValidatorService {

    private final PrescricaoMedicamentoValidator validator = new PrescricaoMedicamentoValidator();

    /**
     * Valida o campo medicamento e falha se estiver inválido.
     *
     * @param prescricao prescrição a validar
     */
    public void validarOuFalhar(Prescricao prescricao) {
        var violations = validator.validarMedicamento(prescricao);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
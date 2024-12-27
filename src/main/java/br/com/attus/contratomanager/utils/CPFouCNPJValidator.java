package br.com.attus.contratomanager.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static br.com.attus.contratomanager.utils.ValidaCNPJ.isCNPJ;
import static br.com.attus.contratomanager.utils.ValidaCPF.isCPF;

public class CPFouCNPJValidator implements ConstraintValidator<CPFouCNPJ, String> {

    @Override
    public void initialize(CPFouCNPJ constraintAnnotation) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        String inputSemFormatacao = input.replaceAll("[^0-9]", "");

        return isCPF(inputSemFormatacao) || isCNPJ(inputSemFormatacao);
    }
}
package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public abstract class ValueObjectValidated {
    private static final Validator VALIDADOR = Validation.buildDefaultValidatorFactory().getValidator();

    public void validar() {
        final var violacoes = VALIDADOR.validate(this);

        if (!violacoes.isEmpty()) {
            throw new ConstraintViolationException(violacoes);
        }
    }
}

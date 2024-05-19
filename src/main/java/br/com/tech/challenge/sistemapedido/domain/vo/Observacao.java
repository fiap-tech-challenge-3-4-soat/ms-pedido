package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.constraints.Size;

import java.util.Objects;

public class Observacao extends ValueObjectValidated {
    @Size(max = 200)
    private final String observacao;

    public Observacao(String observacao) {
        this.observacao = observacao;
        this.validar();
    }

    public @Size(max = 200) String getObservacao() {
        return observacao;
    }
}

package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.constraints.Size;

public class Observacao extends ValueObjectValidated {
    @Size(max = 200)
    private final String valor;

    public Observacao(String observacao) {
        this.valor = observacao;
        this.validar();
    }

    public @Size(max = 200) String getObservacao() {
        return valor;
    }
}

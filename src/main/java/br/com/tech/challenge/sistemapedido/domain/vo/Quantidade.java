package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.constraints.Min;

public class Quantidade extends ValueObjectValidated {
    @Min(1)
    private final Integer valor;

    public Quantidade(Integer quantidade) {
        this.valor = quantidade;
        this.validar();
    }

    public Integer getQuantidade() {
        return valor;
    }
}

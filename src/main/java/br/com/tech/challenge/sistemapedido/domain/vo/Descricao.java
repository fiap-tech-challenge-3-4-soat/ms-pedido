package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.constraints.NotBlank;

public class Descricao extends ValueObjectValidated {
    @NotBlank
    private final String valor;

    public Descricao(String descricao) {
        this.valor = descricao;
        this.validar();
    }

    @Override
    public String toString() {
        return valor;
    }
}

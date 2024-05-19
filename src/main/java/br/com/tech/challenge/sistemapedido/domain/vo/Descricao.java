package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class Descricao extends ValueObjectValidated {
    @NotBlank
    private final String descricao;

    public Descricao(String descricao) {
        this.descricao = descricao;
        this.validar();
    }

    @Override
    public String toString() {
        return descricao;
    }
}

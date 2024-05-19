package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class Nome extends ValueObjectValidated {
    @NotBlank
    private final String nome;

    public Nome(String nome) {
        this.nome = nome;
        this.validar();
    }

    public @NotBlank String getNome() {
        return nome;
    }
}

package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.constraints.NotBlank;

public class Nome extends ValueObjectValidated {
    @NotBlank
    private final String valor;

    public Nome(String nome) {
        this.valor = nome;
        this.validar();
    }

    public @NotBlank String getNome() {
        return valor;
    }
}

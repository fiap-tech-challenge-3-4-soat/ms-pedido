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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nome nome1 = (Nome) o;
        return Objects.equals(nome, nome1.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return nome;
    }

}

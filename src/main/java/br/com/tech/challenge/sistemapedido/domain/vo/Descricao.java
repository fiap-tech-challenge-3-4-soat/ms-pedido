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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Descricao descricao1 = (Descricao) o;
        return Objects.equals(descricao, descricao1.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao);
    }

    @Override
    public String toString() {
        return descricao;
    }
}

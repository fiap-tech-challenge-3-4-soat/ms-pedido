package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.constraints.Min;

import java.util.Objects;

public class Quantidade extends ValueObjectValidated {
    @Min(1)
    private final Integer quantidade;

    public Quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        this.validar();
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantidade that = (Quantidade) o;
        return Objects.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantidade);
    }
}

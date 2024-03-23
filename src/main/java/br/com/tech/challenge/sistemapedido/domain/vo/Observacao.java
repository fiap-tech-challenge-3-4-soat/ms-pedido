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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Observacao that = (Observacao) o;
        return Objects.equals(observacao, that.observacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observacao);
    }

    @Override
    public String toString() {
        return observacao;
    }
}

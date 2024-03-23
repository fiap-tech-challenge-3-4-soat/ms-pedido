package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;
import java.util.Objects;

public class Preco extends ValueObjectValidated {
    @DecimalMin(value = "0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private final BigDecimal preco;

    public Preco(BigDecimal preco) {
        this.preco = preco;
        this.validar();
    }

    public BigDecimal getPreco() {
        return preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Preco preco1 = (Preco) o;
        return Objects.equals(preco, preco1.preco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preco);
    }
}

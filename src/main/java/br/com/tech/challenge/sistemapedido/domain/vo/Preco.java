package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public class Preco extends ValueObjectValidated {
    @NotNull
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0", inclusive = false)
    private final BigDecimal preco;

    public Preco(BigDecimal preco) {
        this.preco = preco;
        this.validar();
    }

    public BigDecimal getPreco() {
        return preco;
    }
}

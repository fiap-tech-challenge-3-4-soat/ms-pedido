package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class Preco extends ValueObjectValidated {
    @NotNull
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0", inclusive = false)
    private final BigDecimal valor;

    public Preco(BigDecimal preco) {
        this.valor = preco;
        this.validar();
    }

    public BigDecimal getPreco() {
        return valor;
    }
}

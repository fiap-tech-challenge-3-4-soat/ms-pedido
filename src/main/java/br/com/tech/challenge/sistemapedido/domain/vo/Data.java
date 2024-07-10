package br.com.tech.challenge.sistemapedido.domain.vo;

import java.time.LocalDateTime;

public class Data extends ValueObjectValidated {
    private final LocalDateTime valor;

    public Data(LocalDateTime data) {
        this.valor = data;
        this.validar();
    }

    public LocalDateTime getData() {
        return valor;
    }
}

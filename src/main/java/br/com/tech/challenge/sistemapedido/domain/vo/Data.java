package br.com.tech.challenge.sistemapedido.domain.vo;

import java.time.LocalDateTime;
import java.util.Objects;

public class Data extends ValueObjectValidated {
    private final LocalDateTime data;

    public Data(LocalDateTime data) {
        this.data = data;
        this.validar();
    }

    public LocalDateTime getData() {
        return data;
    }
}

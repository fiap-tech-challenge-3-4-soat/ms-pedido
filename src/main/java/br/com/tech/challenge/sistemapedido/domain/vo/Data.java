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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data1 = (Data) o;
        return Objects.equals(data, data1.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}

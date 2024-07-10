package br.com.tech.challenge.sistemapedido.application.response;

import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import lombok.Getter;

@Getter
public class StatusPedidoResponse {
    private final Boolean pagamentoAprovado;
    private final StatusPedido status;

    public StatusPedidoResponse(Boolean pagamentoAprovado, StatusPedido status) {
        this.pagamentoAprovado = pagamentoAprovado;
        this.status = status;
    }
}

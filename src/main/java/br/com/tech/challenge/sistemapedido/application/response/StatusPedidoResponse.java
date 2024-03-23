package br.com.tech.challenge.sistemapedido.application.response;

import lombok.Getter;

@Getter
public class StatusPedidoResponse {
    private final boolean pagamentoAprovado;

    public StatusPedidoResponse(boolean pagamentoAprovado) {
        this.pagamentoAprovado = pagamentoAprovado;
    }
}

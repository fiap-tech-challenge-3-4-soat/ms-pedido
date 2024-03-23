package br.com.tech.challenge.sistemapedido.application.response;

import lombok.Getter;

@Getter
public class CadastrarPedidoResponse {
    private Long idPedido;

    public CadastrarPedidoResponse(Long idPedido) {
        this.idPedido = idPedido;
    }
}

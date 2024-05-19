package br.com.tech.challenge.sistemapedido.application.response;

import br.com.tech.challenge.sistemapedido.application.dto.PedidoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PedidoResponse {
    private PedidoDTO pedido;

    public PedidoResponse(PedidoDTO pedido) {
        this.pedido = pedido;
    }
}

package br.com.tech.challenge.sistemapedido.application.response;

import br.com.tech.challenge.sistemapedido.application.dto.PedidoDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class ListarPedidosResponse {
    private List<PedidoDTO> pedidos;

    public ListarPedidosResponse(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }
}

package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import jakarta.inject.Named;

import java.util.List;

@Named
public class ListarPedidosUseCase {
    private final PedidoGateway pedidoGateway;

    public ListarPedidosUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public List<Pedido> executar() {
        return pedidoGateway.buscarTodos();
    }
}

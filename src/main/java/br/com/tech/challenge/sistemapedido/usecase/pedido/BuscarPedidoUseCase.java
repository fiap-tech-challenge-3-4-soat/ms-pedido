package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import jakarta.inject.Named;

@Named
public class BuscarPedidoUseCase {
    private final PedidoGateway pedidoGateway;

    public BuscarPedidoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public Pedido executar(Long id) {
        return pedidoGateway.buscarPorId(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }
}

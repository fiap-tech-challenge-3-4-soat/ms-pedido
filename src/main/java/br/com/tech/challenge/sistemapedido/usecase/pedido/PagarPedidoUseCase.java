package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoCanceladoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoJaPagoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;

public class PagarPedidoUseCase {
    private final PedidoGateway pedidoGateway;

    public PagarPedidoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public void executar(Long idPedido) {
        var pedido = pedidoGateway.buscarPorId(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));

        if (StatusPedido.CANCELADO.equals(pedido.getStatus())) {
            throw new PedidoCanceladoException(idPedido);
        }

        if (Boolean.TRUE.equals(pedido.estaPago())) {
            throw new PedidoJaPagoException(pedido.getId());
        }

        pedido.pagar();
        pedidoGateway.pagar(pedido);
    }
}

package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoCanceladoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoPagoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoStatusIncorretoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import jakarta.inject.Named;

@Named
public class AlterarStatusPedidoParaFinalizadoUseCase {
    private final PedidoGateway pedidoGateway;

    public AlterarStatusPedidoParaFinalizadoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public void executar(Long idPedido) {
        var pedido = pedidoGateway.buscarPorId(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));

        if (StatusPedido.CANCELADO.equals(pedido.getStatus())) {
            throw new PedidoCanceladoException(idPedido);
        }

        if (Boolean.FALSE.equals(pedido.estaPago())) {
            throw new PedidoNaoPagoException(pedido.getId());
        }

        if (!pedido.getStatus().equals(StatusPedido.PRONTO)) {
            throw new PedidoStatusIncorretoException("Pedido não está com o status Pronto");
        }

        pedido.finalizado();

        pedidoGateway.alterarStatus(pedido);
    }
}

package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoCanceladoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoPagoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoStatusIncorretoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import jakarta.inject.Named;

@Named
public class AlterarStatusPedidoParaProntoUseCase {
    private final PedidoGateway pedidoGateway;

    public AlterarStatusPedidoParaProntoUseCase(PedidoGateway pedidoGateway) {
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

        if (!pedido.getStatus().equals(StatusPedido.EM_PREPARACAO)) {
            throw new PedidoStatusIncorretoException("Pedido não está com o status Em Preparação");
        }

        pedido.pronto();

        pedidoGateway.alterarStatus(pedido);
    }
}

package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoPagoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoStatusIncorretoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import jakarta.inject.Named;

@Named
public class AlterarStatusPedidoParaEmPreparacao {
    private final PedidoGateway pedidoGateway;

    public AlterarStatusPedidoParaEmPreparacao(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public void executar(Long idPedido) {
        var pedido = pedidoGateway.buscarPorId(idPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(idPedido));

        if (Boolean.FALSE.equals(pedido.estaPago())) {
            throw new PedidoNaoPagoException(pedido.getId());
        }

        if (!pedido.getStatus().equals(StatusPedido.RECEBIDO)) {
            throw new PedidoStatusIncorretoException("Pedido não está com o status Recebido");
        }

        pedido.preparar();

        pedidoGateway.alterarStatus(pedido);
    }


}

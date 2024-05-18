package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.usecase.gateway.PagamentoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;

public class ConfirmarPagamentoUseCase {
    private final PagamentoGateway pagamentoGateway;
    private final PedidoGateway pedidoGateway;

    public ConfirmarPagamentoUseCase(PagamentoGateway pagamentoGateway, PedidoGateway pedidoGateway) {
        this.pagamentoGateway = pagamentoGateway;
        this.pedidoGateway = pedidoGateway;
    }

    public void executar(Long idExterno) {
        var idPedido = pagamentoGateway.confirmarPagamento(idExterno);

        var pagarPedidoUseCase = new PagarPedidoUseCase(this.pedidoGateway);

        pagarPedidoUseCase.executar(idPedido);
    }
}

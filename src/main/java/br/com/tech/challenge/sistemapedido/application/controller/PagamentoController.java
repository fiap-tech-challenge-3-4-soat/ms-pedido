package br.com.tech.challenge.sistemapedido.application.controller;

import br.com.tech.challenge.sistemapedido.usecase.gateway.PagamentoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import br.com.tech.challenge.sistemapedido.usecase.pedido.ConfirmarPagamentoUseCase;
import br.com.tech.challenge.sistemapedido.usecase.pedido.GerarPagamentoPorQrCodeUseCase;
import br.com.tech.challenge.sistemapedido.usecase.pedido.PagarPedidoUseCase;
import jakarta.inject.Named;

import java.io.File;

@Named
public class PagamentoController {
    private final PedidoGateway pedidoGateway;
    private final PagamentoGateway pagamentoGateway;

    public PagamentoController(PedidoGateway pedidoGateway, PagamentoGateway pagamentoGateway) {
        this.pedidoGateway = pedidoGateway;
        this.pagamentoGateway = pagamentoGateway;
    }

    public void receberConfirmacaoPagamento(Long idExterno) {
        var confirmarPagamentoUseCase = new ConfirmarPagamentoUseCase(this.pagamentoGateway, this.pedidoGateway);

        confirmarPagamentoUseCase.executar(idExterno);
    }

    public File gerarPagamentoPorQrCode(Long idPedido) {
        var gerarPagamentoPorQrCodeUseCase = new GerarPagamentoPorQrCodeUseCase(this.pagamentoGateway, this.pedidoGateway);

        return gerarPagamentoPorQrCodeUseCase.executar(idPedido);
    }

    public void pagar(Long idPedido) {
        var pagarPedidoUseCase = new PagarPedidoUseCase(this.pedidoGateway);

        pagarPedidoUseCase.executar(idPedido);
    }
}

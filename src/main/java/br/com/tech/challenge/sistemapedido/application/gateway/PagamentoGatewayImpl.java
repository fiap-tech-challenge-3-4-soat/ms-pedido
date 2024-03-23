package br.com.tech.challenge.sistemapedido.application.gateway;

import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PagamentoGateway;
import br.com.tech.challenge.sistemapedido.application.service.PagamentoService;
import jakarta.inject.Named;

import java.io.File;

@Named
public class PagamentoGatewayImpl implements PagamentoGateway {
    private final PagamentoService pagamentoService;

    public PagamentoGatewayImpl(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @Override
    public File gerarPagamentoPorQrCode(Pedido pedido) {
        return pagamentoService.gerarQrCode(pedido);
    }

    @Override
    public Long confirmarPagamento(Long idExterno) {
        return pagamentoService.confirmarPagamento(idExterno);
    }
}

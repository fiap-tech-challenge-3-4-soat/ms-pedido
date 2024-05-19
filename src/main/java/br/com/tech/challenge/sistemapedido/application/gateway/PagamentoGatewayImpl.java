package br.com.tech.challenge.sistemapedido.application.gateway;

import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PagamentoGateway;
import br.com.tech.challenge.sistemapedido.application.service.PagamentoService;
import jakarta.inject.Named;

import java.io.File;
import java.io.IOException;

@Named
public class PagamentoGatewayImpl implements PagamentoGateway {
    private final PagamentoService pagamentoService;

    public PagamentoGatewayImpl(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @Override
    public byte[] gerarPagamentoPorQrCode(Pedido pedido) {
        return pagamentoService.gerarQrCode(pedido);
    }
}

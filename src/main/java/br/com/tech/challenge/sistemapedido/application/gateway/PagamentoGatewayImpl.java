package br.com.tech.challenge.sistemapedido.application.gateway;

import br.com.tech.challenge.sistemapedido.application.service.PagamentoService;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PagamentoGateway;
import jakarta.inject.Named;

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

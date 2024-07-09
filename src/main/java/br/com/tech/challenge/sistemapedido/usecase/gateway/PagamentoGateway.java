package br.com.tech.challenge.sistemapedido.usecase.gateway;

import br.com.tech.challenge.sistemapedido.domain.Pedido;

public interface PagamentoGateway {
    byte[] gerarPagamentoPorQrCode(Pedido pedido);
}

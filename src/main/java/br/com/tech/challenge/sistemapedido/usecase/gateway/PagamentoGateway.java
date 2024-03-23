package br.com.tech.challenge.sistemapedido.usecase.gateway;

import br.com.tech.challenge.sistemapedido.domain.Pedido;

import java.io.File;

public interface PagamentoGateway {
    File gerarPagamentoPorQrCode(Pedido pedido);
    Long confirmarPagamento(Long idExterno);
}

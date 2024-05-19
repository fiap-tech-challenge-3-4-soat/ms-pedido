package br.com.tech.challenge.sistemapedido.usecase.gateway;

import br.com.tech.challenge.sistemapedido.domain.Pedido;

import java.io.File;
import java.io.IOException;

public interface PagamentoGateway {
    byte[] gerarPagamentoPorQrCode(Pedido pedido);
}

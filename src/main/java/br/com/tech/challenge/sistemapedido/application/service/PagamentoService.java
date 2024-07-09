package br.com.tech.challenge.sistemapedido.application.service;

import br.com.tech.challenge.sistemapedido.domain.Pedido;

public interface PagamentoService {
    byte[] gerarQrCode(Pedido pedido);
}

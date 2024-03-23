package br.com.tech.challenge.sistemapedido.application.service;

import br.com.tech.challenge.sistemapedido.domain.Pedido;

import java.io.File;

public interface PagamentoService {
    File gerarQrCode(Pedido pedido);
    Long confirmarPagamento(Long idExterno);
}

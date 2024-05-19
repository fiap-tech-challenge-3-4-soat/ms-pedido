package br.com.tech.challenge.sistemapedido.application.service;

import br.com.tech.challenge.sistemapedido.domain.Pedido;

import java.io.File;
import java.io.IOException;

public interface PagamentoService {
    byte[] gerarQrCode(Pedido pedido);
}

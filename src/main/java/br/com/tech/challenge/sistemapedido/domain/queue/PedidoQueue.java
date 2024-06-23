package br.com.tech.challenge.sistemapedido.domain.queue;

import br.com.tech.challenge.sistemapedido.domain.Pedido;

public interface PedidoQueue {
    void publicarPedidoCriado(Pedido pedido);
}

package br.com.tech.challenge.sistemapedido.domain.event;

import br.com.tech.challenge.sistemapedido.domain.Pedido;

public record PedidoPagoEvent(Pedido pedido) {}

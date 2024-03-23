package br.com.tech.challenge.sistemapedido.application.events;

import br.com.tech.challenge.sistemapedido.domain.event.PedidoPagoEvent;

public interface PedidoPagoEventPublisher {
    void publicar(PedidoPagoEvent event);
}

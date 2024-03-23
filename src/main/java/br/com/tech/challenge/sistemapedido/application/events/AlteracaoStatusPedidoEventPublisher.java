package br.com.tech.challenge.sistemapedido.application.events;

import br.com.tech.challenge.sistemapedido.domain.event.AlteracaoStatusPedidoEvent;

public interface AlteracaoStatusPedidoEventPublisher {
    void publicar(AlteracaoStatusPedidoEvent event);
}

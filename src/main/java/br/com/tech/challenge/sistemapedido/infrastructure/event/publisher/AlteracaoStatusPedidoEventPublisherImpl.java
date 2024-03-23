package br.com.tech.challenge.sistemapedido.infrastructure.event.publisher;

import br.com.tech.challenge.sistemapedido.application.events.AlteracaoStatusPedidoEventPublisher;
import br.com.tech.challenge.sistemapedido.domain.event.AlteracaoStatusPedidoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlteracaoStatusPedidoEventPublisherImpl implements AlteracaoStatusPedidoEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publicar(AlteracaoStatusPedidoEvent event) {
        eventPublisher.publishEvent(event);
    }
}

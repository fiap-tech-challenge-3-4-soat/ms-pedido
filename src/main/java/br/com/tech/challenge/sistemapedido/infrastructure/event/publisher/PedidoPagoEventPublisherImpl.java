package br.com.tech.challenge.sistemapedido.infrastructure.event.publisher;

import br.com.tech.challenge.sistemapedido.application.events.PedidoPagoEventPublisher;
import br.com.tech.challenge.sistemapedido.domain.event.PedidoPagoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoPagoEventPublisherImpl implements PedidoPagoEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publicar(PedidoPagoEvent event) {
        eventPublisher.publishEvent(event);
    }
}

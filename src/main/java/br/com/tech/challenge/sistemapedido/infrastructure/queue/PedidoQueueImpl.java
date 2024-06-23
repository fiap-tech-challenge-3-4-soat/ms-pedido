package br.com.tech.challenge.sistemapedido.infrastructure.queue;

import br.com.tech.challenge.sistemapedido.application.mapper.PedidoDataMapper;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.exception.InternalErrorException;
import br.com.tech.challenge.sistemapedido.domain.queue.PedidoQueue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class PedidoQueueImpl implements PedidoQueue {
    @Value("${queue.filas.pedidos_criados}")
    private String pedidosCriados;

    @Value("${queue.exchange.fanoutPedido}")
    private String queueExchange;

    private final RabbitTemplate rabbitTemplate;
    private final PedidoDataMapper pedidoDataMapper;
    private final ObjectMapper jsonMapper;

    @Override
    public void publicarPedidoCriado(Pedido pedido) {
        try {
            var pedidoJson = jsonMapper.writeValueAsString(pedidoDataMapper.toDTO(pedido));
            rabbitTemplate.convertAndSend(queueExchange, "", pedidoJson);
            log.info(String.format("Publicação na fila %s executada", pedidosCriados));
        } catch (Exception exception) {
            log.error(exception);
            throw new InternalErrorException("Não foi possível enviar para a fila de pedidos");
        }
    }
}

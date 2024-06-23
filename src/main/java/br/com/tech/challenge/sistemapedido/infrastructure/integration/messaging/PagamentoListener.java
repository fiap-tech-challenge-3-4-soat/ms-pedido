package br.com.tech.challenge.sistemapedido.infrastructure.integration.messaging;

import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.transfer.PagamentoConfirmadoTO;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.transfer.StatusPagamentoTO;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class PagamentoListener {
    private final ObjectMapper objectMapper;
    private final PedidoGateway pedidoGateway;

    @RabbitListener(queues = {"${queue.filas.pagamentos_confirmados}"})
    public void receive(String message) throws JsonProcessingException {
        var pagamentoConfirmadoTO = objectMapper.readValue(message, PagamentoConfirmadoTO.class);

        if (pagamentoConfirmadoTO.statusPagamento().equals(StatusPagamentoTO.PAGO)) {
            var pedido = pedidoGateway.buscarPorId(pagamentoConfirmadoTO.idPedido())
                    .orElseThrow(() -> new PedidoNaoEncontradoException(pagamentoConfirmadoTO.idPedido()));

            pedido.pagar();
            pedidoGateway.pagar(pedido);
            log.info(String.format("Pedido %s alterado para o status pago", pedido.getId()));
        }
    }
}

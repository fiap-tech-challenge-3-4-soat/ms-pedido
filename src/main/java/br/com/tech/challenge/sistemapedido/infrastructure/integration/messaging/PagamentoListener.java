package br.com.tech.challenge.sistemapedido.infrastructure.integration.messaging;

import br.com.tech.challenge.sistemapedido.application.dto.PedidoDTO;
import br.com.tech.challenge.sistemapedido.application.events.PedidoPagoEventPublisher;
import br.com.tech.challenge.sistemapedido.application.repository.PedidoRepository;
import br.com.tech.challenge.sistemapedido.application.service.NotificacaoService;
import br.com.tech.challenge.sistemapedido.domain.event.PedidoPagoEvent;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.transfer.PagamentoConfirmadoTO;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.transfer.StatusPagamentoTO;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.UsuarioRepositoryJpa;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class PagamentoListener {
    private final ObjectMapper objectMapper;
    private final PedidoRepository pedidoRepository;
    private final PedidoPagoEventPublisher pedidoPagoPublisher;
    private final NotificacaoService notificacaoService;

    @RabbitListener(queues = {"${queue.filas.pagamentos_confirmados}"})
    public void receberPagamentosConfirmado(String message) throws JsonProcessingException {
        var pagamentoConfirmadoTO = objectMapper.readValue(message, PagamentoConfirmadoTO.class);

        if (pagamentoConfirmadoTO.statusPagamento().equals(StatusPagamentoTO.PAGO)) {
            var pedido = pedidoRepository.findById(pagamentoConfirmadoTO.idPedido())
                    .orElseThrow(() -> new PedidoNaoEncontradoException(pagamentoConfirmadoTO.idPedido()));

            pedido.pagar();
            pedidoRepository.save(pedido);
            pedidoPagoPublisher.publicar(new PedidoPagoEvent(pedido));
            notificacaoService.notificar(pedido.getUsuario(), "Pagamento confirmado", "Pagamento realizado com sucesso.");
            log.info(String.format("Pedido %s alterado para o status pago", pedido.getId()));
        }
    }

    @RabbitListener(queues = {"${queue.filas.pagamentos_nao_gerados}"})
    public void receberPagamentosNaoGerados(String message) throws JsonProcessingException {
        var pedidoDTO = objectMapper.readValue(message, PedidoDTO.class);
        var pedido = pedidoRepository.findById(pedidoDTO.id())
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoDTO.id()));

        pedido.cancelar();
        pedidoRepository.save(pedido);
        notificacaoService.notificar(pedido.getUsuario(), "Pedido cancelado", "Não foi possível criar um pagamento para seu pedido. Por favor, tente mais tarde.");
        log.info(String.format("Pedido %s alterado para o status cancelado devido a problemas com o pagamento", pedido.getId()));
    }
}

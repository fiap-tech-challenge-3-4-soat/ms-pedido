package br.com.tech.challenge.sistemapedido.infrastructure.integration.messaging;

import br.com.tech.challenge.sistemapedido.application.dto.PedidoDTO;
import br.com.tech.challenge.sistemapedido.application.events.PedidoPagoEventPublisher;
import br.com.tech.challenge.sistemapedido.application.repository.PedidoRepository;
import br.com.tech.challenge.sistemapedido.application.service.NotificacaoService;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.event.PedidoPagoEvent;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.transfer.PagamentoConfirmadoTO;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.transfer.StatusPagamentoTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoListenerTest {
    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private PagamentoConfirmadoTO pagamentoConfirmadoTO;

    @Mock
    private PedidoDTO pedidoDTO;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private Pedido pedido;

    @Mock
    private PedidoPagoEventPublisher pedidoPagoPublisher;

    @Mock
    private NotificacaoService notificacaoService;

    @InjectMocks
    private PagamentoListener underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void deveReceberConfirmacaoDePagamentoComSucesso() throws JsonProcessingException {
        var message = "message";
        when(objectMapper.readValue(message, PagamentoConfirmadoTO.class))
                .thenReturn(pagamentoConfirmadoTO);
        when(pagamentoConfirmadoTO.statusPagamento())
                .thenReturn(StatusPagamentoTO.PAGO);
        when(pedidoRepository.findById(anyLong()))
                .thenReturn(Optional.of(pedido));

        underTest.receberPagamentosConfirmado("message");

        verify(pedido).pagar();
        verify(pedidoPagoPublisher).publicar(any(PedidoPagoEvent.class));
        verify(pedidoRepository).findById(anyLong());
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    void deveFalharQuandoNaoEncontrarOPedidoDoPagamento() throws JsonProcessingException {
        var message = "message";
        when(objectMapper.readValue(message, PagamentoConfirmadoTO.class))
                .thenReturn(pagamentoConfirmadoTO);
        when(pagamentoConfirmadoTO.statusPagamento())
                .thenReturn(StatusPagamentoTO.PAGO);
        when(pedidoRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(PedidoNaoEncontradoException.class, () ->
                underTest.receberPagamentosConfirmado("message"));

        verify(pedido, never()).pagar();
        verify(pedidoPagoPublisher, never()).publicar(any(PedidoPagoEvent.class));
        verify(pedidoRepository).findById(anyLong());
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void naoDeveReceberConfirmacaoDePagamentoQuandoOStatusForAbert() throws JsonProcessingException {
        var message = "message";
        when(objectMapper.readValue(message, PagamentoConfirmadoTO.class))
                .thenReturn(pagamentoConfirmadoTO);
        when(pagamentoConfirmadoTO.statusPagamento())
                .thenReturn(StatusPagamentoTO.ABERTO);

        underTest.receberPagamentosConfirmado("message");

        verify(pedido, never()).pagar();
        verify(pedidoPagoPublisher, never()).publicar(any(PedidoPagoEvent.class));
        verify(pedidoRepository, never()).findById(anyLong());
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void deveCancelarPedidosQueNaoTiveramOPagamentoGerado() throws JsonProcessingException {
        var message = "message";
        when(objectMapper.readValue(message, PedidoDTO.class))
                .thenReturn(pedidoDTO);
        when(pedidoRepository.findById(anyLong()))
                .thenReturn(Optional.of(pedido));

        underTest.receberPagamentosNaoGerados("message");

        verify(pedido).cancelar();
        verify(pedidoRepository).findById(anyLong());
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    void naoDeveCancelarQuandoNaoEncontrarOPedido() throws JsonProcessingException {
        var message = "message";
        when(objectMapper.readValue(message, PedidoDTO.class))
                .thenReturn(pedidoDTO);
        when(pedidoRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(PedidoNaoEncontradoException.class, () ->
                underTest.receberPagamentosNaoGerados("message"));

        verify(pedido, never()).cancelar();
        verify(pedidoRepository).findById(anyLong());
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }
}
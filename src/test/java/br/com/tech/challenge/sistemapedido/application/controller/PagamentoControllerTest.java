package br.com.tech.challenge.sistemapedido.application.controller;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PagamentoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoControllerTest {
    private Pedido pedido;

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private PagamentoGateway pagamentoGateway;

    @InjectMocks
    private PagamentoController underTest;

    @BeforeEach
    void setUp() {
        var itemPedido = TestObjects.getItemPedido();
        this.pedido = TestObjects.getPedido();
        pedido.adicionarItem(itemPedido);
    }

    @Test
    void deveriaGerarPagamentoComSucesso() {
        when(pedidoGateway.buscarPorId(anyLong()))
                .thenReturn(Optional.of(this.pedido));
        when(pagamentoGateway.gerarPagamentoPorQrCode(any(Pedido.class)))
                .thenReturn(new byte[1]);

        underTest.gerarPagamentoPorQrCode(1L);

        verify(pedidoGateway).buscarPorId(anyLong());
        verify(pagamentoGateway).gerarPagamentoPorQrCode(any(Pedido.class));
    }

    @Test
    void deveriaPagarPedidoPedidoComSucesso() {
        when(pedidoGateway.buscarPorId(anyLong()))
                .thenReturn(Optional.of(this.pedido));
        doNothing().when(pedidoGateway).pagar(any(Pedido.class));

        underTest.pagar(1L);

        verify(pedidoGateway).buscarPorId(anyLong());
        verify(pedidoGateway).pagar(any(Pedido.class));
    }
}
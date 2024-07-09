package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoJaPagoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PagamentoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
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
class GerarPagamentoPorQrCodeUseCaseTest {
    @Mock
    private PagamentoGateway pagamentoGateway;
    @Mock
    private PedidoGateway pedidoGateway;
    @InjectMocks
    private GerarPagamentoPorQrCodeUseCase underTest;

    @Test
    void deveriaGerarPagamentoPorQrCodeComSucesso() {
        var pedido = TestObjects.getPedido();
        var idPedido = 1L;

        when(pedidoGateway.buscarPorId(anyLong()))
                .thenReturn(Optional.of(pedido));
        when(pagamentoGateway.gerarPagamentoPorQrCode(any(Pedido.class)))
                .thenReturn(new byte[1]);

        underTest.executar(idPedido);

        verify(pedidoGateway).buscarPorId(anyLong());
        verify(pagamentoGateway).gerarPagamentoPorQrCode(pedido);
    }

    @Test
    void deveriaLancarExcecaoQuandoNaoEncontrarOPedido() {
        var idPedido = 1L;

        when(pedidoGateway.buscarPorId(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(PedidoNaoEncontradoException.class, () ->
                underTest.executar(idPedido));

        verify(pedidoGateway).buscarPorId(anyLong());
        verify(pagamentoGateway, never())
                .gerarPagamentoPorQrCode(any(Pedido.class));
    }

    @Test
    void deveriaLancarExcecaoQuandoOPedidoJaEstaPago() {
        var pedido = TestObjects.getPedido();
        var idPedido = 1L;

        pedido.pagar();

        when(pedidoGateway.buscarPorId(anyLong()))
                .thenReturn(Optional.of(pedido));

        assertThrows(PedidoJaPagoException.class, () ->
                underTest.executar(idPedido));

        verify(pedidoGateway).buscarPorId(anyLong());
        verify(pagamentoGateway, never())
                .gerarPagamentoPorQrCode(any(Pedido.class));
    }
}
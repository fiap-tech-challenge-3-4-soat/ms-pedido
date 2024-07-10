package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoCanceladoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoPagoException;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoStatusIncorretoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlterarStatusPedidoParaFinalizadoUseCaseTest {
    @Mock
    private PedidoGateway gateway;
    @InjectMocks
    private AlterarStatusPedidoParaFinalizadoUseCase underTest;

    @Test
    void deveriaAlterarStatusParaEmFinalizadoComSucesso() {
        var pedido = TestObjects.getPedido();
        var idPedido = 1L;

        pedido.pagar();
        pedido.pronto();

        when(gateway.buscarPorId(idPedido))
                .thenReturn(Optional.of(pedido));

        underTest.executar(idPedido);

        verify(gateway).alterarStatus(any(Pedido.class));
    }

    @Test
    void deveriaLancarExcecaoQuandoOPedidoNaoEstaPago() {
        var pedido = TestObjects.getPedido();
        var idPedido = 1L;

        when(gateway.buscarPorId(idPedido))
                .thenReturn(Optional.of(pedido));

        assertThrows(PedidoNaoPagoException.class, () ->
                underTest.executar(idPedido));

        verify(gateway, never()).alterarStatus(any(Pedido.class));
    }

    @Test
    void deveriaLancarExcecaoQuandoOPedidoNaoEstaComStatusPronto() {
        var pedido = TestObjects.getPedido();
        var idPedido = 1L;

        pedido.pagar();

        when(gateway.buscarPorId(idPedido))
                .thenReturn(Optional.of(pedido));

        assertThrows(PedidoStatusIncorretoException.class, () ->
                underTest.executar(idPedido));

        verify(gateway, never()).alterarStatus(any(Pedido.class));
    }

    @Test
    void deveriaLancarExcecaoQuandoNaoEncontrarOPedido() {
        var idPedido = 1L;

        when(gateway.buscarPorId(idPedido))
                .thenReturn(Optional.empty());

        assertThrows(PedidoNaoEncontradoException.class, () ->
                underTest.executar(idPedido));

        verify(gateway).buscarPorId(idPedido);
        verify(gateway, never()).alterarStatus(any(Pedido.class));
    }

    @Test
    void deveriaLancarExcecaoQuandoOPedidoEstiverCancelado() {
        var pedido = TestObjects.getPedido();
        var idPedido = pedido.getId();
        pedido.cancelar();

        when(gateway.buscarPorId(pedido.getId()))
                .thenReturn(Optional.of(pedido));

        assertThrows(PedidoCanceladoException.class, () ->
                underTest.executar(idPedido));

        verify(gateway).buscarPorId(idPedido);
        verify(gateway, never()).alterarStatus(any(Pedido.class));
    }
}
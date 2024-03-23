package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.domain.exception.PedidoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPedidoUseCaseTest {
    @Mock
    private PedidoGateway gateway;
    @InjectMocks
    private BuscarPedidoUseCase underTest;

    @Test
    void deveriaBuscarPedidoComSucesso() {
        var pedido = TestObjects.getPedido();
        var idPedido = 1L;

        pedido.pagar();
        pedido.preparar();

        when(gateway.buscarPorId(idPedido))
                .thenReturn(Optional.of(pedido));

        underTest.executar(idPedido);

        verify(gateway).buscarPorId(anyLong());
    }

    @Test
    void deveriaLancarExcecaoQuandoNaoEncontrarOPedido() {
        var idPedido = 1L;

        when(gateway.buscarPorId(idPedido))
                .thenReturn(Optional.empty());

        assertThrows(PedidoNaoEncontradoException.class, () ->
                underTest.executar(idPedido));

        verify(gateway).buscarPorId(idPedido);
    }
}
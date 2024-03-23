package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PagamentoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfirmarPagamentoUseCaseTest {
    @Mock
    private PagamentoGateway pagamentoGateway;
    @Mock
    private PedidoGateway pedidoGateway;
    @InjectMocks
    private ConfirmarPagamentoUseCase underTest;

    @Test
    void deveriaConfirmarPagamentoPedidoComSucesso() {
        var pedido = TestObjects.getPedido();
        var idExterno = 1L;

        when(pagamentoGateway.confirmarPagamento(idExterno))
                .thenReturn(2L);
        when(pedidoGateway.buscarPorId(anyLong()))
                .thenReturn(Optional.of(pedido));

        underTest.executar(idExterno);

        verify(pagamentoGateway).confirmarPagamento(anyLong());
        verify(pedidoGateway).buscarPorId(anyLong());
    }
}
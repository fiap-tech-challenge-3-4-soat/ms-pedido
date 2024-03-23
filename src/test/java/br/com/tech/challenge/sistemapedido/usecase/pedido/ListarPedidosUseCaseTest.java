package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarPedidosUseCaseTest {
    @Mock
    private PedidoGateway pedidoGateway;
    @InjectMocks
    private ListarPedidosUseCase underTest;

    @Test
    void deveriaListarPedidosComSucesso() {
        var pedido = TestObjects.getPedido();

        when(pedidoGateway.buscarTodos())
                .thenReturn(List.of(pedido));

        var response = underTest.executar();

        assertThat(response).hasSize(1);
        verify(pedidoGateway).buscarTodos();
    }
}
package br.com.tech.challenge.sistemapedido.application.controller;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.application.dto.PedidoDTO;
import br.com.tech.challenge.sistemapedido.application.mapper.PedidoDataMapper;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilaControllerTest {
    private Pedido pedido;

    private PedidoDTO pedidoDTO;

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private PedidoDataMapper pedidoMapper;

    @InjectMocks
    private FilaController underTest;

    @BeforeEach
    void setUp() {
        this.pedido = TestObjects.getPedido();
        this.pedidoDTO = TestObjects.getPedidoDTO(pedido);
    }

    @Test
    void deveriaListarPedidosDoRestauranteComSucesso() {
        when(pedidoGateway.buscarFilaRestaurante())
                .thenReturn(List.of(this.pedido));
        when(pedidoMapper.toList(anyList()))
                .thenReturn(List.of(this.pedidoDTO));

        var response = underTest.filaRestaurante();

        assertThat(response.getPedidos()).isNotEmpty();

        verify(pedidoGateway).buscarFilaRestaurante();
        verify(pedidoMapper).toList(anyList());
    }

    @Test
    void deveriaListarPedidosDoClienteComSucesso() {
        when(pedidoGateway.buscarFilaCliente())
                .thenReturn(List.of(this.pedido));
        when(pedidoMapper.toList(anyList()))
                .thenReturn(List.of(this.pedidoDTO));

        var response = underTest.filaCLiente();

        assertThat(response.getPedidos()).isNotEmpty();

        verify(pedidoGateway).buscarFilaCliente();
        verify(pedidoMapper).toList(anyList());
    }
}
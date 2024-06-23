package br.com.tech.challenge.sistemapedido.application.controller;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.application.dto.PedidoDTO;
import br.com.tech.challenge.sistemapedido.application.mapper.ItemPedidoDataMapper;
import br.com.tech.challenge.sistemapedido.application.mapper.PedidoDataMapper;
import br.com.tech.challenge.sistemapedido.application.request.PedidoRequest;
import br.com.tech.challenge.sistemapedido.domain.*;
import br.com.tech.challenge.sistemapedido.domain.queue.PedidoQueue;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {
    private Pedido pedido;

    private ItemPedido itemPedido;

    private Produto produto;

    private PedidoDTO pedidoDTO;

    @Mock
    private Usuario usuario;

    @Mock
    private ProdutoGateway produtoGateway;

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private PedidoQueue pedidoQueue;

    @Mock
    private PedidoDataMapper pedidoMapper;

    @Mock
    private ItemPedidoDataMapper itemPedidoDataMapper;

    @Mock
    private PedidoRequest request;

    @InjectMocks
    private PedidoController underTest;

    @BeforeEach
    void setUp() {
        this.itemPedido = TestObjects.getItemPedido();
        this.pedido = TestObjects.getPedido();
        pedido.adicionarItem(itemPedido);
        this.produto = TestObjects.getProduto("Produto Teste");
        this.pedidoDTO = TestObjects.getPedidoDTO(pedido);
    }

    @Test
    void deveriaCriarPedidoSemCpfComSucesso() {
        when(itemPedidoDataMapper.toDomainList(anyList()))
                .thenReturn(List.of(this.itemPedido));
        when(pedidoGateway.salvar(any(Pedido.class)))
                .thenReturn(this.pedido);
        when(produtoGateway.buscarPorId(anyLong()))
                .thenReturn(this.produto);
        doNothing().when(pedidoQueue).publicarPedidoCriado(any(Pedido.class));

        var response = underTest.criar(request);

        assertThat(response.getIdPedido()).isEqualTo(pedido.getId());

        verify(request).itens();
        verify(pedidoGateway).salvar(any(Pedido.class));
        verify(produtoGateway).buscarPorId(anyLong());
        verify(usuarioGateway, never()).buscarPorCpf(anyString());
        verify(pedidoQueue).publicarPedidoCriado(any(Pedido.class));
    }

    @Test
    void deveriaCriarPedidoComCpfComSucesso() {
        var cpf = "19100000000";
        when(request.cpf())
                .thenReturn(cpf);
        when(itemPedidoDataMapper.toDomainList(anyList()))
                .thenReturn(List.of(this.itemPedido));
        when(pedidoGateway.salvar(any(Pedido.class)))
                .thenReturn(this.pedido);
        when(produtoGateway.buscarPorId(anyLong()))
                .thenReturn(this.produto);
        when(usuarioGateway.buscarPorCpf(cpf))
                .thenReturn(Optional.of(usuario));
        doNothing().when(pedidoQueue).publicarPedidoCriado(any(Pedido.class));

        var response = underTest.criar(request);

        assertThat(response.getIdPedido()).isEqualTo(pedido.getId());

        verify(request).itens();
        verify(pedidoGateway).salvar(any(Pedido.class));
        verify(produtoGateway).buscarPorId(anyLong());
        verify(usuarioGateway).buscarPorCpf(cpf);
        verify(pedidoQueue).publicarPedidoCriado(any(Pedido.class));
    }

    @Test
    void deveriaListarPedidosComSucesso() {
        when(pedidoGateway.buscarTodos())
                .thenReturn(List.of(pedido));
        when(pedidoMapper.toList(anyList()))
                .thenReturn(List.of(this.pedidoDTO));

        var response = underTest.listar();

        assertThat(response.getPedidos()).contains(this.pedidoDTO);

        verify(pedidoGateway).buscarTodos();
        verify(pedidoMapper).toList(anyList());
    }

    @Test
    void deveriaVerificarStatusDoPedidoComSucesso() {
        var idPedido = 1L;
        this.pedido.pagar();

        when(pedidoGateway.buscarPorId(idPedido))
                .thenReturn(Optional.of(this.pedido));

        var response = underTest.verificarStatus(idPedido);

        assertThat(response.isPagamentoAprovado()).isTrue();

        verify(pedidoGateway).buscarPorId(idPedido);
    }

    @Test
    void deveriaAlterarStatusDoPedidoParaEmPreparacaoComSucesso() {
        var idPedido = 1L;
        this.pedido.pagar();

        when(pedidoGateway.buscarPorId(idPedido))
                .thenReturn(Optional.of(this.pedido));

        underTest.preparacao(idPedido);

        assertThat(pedido.getStatus()).isEqualTo(StatusPedido.EM_PREPARACAO);

        verify(pedidoGateway).buscarPorId(idPedido);
    }

    @Test
    void deveriaAlterarStatusDoPedidoParaProntoComSucesso() {
        var idPedido = 1L;
        this.pedido.pagar();
        this.pedido.preparar();

        when(pedidoGateway.buscarPorId(idPedido))
                .thenReturn(Optional.of(this.pedido));

        underTest.pronto(idPedido);

        assertThat(pedido.getStatus()).isEqualTo(StatusPedido.PRONTO);

        verify(pedidoGateway).buscarPorId(idPedido);
    }

    @Test
    void deveriaAlterarStatusDoPedidoParaFinalizadoComSucesso() {
        var idPedido = 1L;
        this.pedido.pagar();
        this.pedido.pronto();

        when(pedidoGateway.buscarPorId(idPedido))
                .thenReturn(Optional.of(this.pedido));

        underTest.finalizado(idPedido);

        assertThat(pedido.getStatus()).isEqualTo(StatusPedido.FINALIZADO);

        verify(pedidoGateway).buscarPorId(idPedido);
    }

    @Test
    void deveriaConsultarPedidoComSucesso() {
        var idPedido = 1L;

        when(pedidoGateway.buscarPorId(idPedido))
                .thenReturn(Optional.of(this.pedido));
        when(pedidoMapper.toDTO(any(Pedido.class)))
                .thenReturn(pedidoDTO);

        var response = underTest.consultar(idPedido);

        assertThat(response.getPedido().id()).isEqualTo(this.pedidoDTO.id());

        verify(pedidoGateway).buscarPorId(idPedido);
        verify(pedidoMapper).toDTO(any(Pedido.class));
    }
}
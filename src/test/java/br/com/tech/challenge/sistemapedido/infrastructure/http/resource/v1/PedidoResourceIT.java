package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.application.dto.ItemProdutoDTO;
import br.com.tech.challenge.sistemapedido.application.request.PedidoRequest;
import br.com.tech.challenge.sistemapedido.domain.Papel;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.domain.queue.PedidoQueue;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.rest.msproduto.ConsultarProdutoResponse;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.rest.msproduto.MSProdutoHttpClient;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.*;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoResourceIT {
    private final String PATH = "/api/v1/pedidos";

    private Pedido pedido;

    @Autowired
    private UsuarioGateway usuarioGateway;

    @Autowired
    private PedidoGateway pedidoGateway;

    @Autowired
    private PedidoRepositoryJpa pedidoRepositoryJpa;

    @Autowired
    private ItemPedidoRepositoryJpa itemPedidoRepositoryJpa;

    @Autowired
    private PapelRepositoryJpa papelRepositoryJpa;

    @Autowired
    private FilaClienteRepositoryJpa filaClienteRepositoryJpa;

    @Autowired
    private FilaRestauranteRepositoryJpa filaRestauranteRepositoryJpa;

    @Autowired
    private UsuarioRepositoryJpa usuarioRepositoryJpa;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockBean
    private MSProdutoHttpClient msProdutoHttpClient;

    @MockBean
    private PedidoQueue pedidoQueue;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        var usuario = obterUsuario();
        var itemPedido = TestObjects.getItemPedido();
        this.pedido = TestObjects.getPedidoComUsuario(usuario);
        pedido.adicionarItem(itemPedido);
        this.pedido = pedidoGateway.salvar(this.pedido);
    }

    @AfterEach
    void tearDown() {
        itemPedidoRepositoryJpa.deleteAll();
        filaClienteRepositoryJpa.deleteAll();
        filaRestauranteRepositoryJpa.deleteAll();
        pedidoRepositoryJpa.deleteAll();
        usuarioRepositoryJpa.deleteAll();
        papelRepositoryJpa.deleteAll();
    }

    @Test
    void deveriaCriarUmPedidoComSucesso() throws Exception {
        var itemProdutoDTO = new ItemProdutoDTO(1L, 1, "Observação Teste");
        var produtoDTO = TestObjects.getProdutoDTO();
        var request = new PedidoRequest(List.of(itemProdutoDTO), null);
        var jsonRequest = jsonMapper.writeValueAsString(request);
        var consultaProdutoResponse = new ConsultarProdutoResponse(produtoDTO);

        when(msProdutoHttpClient.obterProduto(anyLong()))
                .thenReturn(ResponseEntity.of(Optional.of(consultaProdutoResponse)));
        doNothing().when(pedidoQueue).publicarPedidoCriado(any(Pedido.class));

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPedido").isNumber());

        verify(pedidoQueue).publicarPedidoCriado(any(Pedido.class));
    }

    @Test
    void deveriaFalharAoNaoConseguirObterDadosDoPedido() throws Exception {
        var itemProdutoDTO = new ItemProdutoDTO(1L, 1, "Observação Teste");
        var produtoDTO = TestObjects.getProdutoDTO();
        var request = new PedidoRequest(List.of(itemProdutoDTO), null);
        var jsonRequest = jsonMapper.writeValueAsString(request);

        when(msProdutoHttpClient.obterProduto(anyLong()))
                .thenReturn(ResponseEntity.of(Optional.empty()));

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensagem").value(String.format("Não foi possível obter o produto com o ID: %d", produtoDTO.id())));
    }

    @Test
    void deveriaListarPedidosComSucesso() throws Exception {
        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos").isArray())
                .andExpect(jsonPath("$.pedidos[0].id").value(this.pedido.getId()));
    }

    @Test
    void deveriaAlterarStatusDoPedidoParaEmPreparacaoComSucesso() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.salvar(this.pedido);

        mockMvc.perform(patch(PATH + "/preparacao/{idPedido}", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void deveriaFalharAoAlterarStatusDoPedidoParaEmPreparacaoComPedidoNaoPago() throws Exception {
        this.pedidoGateway.salvar(this.pedido);

        mockMvc.perform(patch(PATH + "/preparacao/{idPedido}", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.mensagem").value(String.format("Pedido não está pago id: %d", pedido.getId())));
    }

    @Test
    void deveriaFalharAoAlterarStatusDoPedidoParaEmPreparacaoComPedidoInvalido() throws Exception {
        var idPedido = 99999;

        mockMvc.perform(patch(PATH + "/preparacao/{idPedido}", idPedido)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(String.format("Pedido não encontrado id: %d", idPedido)));
    }

    @Test
    void deveriaAlterarStatusDoPedidoParaProntoComSucesso() throws Exception {
        this.pedido.pagar();
        this.pedido.preparar();
        this.pedidoGateway.salvar(this.pedido);

        mockMvc.perform(patch(PATH + "/pronto/{idPedido}", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void deveriaFalharAoAlterarStatusDoPedidoParaProntoComPedidoNaoPago() throws Exception {
        mockMvc.perform(patch(PATH + "/pronto/{idPedido}", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.mensagem").value(String.format("Pedido não está pago id: %d", pedido.getId())));
    }

    @Test
    void deveriaFalharAoAlterarStatusDoPedidoParaProntoComPedidoNaoIniciadoAPreparacao() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.salvar(this.pedido);

        mockMvc.perform(patch(PATH + "/pronto/{idPedido}", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.mensagem").value("Pedido não está com o status Em Preparação"));
    }

    @Test
    void deveriaFalharAoAlterarStatusDoPedidoParaProntoComPedidoInvalido() throws Exception {
        var idPedido = 99999;

        mockMvc.perform(patch(PATH + "/pronto/{idPedido}", idPedido)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(String.format("Pedido não encontrado id: %d", idPedido)));
    }

    @Test
    void deveriaAlterarStatusDoPedidoParaFinalizadoComSucesso() throws Exception {
        this.pedido.pagar();
        this.pedido.pronto();
        this.pedidoGateway.salvar(this.pedido);

        mockMvc.perform(patch(PATH + "/finalizado/{idPedido}", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void deveriaFalharAoAlterarStatusDoPedidoParaFinalizadoComPedidoNaoPago() throws Exception {
        mockMvc.perform(patch(PATH + "/finalizado/{idPedido}", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.mensagem").value(String.format("Pedido não está pago id: %d", pedido.getId())));
    }

    @Test
    void deveriaFalharAoAlterarStatusDoPedidoParaFinalizadoComPedidoNaoPronto() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.salvar(this.pedido);

        mockMvc.perform(patch(PATH + "/finalizado/{idPedido}", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.mensagem").value("Pedido não está com o status Pronto"));
    }

    @Test
    void deveriaFalharAoAlterarStatusDoPedidoParaFinalizadoComPedidoInvalido() throws Exception {
        var idPedido = 99999;

        mockMvc.perform(patch(PATH + "/finalizado/{idPedido}", idPedido)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(String.format("Pedido não encontrado id: %d", idPedido)));
    }

    @Test
    void deveriaVerificarStatusDoPedidoComSucesso() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.salvar(this.pedido);

        mockMvc.perform(get(PATH + "/{idPedido}/status", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pagamentoAprovado").value(Boolean.TRUE));
    }

    @Test
    void deveriaFalharAoVerificarStatusComPedidoInvalido() throws Exception {
        var idPedido = 99999;
        this.pedido.pagar();
        this.pedidoGateway.salvar(this.pedido);

        mockMvc.perform(get(PATH + "/{99999}/status", idPedido)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(String.format("Pedido não encontrado id: %d", idPedido)));
    }

    @Test
    void deveriaConsultarUmPedidoComSucesso() throws Exception {
        mockMvc.perform(get(PATH + "/{idPedido}", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedido.id").value(this.pedido.getId()))
                .andExpect(jsonPath("$.pedido.status").value(this.pedido.getStatus().name()));
    }

    @Test
    void deveriaFalharQuandoConsultarUmPedidoInvalido() throws Exception {
        var idPedido = 99999;
        mockMvc.perform(get(PATH + "/{idPedido}", idPedido)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value(String.format("Pedido não encontrado id: %d", idPedido)));
    }

    private Usuario obterUsuario() {
        var usuario = TestObjects.getUsuario();
        usuario.setPapeis(Set.of(new Papel("Teste")));

        return usuarioGateway.salvar(usuario);
    }
}
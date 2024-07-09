package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.domain.Papel;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.domain.queue.PedidoQueue;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.rest.mspagamento.MSPagamentoHttpClient;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.*;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
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

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PagamentoResourceIT {
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

    @MockBean
    private MSPagamentoHttpClient msPagamentoHttpClient;

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
    void deveriaGerarPagamentoComSucesso() throws Exception {
        when(msPagamentoHttpClient.gerarQrCode(this.pedido.getId()))
                .thenReturn(ResponseEntity.of(Optional.of(new byte[1])));

        mockMvc.perform(post(PATH + "/{idPedido}/gerar-pagamento", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG));
    }

    @Test
    void deveriaFalharAoNaoConseguirGerarImagemQrCode() throws Exception {
        when(msPagamentoHttpClient.gerarQrCode(this.pedido.getId()))
                .thenReturn(ResponseEntity.of(Optional.empty()));

        mockMvc.perform(post(PATH + "/{idPedido}/gerar-pagamento", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensagem").value("Não foi possível obter o QR-Code"));
    }

    @Test
    void deveriaFalharAoGerarPagamentoComPedidoInvalido() throws Exception {
        var idPedido = 999999;
        when(msPagamentoHttpClient.gerarQrCode(this.pedido.getId()))
                .thenReturn(ResponseEntity.of(Optional.of(new byte[1])));

        mockMvc.perform(post(PATH + "/{idPedido}/gerar-pagamento", idPedido)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void deveriaConfirmarPagamentoComSucesso() throws Exception {
        mockMvc.perform(put(PATH + "/{idPedido}/confirmar-pagamento", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH + "/{idPedido}/status", this.pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pagamentoAprovado").value(Boolean.TRUE));
    }

    @Test
    void deveriaFalharAoConfirmarPagamentoComPedidoInvalido() throws Exception {
        var idPedido = 999999;
        mockMvc.perform(put(PATH + "/{idPedido}/confirmar-pagamento", idPedido)
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
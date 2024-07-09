package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.domain.Papel;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.domain.queue.PedidoQueue;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilaResourceIT {
    private final String PATH = "/api/v1/pedidos/fila";

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
    private MockMvc mockMvc;

    @MockBean
    private PedidoQueue pedidoQueue;

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
    void deveriaExibirPedidosParaORestauranteComOStatusRecebido() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.pagar(this.pedido);

        mockMvc.perform(get(PATH + "/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos").isArray())
                .andExpect(jsonPath("$.pedidos[0].id").value(this.pedido.getId()))
                .andExpect(jsonPath("$.pedidos[0].status").value(StatusPedido.RECEBIDO.name()));
    }

    @Test
    void deveriaExibirPedidosParaORestauranteComOStatusEmPreparacao() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.pagar(this.pedido);
        this.pedido.preparar();
        this.pedidoGateway.alterarStatus(this.pedido);

        mockMvc.perform(get(PATH + "/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos").isArray())
                .andExpect(jsonPath("$.pedidos[0].id").value(this.pedido.getId()))
                .andExpect(jsonPath("$.pedidos[0].status").value(StatusPedido.EM_PREPARACAO.name()));
    }

    @Test
    void deveriaExibirPedidosParaORestauranteComOStatusPronto() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.pagar(this.pedido);
        this.pedido.pronto();
        this.pedidoGateway.alterarStatus(this.pedido);

        mockMvc.perform(get(PATH + "/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos").isArray())
                .andExpect(jsonPath("$.pedidos[0].id").value(this.pedido.getId()))
                .andExpect(jsonPath("$.pedidos[0].status").value(StatusPedido.PRONTO.name()));
    }

    @Test
    void naoDeveriaExibirPedidosParaORestauranteComOStatusFinalizado() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.pagar(this.pedido);
        this.pedido.finalizado();
        this.pedidoGateway.alterarStatus(this.pedido);

        mockMvc.perform(get(PATH + "/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos").isArray())
                .andExpect(jsonPath("$.pedidos").isEmpty());
    }

    @Test
    void naoDeveriaExibirPedidosParaOClienteComOStatusRecebido() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.pagar(this.pedido);

        mockMvc.perform(get(PATH + "/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos").isArray())
                .andExpect(jsonPath("$.pedidos").isEmpty());
    }

    @Test
    void deveriaExibirPedidosParaOClienteComOStatusEmPreparacao() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.pagar(this.pedido);
        this.pedido.preparar();
        this.pedidoGateway.alterarStatus(this.pedido);

        mockMvc.perform(get(PATH + "/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos").isArray())
                .andExpect(jsonPath("$.pedidos[0].id").value(this.pedido.getId()))
                .andExpect(jsonPath("$.pedidos[0].status").value(StatusPedido.EM_PREPARACAO.name()));
    }

    @Test
    void deveriaExibirPedidosParaOClienteComOStatusPronto() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.pagar(this.pedido);
        this.pedido.pronto();
        this.pedidoGateway.alterarStatus(this.pedido);

        mockMvc.perform(get(PATH + "/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos").isArray())
                .andExpect(jsonPath("$.pedidos[0].id").value(this.pedido.getId()))
                .andExpect(jsonPath("$.pedidos[0].status").value(StatusPedido.PRONTO.name()));
    }

    @Test
    void naoDeveriaExibirPedidosParaOClienteComOStatusFinalizado() throws Exception {
        this.pedido.pagar();
        this.pedidoGateway.pagar(this.pedido);
        this.pedido.finalizado();
        this.pedidoGateway.alterarStatus(this.pedido);

        mockMvc.perform(get(PATH + "/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidos").isArray())
                .andExpect(jsonPath("$.pedidos").isEmpty());
    }

    private Usuario obterUsuario() {
        var usuario = TestObjects.getUsuario();
        usuario.setPapeis(Set.of(new Papel("Teste")));

        return usuarioGateway.salvar(usuario);
    }
}
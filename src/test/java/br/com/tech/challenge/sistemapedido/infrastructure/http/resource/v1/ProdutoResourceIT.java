package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.application.request.ProdutoRequest;
import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.ProdutoRepositoryJpa;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProdutoResourceIT {
    private final String PATH = "/api/v1/produtos";
    private Produto produto;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProdutoGateway produtoGateway;
    @Autowired
    private ObjectMapper jsonMapper;
    @Autowired
    private ProdutoRepositoryJpa produtoRepository;

    @BeforeEach
    void setUp() {
        this.produto = produtoGateway.salvar(TestObjects.getProduto("Produto Teste"));
    }

    @AfterEach
    void tearDown() {
        produtoRepository.deleteAll();
    }

    @Test
    void deveriaListarProdutosComSucesso() throws Exception {

        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produtos").isArray())
                .andExpect(jsonPath("$.produtos").isNotEmpty());
    }

    @Test
    void deveriaBuscarProdutoComSucesso() throws Exception {

        mockMvc.perform(get(PATH + "/{idPedido}", this.produto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produto.id").value(produto.getId()))
                .andExpect(jsonPath("$.produto.nome").value(produto.getNome().toString()))
                .andExpect(jsonPath("$.produto.categoria").value(produto.getCategoria().name()))
                .andExpect(jsonPath("$.produto.descricao").value(produto.getDescricao().toString()))
                .andExpect(jsonPath("$.produto.preco").value(produto.getPreco().getPreco().longValue()));
    }

    @Test
    void deveriaFalharQuandoNaoEncontrarProdutoAoBuscar() throws Exception {

        mockMvc.perform(get(PATH + "/{idPedido}", 1234)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void deveriaCriarProdutoComSucesso() throws Exception {
        var request = new ProdutoRequest("Novo Produto", "LANCHE", "Descrição do Produto", BigDecimal.TEN);
        var jsonRequest = jsonMapper.writeValueAsString(request);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProduto").isNumber());
    }

    @Test
    void deveriaAlterarProdutoComSucesso() throws Exception {
        var request = new ProdutoRequest("Produto alterado", "SOBREMESA", "Descrição do Produto Alterado", BigDecimal.ONE);
        var jsonRequest = jsonMapper.writeValueAsString(request);

        mockMvc.perform(put(PATH + "/{idPedido}", this.produto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produto.id").value(produto.getId()))
                .andExpect(jsonPath("$.produto.nome").value(request.nome()))
                .andExpect(jsonPath("$.produto.categoria").value(request.categoria()))
                .andExpect(jsonPath("$.produto.descricao").value(request.descricao()))
                .andExpect(jsonPath("$.produto.preco").value(request.preco()));
    }

    @Test
    void deveriaFalharQuandoNaoEncontrarProdutoAoAlterar() throws Exception {
        var request = new ProdutoRequest("Produto alterado", "SOBREMESA", "Descrição do Produto Alterado", BigDecimal.ONE);
        var jsonRequest = jsonMapper.writeValueAsString(request);

        mockMvc.perform(put(PATH + "/{idPedido}", 1234)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void deveriaExcluirProdutoComSucesso() throws Exception {
        mockMvc.perform(delete(PATH + "/{idPedido}", this.produto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void deveriaFalharQuandoNaoEncontrarProdutoAoExcluir() throws Exception {
        mockMvc.perform(delete(PATH + "/{idPedido}", 1234)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }
}
package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.application.request.AutenticarUsuarioRequest;
import br.com.tech.challenge.sistemapedido.application.request.RegistrarUsuarioRequest;
import br.com.tech.challenge.sistemapedido.domain.Papel;
import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.infrastructure.mapper.PapelModelMapper;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.PapelModel;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.PapelRepositoryJpa;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.PedidoRepositoryJpa;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.UsuarioRepositoryJpa;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AutenticacaoResourceIT {
    private final String PATH = "/api/v1/autenticacao";

    private final String SENHA = "12345678";

    private Usuario usuario;

    @Autowired
    private UsuarioGateway usuarioGateway;

    @Autowired
    private UsuarioRepositoryJpa usuarioRepositoryJpa;

    @Autowired
    private PapelRepositoryJpa papelRepositoryJpa;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        usuarioRepositoryJpa.deleteAll();
    }

    @Test
    void deveriaAutenticarUmUsuarioComSucesso() throws Exception {
        var usuario = obterUsuario();
        var request = new AutenticarUsuarioRequest(usuario.getCpf(), SENHA);
        var jsonRequest = jsonMapper.writeValueAsString(request);

        mockMvc.perform(post(PATH + "/autenticar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isString())
                .andExpect(jsonPath("$.tokenType").isString());
    }

    @Test
    void deveriaFalharAoAutenticarComUmUsuarioInvalido() throws Exception {
        var request = new AutenticarUsuarioRequest("19100000000", "senha invalida");
        var jsonRequest = jsonMapper.writeValueAsString(request);

        mockMvc.perform(post(PATH + "/autenticar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveriaRegistrarUmUsuarioComSucesso() throws Exception {
        var request = new RegistrarUsuarioRequest("Teste",
                "23804173004",
                "teste@gmail.com",
                "12345678",
                Set.of(new Papel("Teste")));
        var jsonRequest = jsonMapper.writeValueAsString(request);

        mockMvc.perform(post(PATH + "/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isCreated());
    }

    private Usuario obterUsuario() {


        var usuario = TestObjects.getUsuario();
        usuario.setPapeis(Set.of(new Papel("Teste")));

        usuario.setSenha(passwordEncoder.encode(SENHA));

        return usuarioGateway.salvar(usuario);
    }
}
package br.com.tech.challenge.sistemapedido.application.controller;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutenticacaoControllerTest {
    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private AutenticacaoController underTest;

    @Test
    void deveriaAutenticarUsuarioComSucesso() {
        var token = "token";

        when(usuarioGateway.autenticar(anyString(), anyString()))
                .thenReturn(token);

        var response = underTest.autenticarUsuario("12345", "senha");

        assertThat(response).isEqualTo(token);

        verify(usuarioGateway).autenticar(anyString(), anyString());
    }

    @Test
    void deveriaRegistrarUsuarioComSucesso() {
        var usuario = TestObjects.getUsuario();

        when(usuarioGateway.registrar(any(Usuario.class)))
                .thenReturn(usuario);

        var response = underTest.registrarUsuario(usuario);

        assertThat(response).isEqualTo(usuario);

        verify(usuarioGateway).registrar(any(Usuario.class));
    }
}
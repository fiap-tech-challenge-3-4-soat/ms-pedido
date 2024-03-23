package br.com.tech.challenge.sistemapedido.usecase.usuario;

import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutenticarUsuarioUseCaseTest {
    @Mock
    private UsuarioGateway gateway;
    @InjectMocks
    private AutenticarUsuarioUseCase underTest;

    @Test
    void deveriaAutenticarUsuarioComSucesso() {
        var token = "token";

        when(gateway.autenticar(anyString(), anyString()))
                .thenReturn(token);

        var response = underTest.executar("cpf", "senha");

        assertThat(response).isEqualTo(token);
        verify(gateway).autenticar(anyString(), anyString());
    }
}
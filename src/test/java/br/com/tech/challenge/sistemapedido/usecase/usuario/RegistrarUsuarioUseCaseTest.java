package br.com.tech.challenge.sistemapedido.usecase.usuario;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrarUsuarioUseCaseTest {
    @Mock
    private UsuarioGateway gateway;
    @InjectMocks
    private RegistrarUsuarioUseCase underTest;
    @Test
    void deveriaRegistrarUsuarioComSucesso() {
        var usuario = TestObjects.getUsuario();

        when(gateway.registrar(any(Usuario.class)))
                .thenReturn(usuario);

        underTest.executar(usuario);

        verify(gateway).registrar(any(Usuario.class));
    }

}
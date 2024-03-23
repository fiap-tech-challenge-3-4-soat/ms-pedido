package br.com.tech.challenge.sistemapedido.usecase.usuario;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.domain.exception.UsuarioNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObterUsuarioUseCaseTest {
    @Mock
    private UsuarioGateway gateway;
    @InjectMocks
    private ObterUsuarioUseCase underTest;

    @Test
    void deveriaObterUsuarioComSucesso() {
        var usuario = TestObjects.getUsuario();
        var cpf = "19100000000";

        when(gateway.buscarPorCpf(cpf))
                .thenReturn(Optional.of(usuario));

        underTest.executar(cpf);

        verify(gateway).buscarPorCpf(anyString());
    }

    @Test
    void deveriaLancarExcecaoQuandoNaoEncontrarOUsuario() {
        var cpf = "19100000000";

        when(gateway.buscarPorCpf(cpf))
                .thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () ->
                underTest.executar(cpf));

        verify(gateway).buscarPorCpf(anyString());
    }
}
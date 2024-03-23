package br.com.tech.challenge.sistemapedido.usecase.usuario;

import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.domain.exception.UsuarioNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import jakarta.inject.Named;

@Named
public class ObterUsuarioUseCase {
    private final UsuarioGateway usuarioGateway;

    public ObterUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public Usuario executar(String cpf) {
        return usuarioGateway.buscarPorCpf(cpf)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(cpf));
    }
}

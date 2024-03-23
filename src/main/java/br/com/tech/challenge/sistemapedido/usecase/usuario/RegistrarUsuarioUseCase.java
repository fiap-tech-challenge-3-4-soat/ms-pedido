package br.com.tech.challenge.sistemapedido.usecase.usuario;

import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import jakarta.inject.Named;

@Named
public class RegistrarUsuarioUseCase {
    private final UsuarioGateway usuarioGateway;

    public RegistrarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public Usuario executar(Usuario usuario) {
        return this.usuarioGateway.registrar(usuario);
    }
}

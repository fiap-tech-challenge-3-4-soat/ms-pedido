package br.com.tech.challenge.sistemapedido.usecase.usuario;

import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import jakarta.inject.Named;

@Named
public class AutenticarUsuarioUseCase {
    private final UsuarioGateway usuarioGateway;

    public AutenticarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public String executar(String cpf, String senha) {
        return this.usuarioGateway.autenticar(cpf, senha);
    }
}

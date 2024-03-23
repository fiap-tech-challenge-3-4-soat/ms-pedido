package br.com.tech.challenge.sistemapedido.application.controller;

import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import br.com.tech.challenge.sistemapedido.usecase.usuario.AutenticarUsuarioUseCase;
import br.com.tech.challenge.sistemapedido.usecase.usuario.RegistrarUsuarioUseCase;
import jakarta.inject.Named;

@Named
public class AutenticacaoController {
    private final UsuarioGateway usuarioGateway;

    public AutenticacaoController(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public String autenticarUsuario(String cpf, String senha) {
        var autenticarUsuarioUseCase = new AutenticarUsuarioUseCase(this.usuarioGateway);

        return autenticarUsuarioUseCase.executar(cpf, senha);
    }

    public Usuario registrarUsuario(Usuario usuario) {
        var registrarUsuarioUseCase = new RegistrarUsuarioUseCase(this.usuarioGateway);

        return registrarUsuarioUseCase.executar(usuario);
    }
}

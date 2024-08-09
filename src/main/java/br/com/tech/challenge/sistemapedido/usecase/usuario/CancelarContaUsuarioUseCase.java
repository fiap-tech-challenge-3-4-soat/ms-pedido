package br.com.tech.challenge.sistemapedido.usecase.usuario;

import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import jakarta.inject.Named;

@Named
public class CancelarContaUsuarioUseCase {
    private final UsuarioGateway usuarioGateway;

    public CancelarContaUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public void executar(String cpf, String nome, String endereco, String numeroDeTelefone, String informacaoDePagamento) {
        this.usuarioGateway.cancelar(cpf, nome, endereco, numeroDeTelefone, informacaoDePagamento);
    }
}

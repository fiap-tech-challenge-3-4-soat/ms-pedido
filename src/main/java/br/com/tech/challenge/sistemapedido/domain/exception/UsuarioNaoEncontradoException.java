package br.com.tech.challenge.sistemapedido.domain.exception;

public class UsuarioNaoEncontradoException extends EntityNotFoundException {
    private static final String MENSAGEM = "Usuario não encontrado cpf: %s";

    public UsuarioNaoEncontradoException(String cpf) {
        super(String.format(MENSAGEM, cpf));
    }
}

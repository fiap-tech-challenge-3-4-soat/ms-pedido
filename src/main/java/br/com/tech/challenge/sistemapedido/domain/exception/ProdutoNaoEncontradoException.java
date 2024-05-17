package br.com.tech.challenge.sistemapedido.domain.exception;

public class ProdutoNaoEncontradoException extends EntityNotFoundException {
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

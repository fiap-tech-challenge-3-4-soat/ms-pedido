package br.com.tech.challenge.sistemapedido.domain.exception;

public class PedidoNaoEncontradoException extends EntityNotFoundException {
    private static final String MENSAGEM = "Pedido não encontrado id: %s";
    public PedidoNaoEncontradoException(Long id) {
        super(String.format(MENSAGEM, id));
    }
}

package br.com.tech.challenge.sistemapedido.domain.exception;

public class PedidoCanceladoException extends RuntimeException {
    private static final String MENSAGEM = "Pedido está cancelado id: %s";
    public PedidoCanceladoException(Long id) {
        super(String.format(MENSAGEM, id));
    }
}

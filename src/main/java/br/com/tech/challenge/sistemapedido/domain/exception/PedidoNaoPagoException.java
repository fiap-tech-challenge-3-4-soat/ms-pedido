package br.com.tech.challenge.sistemapedido.domain.exception;

public class PedidoNaoPagoException extends RuntimeException {
    private static final String MENSAGEM = "Pedido não está pago id: %s";
    public PedidoNaoPagoException(Long id) {
        super(String.format(MENSAGEM, id));
    }
}

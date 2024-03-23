package br.com.tech.challenge.sistemapedido.domain.exception;

public class PedidoStatusIncorretoException extends RuntimeException {
    public PedidoStatusIncorretoException(String message) {
        super(message);
    }
}

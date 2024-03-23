package br.com.tech.challenge.sistemapedido.domain.exception;

import org.springframework.http.HttpStatus;

public class SistemaPedidosAPIException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public SistemaPedidosAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public SistemaPedidosAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

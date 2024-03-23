package br.com.tech.challenge.sistemapedido.domain.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message) {
        super(message);
    }
}

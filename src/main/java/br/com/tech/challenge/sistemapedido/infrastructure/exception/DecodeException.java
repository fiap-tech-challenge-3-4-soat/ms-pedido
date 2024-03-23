package br.com.tech.challenge.sistemapedido.infrastructure.exception;

import lombok.Getter;

@Getter
public class DecodeException extends RuntimeException {
    public DecodeException(String message, Throwable cause) {
        super(message, cause);
    }
}

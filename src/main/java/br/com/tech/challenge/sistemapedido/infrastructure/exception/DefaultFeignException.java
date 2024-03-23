package br.com.tech.challenge.sistemapedido.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class DefaultFeignException extends RuntimeException {
    private final int status;
    private final String error;
    private final String message;
}

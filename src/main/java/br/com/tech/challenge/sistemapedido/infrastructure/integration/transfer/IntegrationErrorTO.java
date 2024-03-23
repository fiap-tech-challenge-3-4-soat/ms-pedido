package br.com.tech.challenge.sistemapedido.infrastructure.integration.transfer;

public record IntegrationErrorTO(int status,
                                 String error,
                                 String message) {}

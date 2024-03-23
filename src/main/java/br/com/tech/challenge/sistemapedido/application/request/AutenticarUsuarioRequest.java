package br.com.tech.challenge.sistemapedido.application.request;

public record AutenticarUsuarioRequest(String cpf,
                                       String senha) {
}

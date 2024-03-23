package br.com.tech.challenge.sistemapedido.application.response;

import lombok.Getter;

@Getter
public class AutenticarUsuarioResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public AutenticarUsuarioResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}

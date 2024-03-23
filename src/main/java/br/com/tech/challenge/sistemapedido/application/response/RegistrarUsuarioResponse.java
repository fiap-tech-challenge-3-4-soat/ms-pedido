package br.com.tech.challenge.sistemapedido.application.response;

import lombok.Getter;

@Getter
public class RegistrarUsuarioResponse {
    private Long idUsuario;

    public RegistrarUsuarioResponse(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}

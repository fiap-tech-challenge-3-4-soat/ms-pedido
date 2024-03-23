package br.com.tech.challenge.sistemapedido.application.response;

import lombok.Getter;

@Getter
public class GerarQrCodeResponse {
    private final byte[] qrCode;

    public GerarQrCodeResponse(byte[] qrCode) {
        this.qrCode = qrCode;
    }
}

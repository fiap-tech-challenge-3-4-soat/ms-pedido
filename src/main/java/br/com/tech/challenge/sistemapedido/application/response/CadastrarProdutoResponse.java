package br.com.tech.challenge.sistemapedido.application.response;

import lombok.Getter;

@Getter
public class CadastrarProdutoResponse {
    private Long idProduto;

    public CadastrarProdutoResponse(Long idProduto) {
        this.idProduto = idProduto;
    }
}

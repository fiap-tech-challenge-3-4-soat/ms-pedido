package br.com.tech.challenge.sistemapedido.application.response;

import br.com.tech.challenge.sistemapedido.application.dto.ProdutoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ProdutoResponse {
    private ProdutoDTO produto;

    public ProdutoResponse(ProdutoDTO produto) {
        this.produto = produto;
    }
}

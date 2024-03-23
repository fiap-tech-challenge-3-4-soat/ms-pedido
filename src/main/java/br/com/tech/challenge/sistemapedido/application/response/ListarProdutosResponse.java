package br.com.tech.challenge.sistemapedido.application.response;

import br.com.tech.challenge.sistemapedido.application.dto.ProdutoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListarProdutosResponse {
    private List<ProdutoDTO> produtos;

    public ListarProdutosResponse(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }
}

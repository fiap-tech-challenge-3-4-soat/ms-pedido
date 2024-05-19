package br.com.tech.challenge.sistemapedido.application.mapper;

import br.com.tech.challenge.sistemapedido.application.dto.ProdutoDTO;
import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.domain.vo.Descricao;
import br.com.tech.challenge.sistemapedido.domain.vo.Nome;
import br.com.tech.challenge.sistemapedido.domain.vo.Preco;
import jakarta.inject.Named;

@Named
public class ProdutoDataMapper {
    public ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(produto.getId(),
                produto.getNome().toString(),
                produto.getCategoria(),
                produto.getDescricao().toString(),
                produto.getPreco().getPreco());
    }

    public Produto toDomain(ProdutoDTO produtoDTO) {
        return Produto.builder()
                .id(produtoDTO.id())
                .nome(new Nome(produtoDTO.nome()))
                .categoria(produtoDTO.categoria())
                .descricao(new Descricao(produtoDTO.descricao()))
                .preco(new Preco(produtoDTO.preco()))
                .build();
    }
}

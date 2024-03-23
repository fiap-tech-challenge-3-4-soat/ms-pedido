package br.com.tech.challenge.sistemapedido.infrastructure.mapper;

import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.domain.vo.Descricao;
import br.com.tech.challenge.sistemapedido.domain.vo.Nome;
import br.com.tech.challenge.sistemapedido.domain.vo.Preco;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.ProdutoModel;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelMapper {
    public Produto toDomain(ProdutoModel produto) {
        return Produto.builder()
                .id(produto.getId())
                .nome(new Nome(produto.getNome()))
                .categoria(produto.getCategoria())
                .descricao(new Descricao(produto.getDescricao()))
                .preco(new Preco(produto.getPreco()))
                .build();
    }

    public ProdutoModel toModel(Produto produto) {
        return ProdutoModel.builder()
                .id(produto.getId())
                .nome(produto.getNome().toString())
                .categoria(produto.getCategoria())
                .descricao(produto.getDescricao().toString())
                .preco(produto.getPreco().getPreco())
                .build();
    }
}

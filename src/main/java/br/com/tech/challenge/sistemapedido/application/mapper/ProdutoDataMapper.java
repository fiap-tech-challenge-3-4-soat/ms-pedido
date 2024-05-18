package br.com.tech.challenge.sistemapedido.application.mapper;

import br.com.tech.challenge.sistemapedido.application.dto.ProdutoDTO;
import br.com.tech.challenge.sistemapedido.application.request.ProdutoRequest;
import br.com.tech.challenge.sistemapedido.domain.Categoria;
import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.domain.vo.Descricao;
import br.com.tech.challenge.sistemapedido.domain.vo.Nome;
import br.com.tech.challenge.sistemapedido.domain.vo.Preco;
import jakarta.inject.Named;

import java.util.List;

@Named
public class ProdutoDataMapper {
    public ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(produto.getId(),
                produto.getNome().toString(),
                produto.getCategoria(),
                produto.getDescricao().toString(),
                produto.getPreco().getPreco());
    }

    public Produto toDomain(ProdutoRequest request) {
        return Produto.builder()
                .nome(new Nome(request.nome()))
                .categoria(Categoria.valueOf(request.categoria().toUpperCase()))
                .descricao(new Descricao(request.descricao()))
                .preco(new Preco(request.preco()))
                .build();
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

    public List<ProdutoDTO> toList(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toDTO)
                .toList();
    }
}

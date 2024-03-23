package br.com.tech.challenge.sistemapedido.application.mapper;

import br.com.tech.challenge.sistemapedido.application.dto.ItemPedidoDTO;
import br.com.tech.challenge.sistemapedido.application.dto.ItemProdutoDTO;
import br.com.tech.challenge.sistemapedido.domain.ItemPedido;
import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.domain.vo.Observacao;
import br.com.tech.challenge.sistemapedido.domain.vo.Quantidade;
import jakarta.inject.Named;

import java.util.List;

@Named
public class ItemPedidoDataMapper {
    public ItemPedido toDomain(ItemProdutoDTO itemPedido) {
        var produto = Produto.builder()
                .id(itemPedido.idProduto())
                .build();

        return ItemPedido.builder()
                .quantidade(new Quantidade(itemPedido.quantidade()))
                .observacao(new Observacao(itemPedido.observacao()))
                .produto(produto)
                .build();
    }

    public List<ItemPedido> toDomainList(List<ItemProdutoDTO> itens) {
        return itens.stream()
                .map(this::toDomain)
                .toList();
    }

    public ItemPedidoDTO toDTO(ItemPedido item) {
        return new ItemPedidoDTO(item.getProduto().getNome().toString(),
                item.getProduto().getDescricao().toString(),
                item.getProduto().getCategoria(),
                item.getProduto().getPreco().getPreco(),
                item.getQuantidade().getQuantidade(),
                item.getObservacao().toString());
    }
}

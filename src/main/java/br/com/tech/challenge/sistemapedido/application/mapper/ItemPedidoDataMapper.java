package br.com.tech.challenge.sistemapedido.application.mapper;

import br.com.tech.challenge.sistemapedido.application.dto.ItemPedidoDTO;
import br.com.tech.challenge.sistemapedido.application.dto.ItemProdutoDTO;
import br.com.tech.challenge.sistemapedido.domain.ItemPedido;
import br.com.tech.challenge.sistemapedido.domain.vo.Observacao;
import br.com.tech.challenge.sistemapedido.domain.vo.Quantidade;
import jakarta.inject.Named;

import java.util.List;

@Named
public class ItemPedidoDataMapper {
    public ItemPedido toDomain(ItemProdutoDTO itemPedido) {
        return ItemPedido.builder()
                .idProduto(itemPedido.idProduto())
                .quantidade(new Quantidade(itemPedido.quantidade()))
                .observacao(new Observacao(itemPedido.observacao()))
                .build();
    }

    public List<ItemPedido> toDomainList(List<ItemProdutoDTO> itens) {
        return itens.stream()
                .map(this::toDomain)
                .toList();
    }

    public ItemPedidoDTO toDTO(ItemPedido item) {
        return new ItemPedidoDTO(item.getNome().toString(),
                item.getDescricao().toString(),
                item.getCategoria(),
                item.getPreco().getPreco(),
                item.getQuantidade().getQuantidade(),
                item.getObservacao().toString());
    }
}

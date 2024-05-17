package br.com.tech.challenge.sistemapedido.infrastructure.mapper;

import br.com.tech.challenge.sistemapedido.domain.ItemPedido;
import br.com.tech.challenge.sistemapedido.domain.vo.*;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.ItemPedidoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemPedidoModelMapper {

    public ItemPedido toDomain(ItemPedidoModel itemPedido) {
        return ItemPedido.builder()
                .id(itemPedido.getId())
                .idProduto(itemPedido.getIdProduto())
                .nome(new Nome(itemPedido.getNome()))
                .categoria(itemPedido.getCategoria())
                .descricao(new Descricao(itemPedido.getDescricao()))
                .quantidade(new Quantidade(itemPedido.getQuantidade()))
                .observacao(new Observacao(itemPedido.getObservacao()))
                .preco(new Preco(itemPedido.getPreco()))
                .build();
    }

    public ItemPedidoModel toModel(ItemPedido itemPedido) {
        return ItemPedidoModel.builder()
                .id(itemPedido.getId())
                .idProduto(itemPedido.getIdProduto())
                .nome(itemPedido.getNome().toString())
                .categoria(itemPedido.getCategoria())
                .descricao(itemPedido.getDescricao().toString())
                .quantidade(itemPedido.getQuantidade().getQuantidade())
                .observacao(itemPedido.getObservacao().toString())
                .preco(itemPedido.getPreco().getPreco())
                .build();
    }
}

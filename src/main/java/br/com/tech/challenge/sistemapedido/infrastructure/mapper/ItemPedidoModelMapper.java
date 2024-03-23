package br.com.tech.challenge.sistemapedido.infrastructure.mapper;

import br.com.tech.challenge.sistemapedido.domain.ItemPedido;
import br.com.tech.challenge.sistemapedido.domain.vo.Observacao;
import br.com.tech.challenge.sistemapedido.domain.vo.Quantidade;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.ItemPedidoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemPedidoModelMapper {
    private final ProdutoModelMapper produtoMapper;

    public ItemPedido toDomain(ItemPedidoModel itemPedido) {
        return ItemPedido.builder()
                .id(itemPedido.getId())
                .quantidade(new Quantidade(itemPedido.getQuantidade()))
                .observacao(new Observacao(itemPedido.getObservacao()))
                .produto(produtoMapper.toDomain(itemPedido.getProduto()))
                .build();
    }

    public ItemPedidoModel toModel(ItemPedido itemPedido) {
        return ItemPedidoModel.builder()
                .id(itemPedido.getId())
                .quantidade(itemPedido.getQuantidade().getQuantidade())
                .observacao(itemPedido.getObservacao().toString())
                .produto(produtoMapper.toModel(itemPedido.getProduto()))
                .build();
    }
}

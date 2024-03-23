package br.com.tech.challenge.sistemapedido.application.mapper;

import br.com.tech.challenge.sistemapedido.application.dto.PedidoDTO;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Named
@RequiredArgsConstructor
public class PedidoDataMapper {
    private final ItemPedidoDataMapper itemPedidoMapper;
    public PedidoDTO toDTO(Pedido pedido) {
        var itens = pedido.getItens()
                .stream()
                .map(itemPedidoMapper::toDTO)
                .toList();

        var nomeCliente = Objects.nonNull(pedido.getUsuario()) ? pedido.getUsuario().getNome() : "";

        return new PedidoDTO(pedido.getId(),
                pedido.getStatus(),
                pedido.getDataCriacao().getData(),
                pedido.getDataAtualizacao().getData(),
                pedido.getTotal().getPreco(),
                pedido.estaPago(),
                nomeCliente,
                itens);
    }

    public List<PedidoDTO> toList(List<Pedido> pedidos) {
        return pedidos
                .stream()
                .map(this::toDTO)
                .toList();
    }
}

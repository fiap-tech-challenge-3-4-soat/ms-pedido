package br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository;

import br.com.tech.challenge.sistemapedido.application.repository.PedidoRepository;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.infrastructure.mapper.ItemPedidoModelMapper;
import br.com.tech.challenge.sistemapedido.infrastructure.mapper.PedidoModelMapper;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.ItemPedidoRepositoryJpa;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.PedidoRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PedidoRepositoryImpl implements PedidoRepository {
    private final PedidoRepositoryJpa pedidoRepository;
    private final ItemPedidoRepositoryJpa itemPedidoRepository;

    private final PedidoModelMapper pedidoMapper;
    private final ItemPedidoModelMapper itemPedidoMapper;



    @Override
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id)
                .map(pedidoMapper::toDomain);
    }

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toDomain)
                .toList();
    }

    @Override
    public Pedido save(Pedido pedido) {
        var itensModel = pedido.getItens()
                .stream()
                .map(itemPedidoMapper::toModel)
                .map(item -> {
                    item.setPedido(pedidoMapper.toModel(pedido));
                    return item;
                })
                .toList();

        itensModel = itemPedidoRepository.saveAll(itensModel);
        var pedidoModel = pedidoMapper.toModel(pedido);
        pedidoModel.setItens(itensModel);
        pedidoModel = pedidoRepository.save(pedidoModel);
        return pedidoMapper.toDomain(pedidoModel);
    }
}

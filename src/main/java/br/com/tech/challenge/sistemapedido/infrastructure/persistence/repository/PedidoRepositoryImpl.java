package br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository;

import br.com.tech.challenge.sistemapedido.application.repository.PedidoRepository;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.infrastructure.mapper.ItemPedidoModelMapper;
import br.com.tech.challenge.sistemapedido.infrastructure.mapper.PedidoModelMapper;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.PedidoRepositoryJpa;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PedidoRepositoryImpl implements PedidoRepository {
    private final PedidoRepositoryJpa pedidoRepository;

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
    @Transactional(Transactional.TxType.REQUIRED)
    public Pedido save(Pedido pedido) {
        var pedidoModel = pedidoMapper.toModel(pedido);

        var itensModel = pedido.getItens()
                .stream()
                .map(itemPedidoMapper::toModel)
                .toList();

        for (var itemModel : itensModel) {
            itemModel.setPedido(pedidoModel);
        }

        pedidoModel.setItens(itensModel);

        pedidoModel = pedidoRepository.save(pedidoModel);

        return pedidoMapper.toDomain(pedidoModel);
    }
}

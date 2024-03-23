package br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository;

import br.com.tech.challenge.sistemapedido.application.repository.FilaRepository;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import br.com.tech.challenge.sistemapedido.infrastructure.mapper.PedidoModelMapper;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.FilaClienteModel;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.FilaRestauranteModel;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.FilaClienteRepositoryJpa;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.FilaRestauranteRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilaRepositoryImpl implements FilaRepository {
    private final FilaClienteRepositoryJpa filaClienteRepository;
    private final FilaRestauranteRepositoryJpa filaRestauranteRepository;
    private final PedidoModelMapper pedidoMapper;

    @Override
    public List<Pedido> buscarFilaRestaurante() {
        var statusArray = new StatusPedido[]{StatusPedido.RECEBIDO, StatusPedido.EM_PREPARACAO, StatusPedido.PRONTO};
        var fila = filaRestauranteRepository.findAllByPedidoStatusIn(new LinkedList<>(Arrays.asList(statusArray)));

        return fila.stream()
                .map(FilaRestauranteModel::getPedido)
                .map(pedidoMapper::toDomain)
                .toList();
    }

    @Override
    public List<Pedido> buscarFilaCliente() {
        var statusArray = new StatusPedido[]{StatusPedido.EM_PREPARACAO, StatusPedido.PRONTO};
        var fila = filaClienteRepository.findAllByPedidoStatusIn(new LinkedList<>(Arrays.asList(statusArray)));

        return fila.stream()
                .map(FilaClienteModel::getPedido)
                .map(pedidoMapper::toDomain)
                .toList();
    }
}

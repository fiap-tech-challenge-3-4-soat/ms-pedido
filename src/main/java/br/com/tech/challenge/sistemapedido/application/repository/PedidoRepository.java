package br.com.tech.challenge.sistemapedido.application.repository;

import br.com.tech.challenge.sistemapedido.domain.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository {
    Optional<Pedido> findById(Long id);
    List<Pedido> findAll();
    Pedido save(Pedido produto);
}

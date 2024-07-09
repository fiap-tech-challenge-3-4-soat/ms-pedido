package br.com.tech.challenge.sistemapedido.usecase.gateway;

import br.com.tech.challenge.sistemapedido.domain.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoGateway {
    Optional<Pedido> buscarPorId(Long id);
    List<Pedido> buscarTodos();
    Pedido salvar(Pedido pedido);
    void pagar(Pedido pedido);
    void alterarStatus(Pedido pedido);
    List<Pedido> buscarFilaRestaurante();
    List<Pedido> buscarFilaCliente();
}

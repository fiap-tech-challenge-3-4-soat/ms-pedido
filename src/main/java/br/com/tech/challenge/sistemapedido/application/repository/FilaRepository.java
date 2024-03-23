package br.com.tech.challenge.sistemapedido.application.repository;

import br.com.tech.challenge.sistemapedido.domain.Pedido;

import java.util.List;

public interface FilaRepository {
    List<Pedido> buscarFilaRestaurante();
    List<Pedido> buscarFilaCliente();
}

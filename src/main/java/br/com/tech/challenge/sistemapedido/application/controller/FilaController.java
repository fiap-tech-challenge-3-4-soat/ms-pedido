package br.com.tech.challenge.sistemapedido.application.controller;

import br.com.tech.challenge.sistemapedido.application.mapper.PedidoDataMapper;
import br.com.tech.challenge.sistemapedido.application.response.ListarPedidosResponse;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import jakarta.inject.Named;

@Named
public class FilaController {
    private final PedidoGateway pedidoGateway;
    private final PedidoDataMapper pedidoMapper;

    public FilaController(PedidoGateway pedidoGateway, PedidoDataMapper pedidoMapper) {
        this.pedidoGateway = pedidoGateway;
        this.pedidoMapper = pedidoMapper;
    }

    public ListarPedidosResponse filaRestaurante() {
        var pedidos = pedidoGateway.buscarFilaRestaurante();

        return new ListarPedidosResponse(pedidoMapper.toList(pedidos));
    }

    public ListarPedidosResponse filaCLiente() {
        var pedidos = pedidoGateway.buscarFilaCliente();

        return new ListarPedidosResponse(pedidoMapper.toList(pedidos));
    }
}

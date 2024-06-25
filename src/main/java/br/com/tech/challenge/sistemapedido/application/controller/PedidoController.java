package br.com.tech.challenge.sistemapedido.application.controller;

import br.com.tech.challenge.sistemapedido.application.mapper.ItemPedidoDataMapper;
import br.com.tech.challenge.sistemapedido.application.mapper.PedidoDataMapper;
import br.com.tech.challenge.sistemapedido.application.request.PedidoRequest;
import br.com.tech.challenge.sistemapedido.application.response.CadastrarPedidoResponse;
import br.com.tech.challenge.sistemapedido.application.response.ListarPedidosResponse;
import br.com.tech.challenge.sistemapedido.application.response.PedidoResponse;
import br.com.tech.challenge.sistemapedido.application.response.StatusPedidoResponse;
import br.com.tech.challenge.sistemapedido.domain.queue.PedidoQueue;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import br.com.tech.challenge.sistemapedido.usecase.pedido.*;
import br.com.tech.challenge.sistemapedido.usecase.usuario.ObterUsuarioUseCase;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
public class PedidoController {
    private final PedidoGateway pedidoGateway;
    private final ProdutoGateway produtoGateway;
    private final UsuarioGateway usuarioGateway;
    private final PedidoQueue pedidoQueue;
    private final PedidoDataMapper pedidoMapper;
    private final ItemPedidoDataMapper itemPedidoMapper;

    public PedidoController(PedidoGateway pedidoGateway,
                            ProdutoGateway produtoGateway,
                            UsuarioGateway usuarioGateway,
                            PedidoQueue pedidoQueue,
                            PedidoDataMapper pedidoMapper,
                            ItemPedidoDataMapper itemPedidoMapper) {
        this.pedidoGateway = pedidoGateway;
        this.produtoGateway = produtoGateway;
        this.usuarioGateway = usuarioGateway;
        this.pedidoQueue = pedidoQueue;
        this.pedidoMapper = pedidoMapper;
        this.itemPedidoMapper = itemPedidoMapper;
    }

    @Transactional
    public CadastrarPedidoResponse criar(PedidoRequest request) {
        var obterUsuarioUseCase = new ObterUsuarioUseCase(this.usuarioGateway);
        var criarPedidoUseCase = new CriarPedidoUseCase(obterUsuarioUseCase, this.pedidoGateway, this.produtoGateway, pedidoQueue);

        var pedido = criarPedidoUseCase.executar(itemPedidoMapper.toDomainList(request.itens()), request.cpf());

        return new CadastrarPedidoResponse(pedido.getId());
    }

    public ListarPedidosResponse listar() {
        var listarPedidosUseCase = new ListarPedidosUseCase(this.pedidoGateway);
        var pedidos = listarPedidosUseCase.executar();

        return new ListarPedidosResponse(pedidoMapper.toList(pedidos));
    }

    public StatusPedidoResponse verificarStatus(Long id) {
        var buscarPedidoUseCase = new BuscarPedidoUseCase(this.pedidoGateway);
        var pedido = buscarPedidoUseCase.executar(id);

        return new StatusPedidoResponse(pedido.estaPago(), pedido.getStatus());
    }

    public PedidoResponse consultar(Long id) {
        var buscarPedidoUseCase = new BuscarPedidoUseCase(this.pedidoGateway);
        var pedido = buscarPedidoUseCase.executar(id);

        return new PedidoResponse(pedidoMapper.toDTO(pedido));
    }

    public void preparacao(Long idPedido) {
        var alterarStatusPedidoUseCase = new AlterarStatusPedidoParaEmPreparacao(this.pedidoGateway);
        alterarStatusPedidoUseCase.executar(idPedido);
    }

    public void pronto(Long idPedido) {
        var alterarStatusPedidoParaProntoUseCase = new AlterarStatusPedidoParaProntoUseCase(this.pedidoGateway);
        alterarStatusPedidoParaProntoUseCase.executar(idPedido);
    }

    public void finalizado(Long idPedido) {
        var alterarStatusPedidoParaFinalizadoUseCase = new AlterarStatusPedidoParaFinalizadoUseCase(this.pedidoGateway);
        alterarStatusPedidoParaFinalizadoUseCase.executar(idPedido);
    }
}

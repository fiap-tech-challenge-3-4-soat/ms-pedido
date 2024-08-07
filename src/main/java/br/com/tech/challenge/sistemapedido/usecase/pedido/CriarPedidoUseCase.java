package br.com.tech.challenge.sistemapedido.usecase.pedido;

import br.com.tech.challenge.sistemapedido.domain.ItemPedido;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import br.com.tech.challenge.sistemapedido.domain.queue.PedidoQueue;
import br.com.tech.challenge.sistemapedido.domain.vo.Data;
import br.com.tech.challenge.sistemapedido.domain.vo.Preco;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import br.com.tech.challenge.sistemapedido.usecase.usuario.ObterUsuarioUseCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CriarPedidoUseCase {
    private final ObterUsuarioUseCase obterUsuarioUseCase;
    private final PedidoGateway pedidoGateway;
    private final ProdutoGateway produtoGateway;
    private final PedidoQueue pedidoQueue;

    public CriarPedidoUseCase(ObterUsuarioUseCase obterUsuarioUseCase,
                              PedidoGateway pedidoGateway,
                              ProdutoGateway produtoGateway,
                              PedidoQueue pedidoQueue) {
        this.obterUsuarioUseCase = obterUsuarioUseCase;
        this.pedidoGateway = pedidoGateway;
        this.produtoGateway = produtoGateway;
        this.pedidoQueue = pedidoQueue;
    }

    public Pedido executar(List<ItemPedido> itensPedido, String cpf) {
        var novosItens = new LinkedList<ItemPedido>();
        var pedido = Pedido.builder()
                .status(StatusPedido.RECEBIDO)
                .dataCriacao(new Data(LocalDateTime.now()))
                .dataAtualizacao(new Data(LocalDateTime.now()))
                .total(new Preco(BigDecimal.ONE))
                .pago(Boolean.FALSE)
                .itens(novosItens)
                .build();

        var itens = this.validarItens(itensPedido, pedido);
        pedido.adicionarItens(itens);
        pedido.calcularTotal();

        if(validarCpf(cpf)) {
            var usuario = obterUsuarioUseCase.executar(cpf);
            pedido.adicionarUsuario(usuario);
        }

        var pedidoSalvo = pedidoGateway.salvar(pedido);

        pedidoQueue.publicarPedidoCriado(pedidoSalvo);

        return pedidoSalvo;
    }

    private List<ItemPedido> validarItens(List<ItemPedido> itens, Pedido pedido) {
        return itens.stream()
                .map(itemPedido -> {
                    var produto = produtoGateway.buscarPorId(itemPedido.getIdProduto());
                    return ItemPedido.builder()
                            .idProduto(produto.getId())
                            .nome(produto.getNome())
                            .categoria(produto.getCategoria())
                            .descricao(produto.getDescricao())
                            .quantidade(itemPedido.getQuantidade())
                            .observacao(itemPedido.getObservacao())
                            .preco(produto.getPreco())
                            .pedido(pedido)
                            .build();
                })
                .toList();
    }

    private boolean validarCpf(String cpf) {
        return Objects.nonNull(cpf)
                && cpf.length() == 11;
    }
}

package br.com.tech.challenge.sistemapedido.domain;

import br.com.tech.challenge.sistemapedido.domain.vo.Observacao;
import br.com.tech.challenge.sistemapedido.domain.vo.Quantidade;

public class ItemPedido {
    private Long id;
    private Quantidade quantidade;
    private Observacao observacao;
    private Produto produto;
    private Pedido pedido;


    public ItemPedido(ItemPedidoBuilder itemPedidoBuilder) {
        this.id = itemPedidoBuilder.id;
        this.quantidade = itemPedidoBuilder.quantidade;
        this.observacao = itemPedidoBuilder.observacao;
        this.produto = itemPedidoBuilder.produto;
        this.pedido = itemPedidoBuilder.pedido;
    }

    public static ItemPedidoBuilder builder() {
        return new ItemPedidoBuilder();
    }

    public Long getId() {
        return id;
    }

    public Quantidade getQuantidade() {
        return quantidade;
    }

    public Observacao getObservacao() {
        return observacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void adicionarPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public static class ItemPedidoBuilder {
        private Long id;
        private Quantidade quantidade;
        private Observacao observacao;
        private Produto produto;
        private Pedido pedido;

        public ItemPedidoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ItemPedidoBuilder quantidade(Quantidade quantidade) {
            this.quantidade = quantidade;
            return this;
        }

        public ItemPedidoBuilder observacao(Observacao observacao) {
            this.observacao = observacao;
            return this;
        }

        public ItemPedidoBuilder produto(Produto produto) {
            this.produto = produto;
            return this;
        }

        public ItemPedidoBuilder pedido(Pedido pedido) {
            this.pedido = pedido;
            return this;
        }

        public ItemPedido build() {
            return new ItemPedido(this);
        }
    }
}

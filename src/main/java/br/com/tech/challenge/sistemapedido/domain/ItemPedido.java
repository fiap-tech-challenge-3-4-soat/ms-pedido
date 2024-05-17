package br.com.tech.challenge.sistemapedido.domain;

import br.com.tech.challenge.sistemapedido.domain.vo.*;

public class ItemPedido {
    private Long id;
    private Long idProduto;
    private Nome nome;
    private Categoria categoria;
    private Descricao descricao;
    private Preco preco;
    private Quantidade quantidade;
    private Observacao observacao;
    private Pedido pedido;


    public ItemPedido(ItemPedidoBuilder itemPedidoBuilder) {
        this.id = itemPedidoBuilder.id;
        this.idProduto = itemPedidoBuilder.idProduto;
        this.nome = itemPedidoBuilder.nome;
        this.categoria = itemPedidoBuilder.categoria;
        this.descricao = itemPedidoBuilder.descricao;
        this.preco = itemPedidoBuilder.preco;
        this.quantidade = itemPedidoBuilder.quantidade;
        this.observacao = itemPedidoBuilder.observacao;
        this.pedido = itemPedidoBuilder.pedido;
    }

    public static ItemPedidoBuilder builder() {
        return new ItemPedidoBuilder();
    }

    public Long getId() {
        return id;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public Nome getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Descricao getDescricao() {
        return descricao;
    }

    public Preco getPreco() {
        return preco;
    }

    public Quantidade getQuantidade() {
        return quantidade;
    }

    public Observacao getObservacao() {
        return observacao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void adicionarPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public static class ItemPedidoBuilder {
        private Long id;
        private Long idProduto;
        private Nome nome;
        private Categoria categoria;
        private Descricao descricao;
        private Preco preco;
        private Quantidade quantidade;
        private Observacao observacao;
        private Pedido pedido;

        public ItemPedidoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ItemPedidoBuilder idProduto(Long idProduto) {
            this.idProduto = idProduto;
            return this;
        }

        public ItemPedidoBuilder nome(Nome nome) {
            this.nome = nome;
            return this;
        }

        public ItemPedidoBuilder categoria(Categoria categoria) {
            this.categoria = categoria;
            return this;
        }

        public ItemPedidoBuilder descricao(Descricao descricao) {
            this.descricao = descricao;
            return this;
        }

        public ItemPedidoBuilder preco(Preco preco) {
            this.preco = preco;
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

        public ItemPedidoBuilder pedido(Pedido pedido) {
            this.pedido = pedido;
            return this;
        }

        public ItemPedido build() {
            return new ItemPedido(this);
        }
    }
}

package br.com.tech.challenge.sistemapedido.domain;

import br.com.tech.challenge.sistemapedido.domain.vo.Descricao;
import br.com.tech.challenge.sistemapedido.domain.vo.Nome;
import br.com.tech.challenge.sistemapedido.domain.vo.Preco;

public class Produto {
    private Long id;
    private Nome nome;
    private Categoria categoria;
    private Descricao descricao;
    private Preco preco;

    public Produto(ProdutoBuilder produtoBuilder) {
        this.id = produtoBuilder.id;
        this.nome = produtoBuilder.nome;
        this.categoria = produtoBuilder.categoria;
        this.descricao = produtoBuilder.descricao;
        this.preco = produtoBuilder.preco;
    }

    public static ProdutoBuilder builder() {
        return new ProdutoBuilder();
    }

    public Long getId() {
        return id;
    }

    public Nome getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Preco getPreco() {
        return preco;
    }

    public Descricao getDescricao() {
        return descricao;
    }

    public static class ProdutoBuilder {
        private Long id;
        private Nome nome;
        private Categoria categoria;
        private Descricao descricao;
        private Preco preco;

        public ProdutoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProdutoBuilder nome(Nome nome) {
            this.nome = nome;
            return this;
        }

        public ProdutoBuilder categoria(Categoria categoria) {
            this.categoria = categoria;
            return this;
        }

        public ProdutoBuilder descricao(Descricao descricao) {
            this.descricao = descricao;
            return this;
        }

        public ProdutoBuilder preco(Preco preco) {
            this.preco = preco;
            return this;
        }

        public Produto build() {
            return new Produto(this);
        }
    }
}

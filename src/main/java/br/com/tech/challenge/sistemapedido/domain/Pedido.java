package br.com.tech.challenge.sistemapedido.domain;

import br.com.tech.challenge.sistemapedido.domain.vo.Data;
import br.com.tech.challenge.sistemapedido.domain.vo.Preco;

import java.math.BigDecimal;
import java.util.List;

public class Pedido {
    private Long id;
    private StatusPedido status;
    private Data dataCriacao;
    private Data dataAtualizacao;
    private List<ItemPedido> itens;
    private Preco total;
    private Boolean pago;
    private Usuario usuario;

    public Pedido(PedidoBuilder pedidoBuilder) {
        this.id = pedidoBuilder.id;
        this.status = pedidoBuilder.status;
        this.dataCriacao = pedidoBuilder.dataCriacao;
        this.dataAtualizacao = pedidoBuilder.dataAtualizacao;
        this.itens = pedidoBuilder.itens;
        this.total = pedidoBuilder.total;
        this.pago = pedidoBuilder.pago;
        this.usuario = pedidoBuilder.usuario;
    }

    public static PedidoBuilder builder() {
        return new PedidoBuilder();
    }

    public Long getId() {
        return id;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public Data getDataCriacao() {
        return dataCriacao;
    }

    public Data getDataAtualizacao() {
        return dataAtualizacao;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public Preco getTotal() {
        return total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean estaPago() {
        return Boolean.TRUE.equals(pago);
    }

    public void adicionarItem(ItemPedido item) {
        this.itens.add(item);
    }

    public void adicionarItens(List<ItemPedido> itens) {
        this.itens.clear();
        this.itens.addAll(itens);
    }
    public void adicionarUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void calcularTotal() {
        var valorTotal = this.itens.stream()
                .map(itemPedido ->
                        itemPedido.getPreco().getPreco()
                                .multiply(new BigDecimal(itemPedido.getQuantidade().getQuantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        this.total = new Preco(valorTotal);
    }

    public void pagar() {
        this.pago = Boolean.TRUE;
    }

    public void cancelar() {
        this.pago = Boolean.FALSE;
        this.status = StatusPedido.CANCELADO;
    }

    public void preparar() {
        this.status = StatusPedido.EM_PREPARACAO;
    }

    public void pronto() {
        this.status = StatusPedido.PRONTO;
    }

    public void finalizado() {
        this.status = StatusPedido.FINALIZADO;
    }

    public static class PedidoBuilder {
        private Long id;
        private StatusPedido status;
        private Data dataCriacao;
        private Data dataAtualizacao;
        private List<ItemPedido> itens;
        private Preco total;
        private Boolean pago;
        private Usuario usuario;

        public PedidoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PedidoBuilder status(StatusPedido status) {
            this.status = status;
            return this;
        }

        public PedidoBuilder dataCriacao(Data dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        public PedidoBuilder dataAtualizacao(Data dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        public PedidoBuilder itens(List<ItemPedido> itens) {
            this.itens = itens;
            return this;
        }

        public PedidoBuilder total(Preco total) {
            this.total = total;
            return this;
        }

        public PedidoBuilder pago(Boolean pago) {
            this.pago = pago;
            return this;
        }

        public PedidoBuilder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Pedido build() {
            return new Pedido(this);
        }
    }
}

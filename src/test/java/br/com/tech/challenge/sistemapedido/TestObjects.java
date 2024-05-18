package br.com.tech.challenge.sistemapedido;

import br.com.tech.challenge.sistemapedido.application.dto.PedidoDTO;
import br.com.tech.challenge.sistemapedido.application.dto.ProdutoDTO;
import br.com.tech.challenge.sistemapedido.application.mapper.ItemPedidoDataMapper;
import br.com.tech.challenge.sistemapedido.application.mapper.PedidoDataMapper;
import br.com.tech.challenge.sistemapedido.application.mapper.ProdutoDataMapper;
import br.com.tech.challenge.sistemapedido.domain.*;
import br.com.tech.challenge.sistemapedido.domain.vo.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

public class TestObjects {
    public static Produto getProduto(String nome) {
        return Produto.builder()
                .id(1L)
                .nome(new Nome("Produto Teste"))
                .categoria(Categoria.LANCHE)
                .descricao(new Descricao("Descrição Produto"))
                .preco(new Preco(BigDecimal.TEN))
                .build();
    }

    public static Usuario getUsuario() {
        return new Usuario("Usuario Teste",
                "19100000000",
                "teste@teste.com",
                "12345678",
                Set.of(new Papel("TEST")));
    }

    public static Pedido getPedido() {
        return Pedido.builder()
                .id(1L)
                .status(StatusPedido.RECEBIDO)
                .dataCriacao(new Data(LocalDateTime.now()))
                .dataAtualizacao(new Data(LocalDateTime.now()))
                .total(new Preco(BigDecimal.TEN))
                .itens(new ArrayList<>())
                .pago(Boolean.FALSE)
                .usuario(getUsuario())
                .build();
    }

    public static Pedido getPedidoComUsuario(Usuario usuario) {
        return Pedido.builder()
                .status(StatusPedido.RECEBIDO)
                .dataCriacao(new Data(LocalDateTime.now()))
                .dataAtualizacao(new Data(LocalDateTime.now()))
                .total(new Preco(BigDecimal.TEN))
                .itens(new ArrayList<>())
                .pago(Boolean.FALSE)
                .usuario(usuario)
                .build();
    }

    public static ItemPedido getItemPedido() {
        return ItemPedido.builder()
                .idProduto(1L)
                .nome(new Nome("Item Teste"))
                .categoria(Categoria.LANCHE)
                .descricao(new Descricao("Descrição Teste"))
                .quantidade(new Quantidade(1))
                .observacao(new Observacao("Observação teste"))
                .preco(new Preco(BigDecimal.TEN))
                .build();
    }

    public static PedidoDTO getPedidoDTO(Pedido pedido) {
        return new PedidoDataMapper(new ItemPedidoDataMapper()).toDTO(pedido);
    }

    public static ProdutoDTO getProdutoDTO() {
        return new ProdutoDataMapper().toDTO(getProduto("Produto Teste"));
    }
}

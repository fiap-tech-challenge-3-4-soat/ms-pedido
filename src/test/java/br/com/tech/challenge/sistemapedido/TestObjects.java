package br.com.tech.challenge.sistemapedido;

import br.com.tech.challenge.sistemapedido.domain.*;
import br.com.tech.challenge.sistemapedido.domain.vo.Data;
import br.com.tech.challenge.sistemapedido.domain.vo.Descricao;
import br.com.tech.challenge.sistemapedido.domain.vo.Nome;
import br.com.tech.challenge.sistemapedido.domain.vo.Preco;

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
}

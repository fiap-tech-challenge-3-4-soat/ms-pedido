package br.com.tech.challenge.sistemapedido.application.dto;

import br.com.tech.challenge.sistemapedido.domain.Categoria;

import java.math.BigDecimal;

public record ItemPedidoDTO(String nomeProduto,
                            String descricaoProduto,
                            Categoria categoriaProduto,
                            BigDecimal preco,
                            Integer quantidade,
                            String observacao) {
}

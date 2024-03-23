package br.com.tech.challenge.sistemapedido.application.dto;

import br.com.tech.challenge.sistemapedido.domain.Categoria;

import java.math.BigDecimal;

public record ProdutoDTO(Long id,
                         String nome,
                         Categoria categoria,
                         String descricao,
                         BigDecimal preco) {
}

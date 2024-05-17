package br.com.tech.challenge.sistemapedido.infrastructure.persistence.model;

import br.com.tech.challenge.sistemapedido.domain.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter

@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProdutoModel {
    //TODO remover

    private Long id;
    private String nome;
    private Categoria categoria;
    private BigDecimal preco;
    private String descricao;
    private String imagens;
}

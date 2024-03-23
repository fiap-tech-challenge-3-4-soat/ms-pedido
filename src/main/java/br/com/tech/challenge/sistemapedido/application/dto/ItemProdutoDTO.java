package br.com.tech.challenge.sistemapedido.application.dto;

public record ItemProdutoDTO(Long idProduto,
                             Integer quantidade,
                             String observacao) {}

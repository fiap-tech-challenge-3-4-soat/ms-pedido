package br.com.tech.challenge.sistemapedido.application.request;

import br.com.tech.challenge.sistemapedido.application.dto.ItemProdutoDTO;

import java.util.List;

public record PedidoRequest(List<ItemProdutoDTO> itens, String cpf) {}

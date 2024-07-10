package br.com.tech.challenge.sistemapedido.usecase.gateway;

import br.com.tech.challenge.sistemapedido.domain.Produto;

public interface ProdutoGateway {
    Produto buscarPorId(Long id);
}

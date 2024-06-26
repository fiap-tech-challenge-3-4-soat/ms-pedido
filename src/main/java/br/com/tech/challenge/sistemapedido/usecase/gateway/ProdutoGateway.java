package br.com.tech.challenge.sistemapedido.usecase.gateway;

import br.com.tech.challenge.sistemapedido.domain.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoGateway {
    Produto buscarPorId(Long id);
}

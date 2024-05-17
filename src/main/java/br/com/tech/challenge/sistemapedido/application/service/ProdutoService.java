package br.com.tech.challenge.sistemapedido.application.service;

import br.com.tech.challenge.sistemapedido.application.dto.ProdutoDTO;

public interface ProdutoService {
    ProdutoDTO buscarProdutoPorId(Long idProduto);
}

package br.com.tech.challenge.sistemapedido.application.gateway;

import br.com.tech.challenge.sistemapedido.application.mapper.ProdutoDataMapper;
import br.com.tech.challenge.sistemapedido.application.service.ProdutoService;
import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import jakarta.inject.Named;

@Named
public class ProdutoGatewayImpl implements ProdutoGateway {
    private final ProdutoService produtoService;
    private final ProdutoDataMapper produtoMapper;

    public ProdutoGatewayImpl(ProdutoService produtoService, ProdutoDataMapper produtoMapper) {
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public Produto buscarPorId(Long id) {
        var produtoDTO = produtoService.buscarProdutoPorId(id);
        return produtoMapper.toDomain(produtoDTO);
    }
}

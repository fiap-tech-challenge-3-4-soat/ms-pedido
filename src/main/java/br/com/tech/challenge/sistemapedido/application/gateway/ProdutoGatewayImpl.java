package br.com.tech.challenge.sistemapedido.application.gateway;

import br.com.tech.challenge.sistemapedido.application.mapper.ProdutoDataMapper;
import br.com.tech.challenge.sistemapedido.application.repository.ProdutoRepository;
import br.com.tech.challenge.sistemapedido.application.service.ProdutoService;
import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import jakarta.inject.Named;

import java.util.List;

@Named
public class ProdutoGatewayImpl implements ProdutoGateway {
    private final ProdutoRepository repository;
    private final ProdutoService produtoService;
    private final ProdutoDataMapper produtoMapper;

    public ProdutoGatewayImpl(ProdutoRepository repository, ProdutoService produtoService, ProdutoDataMapper produtoMapper) {
        this.repository = repository;
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public Produto buscarPorId(Long id) {
        var produtoDTO = produtoService.buscarProdutoPorId(id);
        return produtoMapper.toDomain(produtoDTO);
    }

    @Override
    public List<Produto> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    @Override
    public void excluir(Produto produto) {
        repository.delete(produto);
    }
}

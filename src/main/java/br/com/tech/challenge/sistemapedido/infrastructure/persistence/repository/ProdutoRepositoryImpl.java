package br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository;

import br.com.tech.challenge.sistemapedido.application.repository.ProdutoRepository;
import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.infrastructure.mapper.ProdutoModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {
    private final ProdutoModelMapper produtoMapper;

    public ProdutoRepositoryImpl(ProdutoModelMapper produtoMapper) {
        this.produtoMapper = produtoMapper;
    }

    @Override
    public Optional<Produto> findById(Long id) {
        return null;
    }

    @Override
    public List<Produto> findAll() {
        return null;
    }

    @Override
    public Produto save(Produto produto) {
        return null;
    }

    @Override
    public void delete(Produto produto) {
        var produtoModel = produtoMapper.toModel(produto);

    }
}

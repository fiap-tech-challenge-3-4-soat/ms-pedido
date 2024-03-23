package br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository;

import br.com.tech.challenge.sistemapedido.application.repository.ProdutoRepository;
import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.infrastructure.mapper.ProdutoModelMapper;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.ProdutoRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {
    private final ProdutoRepositoryJpa produtoRepository;
    private final ProdutoModelMapper produtoMapper;

    public ProdutoRepositoryImpl(ProdutoRepositoryJpa produtoRepository, ProdutoModelMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id)
                .map(produtoMapper::toDomain);
    }

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll()
                .stream()
                .map(produtoMapper::toDomain)
                .toList();
    }

    @Override
    public Produto save(Produto produto) {
        var produtoModel = produtoRepository.save(produtoMapper.toModel(produto));

        return produtoMapper.toDomain(produtoModel);
    }

    @Override
    public void delete(Produto produto) {
        var produtoModel = produtoMapper.toModel(produto);
        produtoRepository.delete(produtoModel);
    }
}

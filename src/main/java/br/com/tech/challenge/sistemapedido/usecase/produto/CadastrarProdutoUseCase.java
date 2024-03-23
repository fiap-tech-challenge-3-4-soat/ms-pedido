package br.com.tech.challenge.sistemapedido.usecase.produto;

import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import jakarta.inject.Named;

@Named
public class CadastrarProdutoUseCase {
    private final ProdutoGateway repository;

    public CadastrarProdutoUseCase(ProdutoGateway repository) {
        this.repository = repository;
    }

    public Produto executar(Produto produto) {
        return repository.salvar(produto);
    }
}

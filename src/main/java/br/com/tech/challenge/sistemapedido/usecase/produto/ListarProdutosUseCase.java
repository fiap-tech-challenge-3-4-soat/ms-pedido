package br.com.tech.challenge.sistemapedido.usecase.produto;

import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import jakarta.inject.Named;

import java.util.List;

@Named
public class ListarProdutosUseCase {
    private final ProdutoGateway gateway;

    public ListarProdutosUseCase(ProdutoGateway repository) {
        this.gateway = repository;
    }

    public List<Produto> executar() {
        return gateway.buscarTodos();
    }
}

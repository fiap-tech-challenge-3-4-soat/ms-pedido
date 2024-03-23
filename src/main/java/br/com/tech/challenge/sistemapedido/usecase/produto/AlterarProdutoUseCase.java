package br.com.tech.challenge.sistemapedido.usecase.produto;

import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.domain.exception.ProdutoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import jakarta.inject.Named;

@Named
public class AlterarProdutoUseCase {
    private final ProdutoGateway produtoGateway;

    public AlterarProdutoUseCase(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public Produto executar(Long id, Produto produtoAlterado) {
        var produtoAtual = this.produtoGateway.buscarPorId(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        var produtoAtualizado = atualizarDados(produtoAlterado, produtoAtual);

        return produtoGateway.salvar(produtoAtualizado);
    }

    private Produto atualizarDados(Produto novoProduto, Produto produtoAtual) {
        return Produto.builder()
                .id(produtoAtual.getId())
                .nome(novoProduto.getNome())
                .categoria(novoProduto.getCategoria())
                .descricao(novoProduto.getDescricao())
                .preco(novoProduto.getPreco())
                .build();
    }
}

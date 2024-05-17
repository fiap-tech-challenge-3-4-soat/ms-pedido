package br.com.tech.challenge.sistemapedido.infrastructure.service;

import br.com.tech.challenge.sistemapedido.application.dto.ProdutoDTO;
import br.com.tech.challenge.sistemapedido.application.service.ProdutoService;
import br.com.tech.challenge.sistemapedido.domain.exception.InternalErrorException;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.rest.msproduto.MSProdutoHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {
    private final MSProdutoHttpClient msProdutoHttpClient;

    @Override
    public ProdutoDTO buscarProdutoPorId(Long idProduto) {
        var response = msProdutoHttpClient.obterProduto(idProduto);
        var produtoResponse = response.getBody();

        if (Objects.isNull(produtoResponse) || Objects.isNull(produtoResponse.produto())) {
            throw new InternalErrorException(String.format("Não foi possível obter o produto com o ID: %d", idProduto));
        }

        return produtoResponse.produto();
    }
}

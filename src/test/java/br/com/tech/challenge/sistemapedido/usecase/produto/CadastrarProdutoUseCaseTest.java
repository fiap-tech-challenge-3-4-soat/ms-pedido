package br.com.tech.challenge.sistemapedido.usecase.produto;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarProdutoUseCaseTest {
    @Mock
    private ProdutoGateway gateway;
    @InjectMocks
    private CadastrarProdutoUseCase underTest;

    @Test
    void deveriaCadastrarUmProdutoComSucesso() {
        var produto = TestObjects.getProduto("Produto Teste");

        when(gateway.salvar(any(Produto.class)))
                .thenReturn(produto);

        underTest.executar(produto);

        verify(gateway).salvar(any(Produto.class));
    }
}
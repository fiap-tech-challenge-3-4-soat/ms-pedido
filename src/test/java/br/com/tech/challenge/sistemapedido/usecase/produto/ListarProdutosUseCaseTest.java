package br.com.tech.challenge.sistemapedido.usecase.produto;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarProdutosUseCaseTest {
    @Mock
    private ProdutoGateway gateway;
    @InjectMocks
    private ListarProdutosUseCase underTest;

    @Test
    void deveriaListarProdutosComSucesso() {
        var produto = TestObjects.getProduto("Produto Teste");

        when(gateway.buscarTodos())
                .thenReturn(List.of(produto));

        var response = underTest.executar();

        assertThat(response).hasSize(1);
        verify(gateway).buscarTodos();
    }
}
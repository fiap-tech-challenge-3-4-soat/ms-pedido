package br.com.tech.challenge.sistemapedido.usecase.produto;

import br.com.tech.challenge.sistemapedido.TestObjects;
import br.com.tech.challenge.sistemapedido.domain.Produto;
import br.com.tech.challenge.sistemapedido.domain.exception.ProdutoNaoEncontradoException;
import br.com.tech.challenge.sistemapedido.usecase.gateway.ProdutoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarProdutoUseCaseTest {
    @Mock
    private ProdutoGateway gateway;
    @InjectMocks
    private BuscarProdutoUseCase underTest;

    @Test
    void deveriaBuscarUmProdutoComSucesso() {
        var produto = TestObjects.getProduto("Produto Teste");

        when(gateway.buscarPorId(anyLong()))
                .thenReturn(Optional.of(produto));

        underTest.executar(1L);

        verify(gateway).buscarPorId(anyLong());
    }

    @Test
    void deveriaLancarExcecaoQuandoNaoEncontrarOProduto() {
        var idProduto = 2L;

        when(gateway.buscarPorId(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () ->
                underTest.executar(idProduto));

        verify(gateway).buscarPorId(anyLong());
    }
}
package br.com.tech.challenge.sistemapedido.usecase.gateway;

import br.com.tech.challenge.sistemapedido.domain.Papel;

import java.util.List;
import java.util.Optional;

public interface PapelGateway {
    Optional<Papel> buscarPorId(Long id);

    List<Papel> buscarTodos();

    Papel salvar(Papel papel);

    void excluir(Papel papel);

    Optional<Papel> buscarPorNome(String nome);
}

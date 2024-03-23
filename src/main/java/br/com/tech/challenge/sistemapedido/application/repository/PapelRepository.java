package br.com.tech.challenge.sistemapedido.application.repository;

import br.com.tech.challenge.sistemapedido.domain.Papel;

import java.util.List;
import java.util.Optional;

public interface PapelRepository {
    Optional<Papel> findById(Long id);
    List<Papel> findAll();
    Papel save(Papel papel);
    void delete(Papel papel);
    Optional<Papel> findByNome(String nome);
}

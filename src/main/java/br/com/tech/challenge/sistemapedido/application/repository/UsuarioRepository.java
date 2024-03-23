package br.com.tech.challenge.sistemapedido.application.repository;

import br.com.tech.challenge.sistemapedido.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findById(Long id);

    List<Usuario> findAll();

    Usuario save(Usuario usuario);

    void delete(Usuario usuario);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpfOrEmail(String cpf, String email);

    Optional<Usuario> findByCpf(String cpf);

    Boolean existsByCpf(String cpf);

    Boolean existsByEmail(String email);
}

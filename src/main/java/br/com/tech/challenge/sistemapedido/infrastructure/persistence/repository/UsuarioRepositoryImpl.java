package br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository;

import br.com.tech.challenge.sistemapedido.application.repository.UsuarioRepository;
import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.infrastructure.mapper.UsuarioModelMapper;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.UsuarioRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final UsuarioRepositoryJpa usuarioRepository;
    private final UsuarioModelMapper usuarioMapper;

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDomain)
                .toList();
    }

    @Override
    public Usuario save(Usuario usuario) {
        var usuarioModel = usuarioRepository.save(usuarioMapper.toModel(usuario));

        return usuarioMapper.toDomain(usuarioModel);
    }

    @Override
    public void delete(Usuario usuario) {
        var usuarioModel = usuarioMapper.toModel(usuario);
        usuarioRepository.delete(usuarioModel);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByCpfOrEmail(String cpf, String email) {
        return usuarioRepository.findByCpfOrEmail(cpf, email)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public Boolean existsByCpf(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return null;
    }
}

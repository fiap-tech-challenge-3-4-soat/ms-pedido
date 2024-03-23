package br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository;

import br.com.tech.challenge.sistemapedido.application.repository.PapelRepository;
import br.com.tech.challenge.sistemapedido.domain.Papel;
import br.com.tech.challenge.sistemapedido.infrastructure.mapper.PapelModelMapper;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.PapelRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PapelRepositoryImpl implements PapelRepository {
    private final PapelRepositoryJpa repository;
    private final PapelModelMapper papelMapper;

    @Override
    public Optional<Papel> findById(Long id) {
        return repository.findById(id)
                .map(papelMapper::toDomain);
    }

    @Override
    public List<Papel> findAll() {
        return repository.findAll().stream()
                .map(papelMapper::toDomain)
                .toList();
    }

    @Override
    public Papel save(Papel papel) {
        var papelModel = repository.save(papelMapper.toModel(papel));

        return papelMapper.toDomain(papelModel);
    }

    @Override
    public void delete(Papel papel) {
        var papelModel = papelMapper.toModel(papel);
        repository.delete(papelModel);
    }

    @Override
    public Optional<Papel> findByNome(String nome) {
        return repository.findByNome(nome)
                .map(papelMapper::toDomain);
    }
}

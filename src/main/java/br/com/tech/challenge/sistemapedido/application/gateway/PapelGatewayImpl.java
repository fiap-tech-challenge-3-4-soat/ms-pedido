package br.com.tech.challenge.sistemapedido.application.gateway;

import br.com.tech.challenge.sistemapedido.application.repository.PapelRepository;
import br.com.tech.challenge.sistemapedido.domain.Papel;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PapelGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PapelGatewayImpl implements PapelGateway {
    private final PapelRepository repository;

    @Override
    public Optional<Papel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Papel> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public Papel salvar(Papel papel) {
        return repository.save(papel);
    }

    @Override
    public void excluir(Papel papel) {
        repository.delete(papel);
    }

    @Override
    public Optional<Papel> buscarPorNome(String nome) {
        return repository.findByNome(nome);
    }
}

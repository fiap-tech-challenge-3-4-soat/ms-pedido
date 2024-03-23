package br.com.tech.challenge.sistemapedido.infrastructure.mapper;

import br.com.tech.challenge.sistemapedido.domain.Papel;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.PapelModel;
import org.springframework.stereotype.Component;

@Component
public class PapelModelMapper {
    public Papel toDomain(PapelModel papel) {
        return new Papel(papel.getId(),
                papel.getNome()
        );
    }

    public PapelModel toModel(Papel papel) {
        return PapelModel.builder()
                .id(papel.getId())
                .nome(papel.getNome())
                .build();
    }
}

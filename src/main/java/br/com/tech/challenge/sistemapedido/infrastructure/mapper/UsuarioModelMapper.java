package br.com.tech.challenge.sistemapedido.infrastructure.mapper;

import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.UsuarioModel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UsuarioModelMapper {
    private final PapelModelMapper papelMapper;

    public UsuarioModelMapper(PapelModelMapper papelMapper) {
        this.papelMapper = papelMapper;
    }

    public Usuario toDomain(UsuarioModel usuario) {
        return new Usuario(usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getPapeis().stream().map(papelMapper::toDomain).collect(Collectors.toSet())
        );
    }

    public UsuarioModel toModel(Usuario usuario) {
        return UsuarioModel.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .cpf(usuario.getCpf())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .papeis(usuario.getPapeis().stream().map(papelMapper::toModel).collect(Collectors.toSet()))
                .build();
    }
}

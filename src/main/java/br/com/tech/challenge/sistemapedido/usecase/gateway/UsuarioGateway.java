package br.com.tech.challenge.sistemapedido.usecase.gateway;

import br.com.tech.challenge.sistemapedido.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioGateway {
    Optional<Usuario> buscarPorId(Long id);

    List<Usuario> buscarTodos();

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    Optional<Usuario> buscarPorCpfOuEmail(String cpf, String email);

    Optional<Usuario> buscarPorCpf(String cpf);

    String autenticar(String cpf, String senha);

    Usuario registrar(Usuario usuario);
}

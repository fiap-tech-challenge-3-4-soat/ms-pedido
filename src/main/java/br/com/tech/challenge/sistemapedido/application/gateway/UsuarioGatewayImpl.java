package br.com.tech.challenge.sistemapedido.application.gateway;

import br.com.tech.challenge.sistemapedido.application.repository.UsuarioRepository;
import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.usecase.gateway.UsuarioGateway;
import br.com.tech.challenge.sistemapedido.application.service.AutenticacaoService;
import jakarta.inject.Named;

import java.util.List;
import java.util.Optional;

@Named
public class UsuarioGatewayImpl implements UsuarioGateway {
    private final UsuarioRepository repository;
    private final AutenticacaoService autenticacaoService;

    public UsuarioGatewayImpl(UsuarioRepository repository, AutenticacaoService autenticacaoService) {
        this.repository = repository;
        this.autenticacaoService = autenticacaoService;
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> buscarPorCpfOuEmail(String cpf, String email) {
        return repository.findByCpfOrEmail(cpf, email);
    }

    @Override
    public Optional<Usuario> buscarPorCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    @Override
    public String autenticar(String cpf, String senha) {
        return autenticacaoService.autenticar(cpf, senha);
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        return autenticacaoService.registrar(usuario);
    }
}

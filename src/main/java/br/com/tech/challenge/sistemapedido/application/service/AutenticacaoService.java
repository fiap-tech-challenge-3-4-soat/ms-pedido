package br.com.tech.challenge.sistemapedido.application.service;

import br.com.tech.challenge.sistemapedido.domain.Usuario;

public interface AutenticacaoService {
    String autenticar(String cpf, String senha);

    Usuario registrar(Usuario usuario);
}

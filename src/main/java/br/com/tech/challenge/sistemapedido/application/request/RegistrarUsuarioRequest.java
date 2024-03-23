package br.com.tech.challenge.sistemapedido.application.request;

import br.com.tech.challenge.sistemapedido.domain.Papel;

import java.util.Set;

public record RegistrarUsuarioRequest(String nome,
                                      String cpf,
                                      String email,
                                      String senha,
                                      Set<Papel> papeis) {
}

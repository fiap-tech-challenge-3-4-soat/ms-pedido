package br.com.tech.challenge.sistemapedido.application.request;

import br.com.tech.challenge.sistemapedido.domain.Papel;

import java.util.Set;

public record CancelarUsuarioRequest(String cpf,
                                     String nome,
                                     String endereco,
                                     String numeroDeTelefone,
                                     String informacaoDePagamento) {
}

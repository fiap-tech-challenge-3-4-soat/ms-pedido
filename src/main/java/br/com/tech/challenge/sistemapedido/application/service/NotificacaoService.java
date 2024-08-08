package br.com.tech.challenge.sistemapedido.application.service;

import br.com.tech.challenge.sistemapedido.domain.Usuario;

public interface NotificacaoService {
    void notificar(Usuario usuario, String assunto, String mensagem);
}

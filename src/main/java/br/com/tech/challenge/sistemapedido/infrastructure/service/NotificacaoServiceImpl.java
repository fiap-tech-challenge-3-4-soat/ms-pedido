package br.com.tech.challenge.sistemapedido.infrastructure.service;

import br.com.tech.challenge.sistemapedido.application.service.NotificacaoService;
import br.com.tech.challenge.sistemapedido.domain.Usuario;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Log4j2
@Service
public class NotificacaoServiceImpl implements NotificacaoService {
    @Value("${sendgrid.api-key}")
    private String apiKey;

    @Override
    public void notificar(Usuario usuario, String assunto, String mensagem) {
        if (Objects.nonNull(usuario.getEmail()) && !usuario.getEmail().isEmpty()) {
            Email from = new Email("sistema-pedido@sistema-pedido.com");
            Email to = new Email(usuario.getEmail());
            Content content = new Content("text/plain", mensagem);
            Mail mail = new Mail(from, assunto, to, content);
            SendGrid sg = new SendGrid(apiKey);
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                sg.api(request);
            } catch (IOException ex) {
                log.error(ex);
            }
        }
    }
}

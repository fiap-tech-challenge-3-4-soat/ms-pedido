package br.com.tech.challenge.sistemapedido.infrastructure.config;

import br.com.tech.challenge.sistemapedido.infrastructure.integration.rest.mercadopago.EventoConfirmacaoPagamento;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, EventoConfirmacaoPagamento> {
    @Override
    public EventoConfirmacaoPagamento convert(String source) {
        return EventoConfirmacaoPagamento.valueOf(source.toUpperCase());
    }
}

package br.com.tech.challenge.sistemapedido.infrastructure.exception;

import br.com.tech.challenge.sistemapedido.application.dto.InputErrorDTO;
import br.com.tech.challenge.sistemapedido.domain.exception.ProdutoNaoEncontradoException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Component
public class FeignDecoderHandler implements ErrorDecoder {
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
    @Override
    public Exception decode(String s, Response response) {
        try {
            String inputStream = StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8);

            if (response.status() == HttpStatus.NOT_FOUND.value()) {
                var errorDTO =  mapper.readValue(inputStream, InputErrorDTO.class);
                throw new ProdutoNaoEncontradoException(errorDTO.mensagem());
            }

            return mapper.readValue(inputStream, DefaultFeignException.class);
        } catch (Exception exception) {
            throw new DecodeException("Não foi possível decodificar a exceção", exception);
        }
    }
}

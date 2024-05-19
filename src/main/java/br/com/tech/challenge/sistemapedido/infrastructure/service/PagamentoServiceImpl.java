package br.com.tech.challenge.sistemapedido.infrastructure.service;

import br.com.tech.challenge.sistemapedido.application.service.PagamentoService;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.exception.InternalErrorException;
import br.com.tech.challenge.sistemapedido.domain.exception.SistemaPedidosAPIException;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.rest.mspagamento.MSPagamentoHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {
    private final MSPagamentoHttpClient msPagamentoHttpClient;

    @Override
    public byte[] gerarQrCode(Pedido pedido) {
        var response = msPagamentoHttpClient.gerarQrCode(pedido.getId());
        var qrCodeImage = response.getBody();

        if (Objects.isNull(qrCodeImage)) {
            throw new SistemaPedidosAPIException(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível obter o QR-Code");
        }

        return qrCodeImage;
    }
}

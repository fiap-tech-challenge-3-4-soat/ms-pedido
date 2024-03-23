package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1;

import br.com.tech.challenge.sistemapedido.application.controller.PagamentoController;
import br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1.openapi.PagamentoResourceOpenApi;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.rest.mercadopago.EventoConfirmacaoPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pedidos")
public class PagamentoResource implements PagamentoResourceOpenApi {
    private final PagamentoController controller;

    @Override
    @PostMapping("/{idPedido}/gerar-pagamento")
    public ResponseEntity<ByteArrayResource> gerarPagamento(@PathVariable Long idPedido) throws IOException {
        var arquivo = controller.gerarPagamentoPorQrCode(idPedido);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mercadopago.png");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(arquivo.length())
                .contentType(MediaType.IMAGE_PNG)
                .body(new ByteArrayResource(Files.readAllBytes(arquivo.toPath())));
    }

    @Override
    @PostMapping("/confirmar-pagamento")
    public ResponseEntity<Void> receberConfirmacaoPagamento(@RequestParam(required = false) Long id,
                                                            @RequestParam(required = false) EventoConfirmacaoPagamento topic) {

        if (EventoConfirmacaoPagamento.MOCK.equals(topic) && Objects.nonNull(id)) {
            controller.pagar(id);
        }

        if (EventoConfirmacaoPagamento.MERCHANT_ORDER.equals(topic) && Objects.nonNull(id)) {
            controller.receberConfirmacaoPagamento(id);
        }

        return ResponseEntity.ok().build();
    }
}

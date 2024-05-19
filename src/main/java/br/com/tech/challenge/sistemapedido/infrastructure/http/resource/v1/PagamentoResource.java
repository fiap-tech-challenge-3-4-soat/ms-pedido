package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1;

import br.com.tech.challenge.sistemapedido.application.controller.PagamentoController;
import br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1.openapi.PagamentoResourceOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pedidos")
public class PagamentoResource implements PagamentoResourceOpenApi {
    private final PagamentoController controller;

    @Override
    @PostMapping("/{idPedido}/gerar-pagamento")
    public ResponseEntity<ByteArrayResource> gerarPagamento(@PathVariable Long idPedido) {
        var arquivo = controller.gerarPagamentoPorQrCode(idPedido);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mercadopago.png");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.IMAGE_PNG)
                .body(new ByteArrayResource(arquivo));
    }

    @PutMapping("/{idPedido}/confirmar-pagamento")
    public ResponseEntity<Void> receberConfirmacaoPagamento(@PathVariable Long idPedido) {
        controller.pagar(idPedido);

        return ResponseEntity.ok().build();
    }
}

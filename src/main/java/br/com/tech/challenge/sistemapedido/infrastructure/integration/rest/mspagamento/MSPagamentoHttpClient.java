package br.com.tech.challenge.sistemapedido.infrastructure.integration.rest.mspagamento;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ms-pagamento", url = "${rest.service.ms-pagamento.url}/${rest.service.ms-pagamento.context}")
public interface MSPagamentoHttpClient {

    @PostMapping("/pagamentos/{idPedido}")
    ResponseEntity<byte[]> gerarQrCode(@PathVariable Long idPedido);
}

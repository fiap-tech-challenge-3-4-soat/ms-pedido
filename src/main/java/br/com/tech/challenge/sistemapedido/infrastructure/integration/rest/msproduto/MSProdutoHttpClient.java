package br.com.tech.challenge.sistemapedido.infrastructure.integration.rest.msproduto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-produto", url = "${rest.service.ms-produto.url}/${rest.service.ms-produto.context}")
public interface MSProdutoHttpClient {

    @GetMapping("/produtos/{idProduto}")
    ResponseEntity<ConsultarProdutoResponse> obterProduto(@PathVariable Long idProduto);
}

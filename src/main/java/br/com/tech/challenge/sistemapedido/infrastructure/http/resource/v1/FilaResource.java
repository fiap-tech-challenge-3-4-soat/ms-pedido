package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1;

import br.com.tech.challenge.sistemapedido.application.controller.FilaController;
import br.com.tech.challenge.sistemapedido.application.response.ListarPedidosResponse;
import br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1.openapi.FilaResourceOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pedidos/fila")
public class FilaResource implements FilaResourceOpenApi {
    private final FilaController controller;

    @Override
    @GetMapping("restaurante")
    public ResponseEntity<ListarPedidosResponse> listarPedidosRestaurante() {
        var resposta = controller.filaRestaurante();

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @Override
    @GetMapping("cliente")
    public ResponseEntity<ListarPedidosResponse> listarPedidosCliente() {
        var resposta = controller.filaCLiente();

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }
}

package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1.openapi;

import br.com.tech.challenge.sistemapedido.application.dto.InputErrorDTO;
import br.com.tech.challenge.sistemapedido.application.request.PedidoRequest;
import br.com.tech.challenge.sistemapedido.application.response.CadastrarPedidoResponse;
import br.com.tech.challenge.sistemapedido.application.response.ListarPedidosResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Fila de Pedidos", description = "Listagem de pedidos de acordo com a fila")
public interface FilaResourceOpenApi {

    @Operation(summary = "Lista os pedidos para o restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    ResponseEntity<ListarPedidosResponse> listarPedidosRestaurante();

    @Operation(summary = "Lista os pedidos para o cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    ResponseEntity<ListarPedidosResponse> listarPedidosCliente();
}

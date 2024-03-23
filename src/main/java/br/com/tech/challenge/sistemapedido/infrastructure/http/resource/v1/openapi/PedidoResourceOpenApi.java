package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1.openapi;

import br.com.tech.challenge.sistemapedido.application.dto.InputErrorDTO;
import br.com.tech.challenge.sistemapedido.application.request.PedidoRequest;
import br.com.tech.challenge.sistemapedido.application.response.CadastrarPedidoResponse;
import br.com.tech.challenge.sistemapedido.application.response.ListarPedidosResponse;
import br.com.tech.challenge.sistemapedido.application.response.StatusPedidoResponse;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.rest.mercadopago.EventoConfirmacaoPagamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@Tag(name = "Pedidos", description = "Gerencia os pedidos")
public interface PedidoResourceOpenApi {
    @Operation(summary = "Cadastra um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "422",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InputErrorDTO.class)) }
            )

    })
    ResponseEntity<CadastrarPedidoResponse> criar(PedidoRequest request);

    @Operation(summary = "Lista os pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    ResponseEntity<ListarPedidosResponse> listar();

    @Operation(summary = "Altera o status do pedido para Em Preparação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            value="{\"mensagem\": \"Pedido não encontrado\"}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "422",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InputErrorDTO.class)) }
            )
    })
    ResponseEntity<Void> preparacao(Long id);

    @Operation(summary = "Altera o status do pedido para Pronto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            value="{\"mensagem\": \"Pedido não encontrado\"}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "422",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InputErrorDTO.class)) }
            )
    })
    ResponseEntity<Void> pronto(Long id);

    @Operation(summary = "Altera o status do pedido para Finalizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            value="{\"mensagem\": \"Pedido não encontrado\"}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "422",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InputErrorDTO.class)) }
            )
    })
    ResponseEntity<Void> finalizado(Long id);

    @Operation(summary = "Verifica o status de pagamento do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            value="{\"mensagem\": \"Pedido não encontrado\"}"
                                    )
                            }
                    )
            )
    })
    ResponseEntity<StatusPedidoResponse> verificarStatus(Long id);
}

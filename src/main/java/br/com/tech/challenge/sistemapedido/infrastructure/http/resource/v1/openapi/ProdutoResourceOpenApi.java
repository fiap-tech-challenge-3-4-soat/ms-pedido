package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1.openapi;

import br.com.tech.challenge.sistemapedido.application.dto.InputErrorDTO;
import br.com.tech.challenge.sistemapedido.application.request.ProdutoRequest;
import br.com.tech.challenge.sistemapedido.application.response.CadastrarProdutoResponse;
import br.com.tech.challenge.sistemapedido.application.response.ListarProdutosResponse;
import br.com.tech.challenge.sistemapedido.application.response.ProdutoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Produtos", description = "Gerencia os produtos")
public interface ProdutoResourceOpenApi {
    @Operation(summary = "Lista os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    ResponseEntity<ListarProdutosResponse> listar();
    
    @Operation(summary = "Busca um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404",
                    description = "Produto não encontrado",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            value="{\"mensagem\": \"Produto não encontrado\"}"
                                    )
                            }
                    )
            ),

    })
    ResponseEntity<ProdutoResponse> buscar(Long id);

    @Operation(summary = "Cadastra um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "422",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InputErrorDTO.class)) }
            )

    })
    ResponseEntity<CadastrarProdutoResponse> criar(ProdutoRequest request);

    @Operation(summary = "Altera um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404",
                    description = "Produto não encontrado",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            value="{\"mensagem\": \"Produto não encontrado\"}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "422",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InputErrorDTO.class)) }
            )

    })
    ResponseEntity<ProdutoResponse> alterar(Long id, ProdutoRequest request);

    @Operation(summary = "Deleta um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404",
                    description = "Produto não encontrado",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            value="{\"mensagem\": \"Produto não encontrado\"}"
                                    )
                            }
                    )
            )

    })
    ResponseEntity<Void> excluir(Long id);
}

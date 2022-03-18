package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

	@ApiOperation("Lista os usuários responsáveis associados a restaurante")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<UsuarioDTO>  listar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Desassociação de restaurante com usuário responsável")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso", 
				content = @Content(mediaType = "application/json")),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado", 
		content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
	})
    ResponseEntity<Void> desassociar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId);

    @ApiOperation("Associação de restaurante com usuário responsável")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Associação realizada com sucesso", 
				content = @Content(mediaType = "application/json")),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado", 
		content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
	})
    ResponseEntity<Void> associar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId);
}
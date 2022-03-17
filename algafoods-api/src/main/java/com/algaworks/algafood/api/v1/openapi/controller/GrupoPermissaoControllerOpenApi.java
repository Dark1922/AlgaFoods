package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.PermissaoDTO;
import com.algaworks.algafood.api.v1.openapi.model.PermissoesModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Api(tags = "Grupos Permissões")
public interface GrupoPermissaoControllerOpenApi {
    
    @ApiOperation("Lista as permissões associadas a um grupo")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class),
        @ApiResponse(code = 200, message = "OK", response = PermissoesModelOpenApi.class)
    })
    ResponseEntity<CollectionModel<PermissaoDTO>> listar(
            @ApiParam(value = "ID do grupo", example = "1", required = true)
            Long grupoId);

  
    
    @ApiOperation("Desassociação de permissão com grupo")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso", 
				content = @Content(mediaType = "application/json")),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrada", 
		content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
	})
    ResponseEntity<Void> desassociar(
            @ApiParam(value = "ID do grupo", example = "1", required = true)
            Long grupoId,
            
            @ApiParam(value = "ID da permissão", example = "1", required = true)
            Long permissaoId);

    
   
    @ApiOperation("Associação de permissão com grupo")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Associação realizada com sucesso", 
				content = @Content(mediaType = "application/json")),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "ID da cidade é inválido", 
		content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
	})
    ResponseEntity<Void> associar(
            @ApiParam(value = "ID do grupo", example = "1", required = true)
            Long grupoId,
            
            @ApiParam(value = "ID da permissão", example = "1", required = true)
            Long permissaoId);
    
//  @ApiResponses({
//  @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
//  @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada", 
//      response = Problem.class)
//})
}        
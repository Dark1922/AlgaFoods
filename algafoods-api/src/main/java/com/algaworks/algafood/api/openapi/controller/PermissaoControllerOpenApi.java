package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.api.openapi.model.PermissoesModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

	 @ApiOperation("Lista as permissões")
	 @ApiResponses(value = {
			 @ApiResponse(code = 200, message = "OK", response = PermissoesModelOpenApi.class)
			 })
	    ResponseEntity<CollectionModel<PermissaoDTO>> listar();
}

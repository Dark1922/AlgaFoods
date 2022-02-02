package com.algaworks.algafood.api.openapi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.RestauranteBasicModelOpenApi;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {


	    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicModelOpenApi.class)
	    @ApiImplicitParams({
	        @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
	                name = "projecao", paramType = "query", type = "string")
	    })
	     List<RestauranteDTO> listar();
	    
	    @ApiOperation(value = "Lista restaurantes", hidden = true)
	     List<RestauranteDTO> listarApenasNomes();
	    
	    @ApiOperation("Busca um restaurante por ID")
	    @ApiResponses({
	        @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
	        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	    })
	     RestauranteDTO buscar(
	            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
	            Long restauranteId);
	    
	    @ApiOperation("Cadastra um restaurante")
	    @ApiResponses({
	        @ApiResponse(code = 201, message = "Restaurante cadastrado"),
	    })
	     RestauranteDTO adicionar(
	            @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
	            RestauranteInput restauranteInput);
	    
	    @ApiOperation("Atualiza um restaurante por ID")
	    @ApiResponses({
	        @ApiResponse(code = 200, message = "Restaurante atualizado"),
	        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	    })
	     RestauranteDTO atualizar(
	            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
	            Long restauranteId,
	            
	            @ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", 
	                required = true)
	            RestauranteInput restauranteInput);
	    
	    @ApiOperation("Ativa um restaurante por ID")
	    @ApiResponses({
	        @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
	        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	    })
	     void ativar(
	            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
	            Long restauranteId);
	    
	    @ApiOperation("Inativa um restaurante por ID")
	    @ApiResponses({
	        @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
	        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	    })
	     void inativar(
	            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
	            Long restauranteId);
	    
	    @ApiOperation("Ativa múltiplos restaurantes")
	    @ApiResponses({
	        @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	    })
	     void ativarMultiplosRestaurantes(
	            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
	            List<Long> restauranteIds);
	    
	    @ApiOperation("Inativa múltiplos restaurantes")
	    @ApiResponses({
	        @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	    })
	     void inativarMultiplosRestaurantes(
	            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
	            List<Long> restauranteIds);

	    @ApiOperation("Abre um restaurante por ID")
	    @ApiResponses({
	        @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
	        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	    })
	     void abrir(
	            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
	            Long restauranteId);
	    
	    @ApiOperation("Fecha um restaurante por ID")
	    @ApiResponses({
	        @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
	        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	    })
	     void fechar(
	            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
	            Long restauranteId);
	    
		@ApiOperation("Atualiza dado especifico")
	    @ApiResponses({
	        @ApiResponse(code = 200, message = "Restaurante atualizado"),
	        @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
	        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	    })
		
		 RestauranteDTO atualizarParcial( @PathVariable Long restauranteId,
				@ApiParam(value = "nome, taxaFrete, ativo,inativo", example = "1", required = true)
		 @RequestBody @Valid Map<String, Object> campos, HttpServletRequest request);
				
			
		
}

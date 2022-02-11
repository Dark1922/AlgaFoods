package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.EstadoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("EstadosModel")
@Data
public class EstadoModelOpenApi {//classe para modelagem de documentação

	private EstadoEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("EstadosEmbeddedModel")
	@Data
	public class EstadoEmbeddedModelOpenApi {
		private List<EstadoDTO> estados;
	}
}

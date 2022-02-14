package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.ProdutoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("ProdutosModel")
@Data
public class ProdutosModelOpenApi {//classe para modelagem de documentação

	private ProdutosEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("ProdutosEmbeddedModel")
	@Data
	public class ProdutosEmbeddedModelOpenApi {
		private List<ProdutoDTO> produtos;
	}
}

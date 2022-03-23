package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("FormasPagamentoModel")
@Data
public class FormasPagamentoModelOpenApi {//classe para modelagem de documentação

	private FormasPagamentoEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("FormaPagamentosEmbeddedModel")
	@Data
	public class FormasPagamentoEmbeddedModelOpenApi {
		private List<FormaPagamentoDTO> formasPagamento;
	}
}

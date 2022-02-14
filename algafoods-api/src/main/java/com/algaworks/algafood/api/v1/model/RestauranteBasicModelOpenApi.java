package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestauranteBasicModel")
@Getter
@Setter
public class RestauranteBasicModelOpenApi {


	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	private String nome;
	
	@ApiModelProperty(example = "17.00")
	//@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	private CozinhaDTO cozinha;
}

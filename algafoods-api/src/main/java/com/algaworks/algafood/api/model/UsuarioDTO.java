package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "João da Silva")
	private String nome;

	@ApiModelProperty(example = "joao.ger@algafood.com.br")
	private String email;
	
}

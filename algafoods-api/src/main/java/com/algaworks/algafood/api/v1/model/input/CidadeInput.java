package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	@ApiModelProperty(example = "Ubelândia", required = true)
	@NotBlank
	private String nome;
	 
	 
	@Valid //verificar se os métodos do estados são validos
	@NotNull
	private EstadoIdInput estado;
}

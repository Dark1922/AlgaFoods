package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.domain.model.Estado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	 @NotBlank
	private String nome;
	 
	 
	 @Valid //verificar se os métodos do estados são validos
	    @NotNull
	private Estado estado;
}

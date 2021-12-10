package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.Estado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeDTO {

	private Long id;
	private String nome;
	private Estado estado;


}

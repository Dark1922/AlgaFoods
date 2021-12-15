package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	@NotBlank
	private String cep;

	@NotBlank
	private String logradouro;

	@NotBlank
	private String numero;

	private String complemento; //opcional

	@NotBlank
	private String bairro;

	@Valid //validar os elementos dentro do idInput
	@NotNull //cidade é obrigatório
	private CidadeIdInput cidade;
}

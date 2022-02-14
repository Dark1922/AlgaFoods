package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	@ApiModelProperty(example = "38400-000", required = true)
	@NotBlank
	private String cep;

	@ApiModelProperty(example = "Rua Floriano Peixoto", required = true)
	@NotBlank
	private String logradouro;

	@ApiModelProperty(example = "157", required = true)
	@NotBlank
	private String numero;

	@ApiModelProperty(example = "Apto 901")
	private String complemento; //opcional

	@ApiModelProperty(example = "Centro", required = true)
	@NotBlank
	private String bairro;

	@ApiModelProperty(example = "1", required = true)
	@Valid //validar os elementos dentro do idInput
	@NotNull //cidade é obrigatório
	private CidadeIdInput cidade;
}

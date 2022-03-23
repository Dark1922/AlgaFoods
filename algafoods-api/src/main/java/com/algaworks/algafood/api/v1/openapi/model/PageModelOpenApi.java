package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "PageModel")
@Getter
@Setter
public class PageModelOpenApi<T>  {


	@ApiModelProperty(example = "1", value = "Quantidade de registros por página")
	private int size;

	@ApiModelProperty(example = "1", value = "Total de registros")
	private int totalElements;

	@ApiModelProperty(example = "1", value = "Total de páginas")
	private int totalPage;

	@ApiModelProperty(example = "1", value = "Número da página (começa em 0)")
	private int number;

	@ApiModelProperty(example = "nome,asc", value = "Nome da propriedade para ordenação")
	private List<String> sort;


}
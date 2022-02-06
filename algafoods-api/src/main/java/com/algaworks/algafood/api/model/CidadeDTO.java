package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description =  "Representa uma cidade") //muda o nome de cidadeDTO para cidade swagger
@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

	@ApiModelProperty(value = "ID da cidade" , example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Sobradinho")
	private String nome;
	
	@ApiModelProperty(example = "Distrito Federal")
	private EstadoDTO estado;


}

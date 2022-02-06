package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {
	
	 @ApiModelProperty(example = "1")
	 private Long id;
	
	 @ApiModelProperty(example = "Distrito Federal")
     private String nome;
}

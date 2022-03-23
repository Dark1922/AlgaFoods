package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoFilter { //representar as propriedades que eu quero fazer uma consulta
	//pode fazer uma consulta passando o id do cliente e ou restaurante e ou e assim vai
	
	@ApiModelProperty(example = "1", value = "ID do cliente para filtro da pesquisa")
	private Long clienteId;

	@ApiModelProperty(example = "1", value = "ID do restaurante para filtro da pesquisa")
	private Long restauranteId;
	
	@ApiModelProperty(example = "2019-10-30T00:00:00Z", value = "Data/hora de criação inicial para filtro da pesquisa")
	       
	@DateTimeFormat(iso = ISO.DATE_TIME) //força a formatação para o datetime do iso "2000-10-31T01:30:00.000-05:00"
	private OffsetDateTime dataCriacaoInicio;
	
	@ApiModelProperty(example = "2019-11-01T10:00:00Z",value = "Data/hora de criação final para filtro da pesquisa")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;

}

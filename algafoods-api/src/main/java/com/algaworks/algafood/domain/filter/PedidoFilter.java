package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoFilter { //representar as propriedades que eu quero fazer uma consulta
	//pode fazer uma consulta passando o id do cliente e ou restaurante e ou e assim vai
	private Long clienteId;
	private Long RestauranteId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME) //força a formatação para o datetime do iso "2000-10-31T01:30:00.000-05:00"
	private OffsetDateTime dataCriacaoInicio;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;

}

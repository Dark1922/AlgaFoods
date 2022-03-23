package com.algaworks.algafood.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;
 
@Getter
@Setter
public class VendaDiariaFilter {
	
      private Long RestauranteId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME) //força a formatação para o datetime do iso "2000-10-31T01:30:00.000-05:00"
	private OffsetDateTime dataCriacaoInicio;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;

}

package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	
	byte[] emitirVendasDiaria(VendaDiariaFilter filtro, String timeOfset); //espera receber os byte de binario do pdf

}

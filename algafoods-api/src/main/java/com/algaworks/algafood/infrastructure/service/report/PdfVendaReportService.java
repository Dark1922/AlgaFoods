package com.algaworks.algafood.infrastructure.service.report;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaReportService;

@Service
public class PdfVendaReportService implements VendaReportService {//infraestrutura n tem a ver com dominio negocio ou api

	@Override
	public byte[] emitirVendasDiaria(VendaDiariaFilter filtro, String timeOfset) {
		return null;
	} 

}

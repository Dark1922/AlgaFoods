package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping(path = "/estatisticas", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstatisticasController implements EstatisticasControllerOpenApi{

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE) //required false tudo bem se n ultilizar um parametro
	public 	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
	 @RequestParam(required = false, defaultValue = "+00:00") String timeOfset) {
		return vendaQueryService.consultarVendasDiaria(filtro, timeOfset);
	} 
	
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE) 
	public 	ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
	 @RequestParam(required = false, defaultValue = "+00:00") String timeOfset) {
		
		byte[] bytesPdf = vendaReportService.emitirVendasDiaria(filtro, timeOfset);
		
		var headers = new HttpHeaders(); //filename nome padrão ao baixar o pdf
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diaria.pdf");//attachment relatório para dowload
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
				.body(bytesPdf);
	}
}

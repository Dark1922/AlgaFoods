package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algafood.core.linkhateos.AlgaLinks;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping(path = "/v1/estatisticas", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstatisticasController implements EstatisticasControllerOpenApi{

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@CheckSecurity.Estatisticas.PodeConsultar
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE) //required false tudo bem se n ultilizar um parametro
	public ResponseEntity<List<VendaDiaria>> consultarVendasDiarias(VendaDiariaFilter filtro,
	 @RequestParam(required = false, defaultValue = "+00:00") String timeOfset) {
		return ResponseEntity.ok().body(vendaQueryService.consultarVendasDiaria(filtro, timeOfset));
	} 
	
	@CheckSecurity.Estatisticas.PodeConsultar
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
	
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public EstatisticasModel estatisticas() {
	    var estatisticasModel = new EstatisticasModel();
	    
	    estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
	    
	    return estatisticasModel;
	} 
	
	public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
	}
}

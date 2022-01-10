package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController {

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@GetMapping("/vendas-diarias") //required false tudo bem se n ultilizar um parametro
	public 	List<VendaDiaria> consultarVendasDiaria(VendaDiariaFilter filtro,
	 @RequestParam(required = false, defaultValue = "+00:00") String timeOfset) {
		return vendaQueryService.consultarVendasDiaria(filtro, timeOfset);
	}
}

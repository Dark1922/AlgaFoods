package com.algaworks.algafood.api.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // tratar o mapeamento do recurso foto
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestaurantePedidoFotoController {

	@PutMapping
	public void atualizarFoto() {
		
	}
}

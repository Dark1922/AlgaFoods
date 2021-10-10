package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository {
	
	List<Restaurante> listar();
	Restaurante buscarId(Long id);
	Restaurante adiciona(Restaurante restaurante);
	void remover(Restaurante restaurante);

}

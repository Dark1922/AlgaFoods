package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository {
	
	List<Restaurante> listar();
	Cozinha buscarId(Long id);
	Cozinha adiciona(Restaurante restaurante);
	void remover(Restaurante restaurante);

}

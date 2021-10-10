package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;

public interface RestauranteRepository {
	
	List<Cozinha> listar();
	Cozinha buscarId(Long id);
	Cozinha adiciona(Cozinha cozinha);
	void remover(Cozinha cozinha);

}

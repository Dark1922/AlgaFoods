package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;

public interface CozinhaRepository {
	
	List<Cozinha> listar();
	Cozinha buscarId(Long id);
	Cozinha adiciona(Cozinha cozinha);
	void remover(Long id);

}

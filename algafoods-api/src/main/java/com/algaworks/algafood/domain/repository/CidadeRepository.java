package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cidade;

public interface CidadeRepository {

	List<Cidade> listar();
	Cidade buscarId(Long id);
	Cidade adiciona(Cidade estado);
	void remover(Long id);
}

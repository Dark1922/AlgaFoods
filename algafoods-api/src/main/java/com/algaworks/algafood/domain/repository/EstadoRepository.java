package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Estado;

public interface EstadoRepository {
	List<Estado> listar();
	Estado buscarId(Long id);
	Estado adiciona(Estado estado);
	void remover(Long id);
}

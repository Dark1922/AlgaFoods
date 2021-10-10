package com.algaworks.algafood.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

	@Override
	public List<Restaurante> listar() {
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@PersistenceContext // injetar um EntityManager
	private EntityManager manager;

	
	
	@Transactional
	@Override
	public Restaurante adiciona(Restaurante restaurante) {
		return manager.merge(restaurante);
	}
	@Override
	public Restaurante buscarId(Long id) {
		return manager.find(Restaurante.class, id);
		//select from cozinha where id = ?
	}

	@Transactional
	@Override
	public void remover(Restaurante restaurante) {
		restaurante = buscarId(restaurante.getId());
		manager.remove(restaurante);
		
	}

	

	
}

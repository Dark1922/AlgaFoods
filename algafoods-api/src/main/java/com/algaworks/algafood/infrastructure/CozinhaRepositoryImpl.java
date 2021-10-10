package com.algaworks.algafood.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {

	@Override
	public List<Cozinha> listar() {
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

	@PersistenceContext // injetar um EntityManager
	private EntityManager manager;

	
	
	@Transactional
	@Override
	public Cozinha adiciona(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
	@Override
	public Cozinha buscarId(Long id) {
		return manager.find(Cozinha.class, id);
		//select from cozinha where id = ?
	}

	@Transactional
	@Override
	public void remover(Cozinha cozinha) {
		cozinha = buscarId(cozinha.getId());
		manager.remove(cozinha);
		
	}

	

	
}

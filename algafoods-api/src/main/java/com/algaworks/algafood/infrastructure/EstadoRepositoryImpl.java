package com.algaworks.algafood.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

	@PersistenceContext // injetar um EntityManager
	private EntityManager manager;
	
	@Override
	public List<Estado> listar() {
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}
	
	@Transactional
	@Override
	public Estado adiciona(Estado estado) {
		return manager.merge(estado);
	}
	@Override
	public Estado buscarId(Long id) {
		return manager.find(Estado.class, id);
		//select from cozinha where id = ?
	}

	@Transactional
	@Override
	public void remover(Long id) {
		Estado estado = buscarId(id);
		
		if(estado == null) {
			throw new EmptyResultDataAccessException(1); //esperava que tivesse ao menos 1 cozinha
		}
		manager.remove(estado);
		
	}
		
}

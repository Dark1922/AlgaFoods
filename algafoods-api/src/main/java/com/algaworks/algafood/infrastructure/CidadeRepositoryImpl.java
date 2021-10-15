package com.algaworks.algafood.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Repository
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext // injetar um EntityManager
	private EntityManager manager;
	
	@Override
	public List<Cidade> listar() {
		return manager.createQuery("from Cidade", Cidade.class).getResultList();
	}
	
	@Transactional
	@Override
	public Cidade adiciona(Cidade cidade) {
		return manager.merge(cidade);
	}
	@Override
	public Cidade buscarId(Long id) {
		return manager.find(Cidade.class, id);
		//select from cozinha where id = ?
	}

	@Transactional
	@Override
	public void remover(Long id) {
		Cidade cidade = buscarId(id);
		
		if(cidade == null) {
			throw new EmptyResultDataAccessException(1); //esperava que tivesse ao menos 1 cozinha
		}
		manager.remove(cidade);
		
	}
		
}

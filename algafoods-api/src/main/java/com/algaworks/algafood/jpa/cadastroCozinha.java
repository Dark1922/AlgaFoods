package com.algaworks.algafood.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class cadastroCozinha {

	@PersistenceContext // injetar um EntityManager
	private EntityManager manager;

	public List<Cozinha> listar() {
		return  manager.createQuery("from Cozinha", Cozinha.class).getResultList();
		
	}
	
	@Transactional
	public Cozinha adiciona(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
	
	public Cozinha buscarId(Long id) {
		return manager.find(Cozinha.class, id);
		//select from cozinha where id = ?
	}
}

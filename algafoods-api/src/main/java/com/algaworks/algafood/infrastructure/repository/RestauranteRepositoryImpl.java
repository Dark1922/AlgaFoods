package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQuery;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy //só instancia ela no momento que for preciso preguiçoso
	private RestauranteRepository restauranteRepository;
	
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial , 
			BigDecimal taxaFreteFinal ) {
		
		var builder = manager.getCriteriaBuilder(); //fábrica de de elementos pra consulta
		
		var criteria = builder.createQuery(Restaurante.class); //construtor de clausulas
		
		var root = criteria.from(Restaurante.class); //From restaurante
		
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(nome)) {
		predicates.add(builder.like(root.get("nome"), "%" +  nome + "%")); //consulta por partes de nome
		}
		
		if(taxaFreteInicial != null) {
		predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}
		
		if(taxaFreteFinal != null) {
		predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0])); //instancia de array preenchido com todos predicates passados
		
		var query = manager.createQuery(criteria);
		return query.getResultList();
		
	}

	@Override
	public List<Restaurante> findComFreteGratos(String nome) {
		return restauranteRepository.findComFreteGratos(nome);
	}
}

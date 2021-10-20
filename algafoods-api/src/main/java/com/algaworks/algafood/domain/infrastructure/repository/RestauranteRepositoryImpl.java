package com.algaworks.algafood.domain.infrastructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQuery;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial , 
			BigDecimal taxaFreteFinal ) {
		
		var jpql = new StringBuilder();
		jpql.append("from Restaurante where 0 = 0 ");
		
		var parametros = new HashMap<String, Object>(); //mapa de string e objeto
		
		if(StringUtils.hasLength(nome)) { //verifica se ele n está nulo nem vázio
			jpql.append("and nome like: nome ");
			parametros.put("nome","%" + nome + "%");
		}
		if(taxaFreteInicial != null) {
			jpql.append("and  taxaFrete  >= :taxaInicial ");
			parametros.put("taxaInicial", taxaFreteInicial);
		}
		if(taxaFreteFinal != null) {
			jpql.append("and  taxaFrete  <= :taxaFinal ");
			parametros.put("taxaFinal", taxaFreteFinal);
		}
		
		
		
		TypedQuery<Restaurante> query = manager
				.createQuery(jpql.toString(), Restaurante.class);
		
		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
		
		return query.getResultList();
	}
}

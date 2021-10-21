package com.algaworks.algafood.domain.infrastructure.repository.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Restaurante;

import lombok.AllArgsConstructor;

@AllArgsConstructor //cria construtor e recebendo a instacia do nome
public class RestauranteComNomeSemelhanteSpec implements Specification<Restaurante> {
	private static final long serialVersionUID = 1L;

	private String nome;
	
	@Override
	public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		//Um filto de predicate aonde a taxaFrete é zero
		return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
	}

}

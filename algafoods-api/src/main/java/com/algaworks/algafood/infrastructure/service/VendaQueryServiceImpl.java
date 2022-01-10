package com.algaworks.algafood.infrastructure.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository //pode traduzir algumas exceptions de persistencia do spring
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext //vai persistir esse contexto
	private EntityManager entityManager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiaria(VendaDiariaFilter filtro) {

		var  builder = entityManager.getCriteriaBuilder(); //tipo de cria crateria builder predicates etc
		var query = builder.createQuery(VendaDiaria.class); //craterai builder do vendadiaria os dados
		var	 root = query.from(Pedido.class); //from onde na nossa entidade pedido
	
	   var functionDateDataCriacao = builder.function("date", Date.class,
			   root.get("dataCriacao")); //tipo date , dado esperado ,quais argumento
		
		
		//construa venda diaria atráves da seleçao passada
		var selection = builder.construct(VendaDiaria.class,  
				functionDateDataCriacao,
				builder.count(root.get("id")), //root é o produto pegando pelo get seu id
				builder.sum(root.get("valorTotal")));
		
		query.select(selection); //qual seleção eu quero, select oq eu estou querendo especificar que nem no sql
		query.groupBy(functionDateDataCriacao);
		
		return entityManager.createQuery(query).getResultList();
	}

	}

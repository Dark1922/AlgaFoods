package com.algaworks.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository //pode traduzir algumas exceptions de persistencia do spring
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext //vai persistir esse contexto
	private EntityManager entityManager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiaria(VendaDiariaFilter filtro,String timeOfset) {

		var  builder = entityManager.getCriteriaBuilder(); //tipo de cria crateria builder predicates etc
		var query = builder.createQuery(VendaDiaria.class); //craterai builder do vendadiaria os dados
		var	 root = query.from(Pedido.class); //from onde na nossa entidade pedido
		
		var functionConvertTzDataCriacao = builder.function(
				"convert_tz",Date.class, root.get("dataCriacao"),
			     builder.literal("+00:00"), builder.literal(timeOfset));
	
	   var functionDateDataCriacao = builder.function("date", Date.class,
			   functionConvertTzDataCriacao); //tipo date , dado esperado ,quais argumento
		
			
		//construa venda diaria atráves da seleçao passada
		var selection = builder.construct(VendaDiaria.class,  
				functionDateDataCriacao,
				builder.count(root.get("id")), //root é o produto pegando pelo get seu id
				builder.sum(root.get("valorTotal")));
		
		var predicates = new ArrayList<Predicate>();
		
		if (filtro.getRestauranteId() != null) { //filtrando o id do restaurante
		    predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}
		    
		if (filtro.getDataCriacaoInicio() != null) {//filtrando data de criação
		    predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), 
		            filtro.getDataCriacaoInicio()));
		}

		if (filtro.getDataCriacaoFim() != null) {//filtrando data de criacao fim
		    predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), 
		            filtro.getDataCriacaoFim()));
		}
		    
		predicates.add(root.get("status").in(
		        StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE)); //vai mostrar só status com pedido confirmado e entregue cancelado n
		
		query.select(selection); //qual seleção eu quero, select oq eu estou querendo especificar que nem no sql
		query.groupBy(functionDateDataCriacao);
		query.where(predicates.toArray(new Predicate[0]));
		
		return entityManager.createQuery(query).getResultList();
	}

	}

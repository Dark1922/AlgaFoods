package com.algaworks.algafood.infrastructure.repository.spec;

 
import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;

public class PedidoSpecs {

	
	 public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) { 
		 return (root, query, builder) -> {
			 var predicates = new ArrayList<Predicate>();
			 
			 //adicionar predicates  no ArrayList
			 if(filtro.getClienteId() != null) {
				 predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
			 }
			 if(filtro.getRestauranteId() != null) {
				 predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
			 }
			 if(filtro.getDataCriacaoInicio() != null) { //maior que a data inicial de criação tem que ser a maior
				 predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
			 }//datacriação ou datafim vem do proprio  pedido do nome das entidade e dps vem os get do pedidoFilter
			 if(filtro.getDataCriacaoFim() != null) {//menor que a data fim tem que ser menor q  a criação
				 predicates.add(builder.lessThanOrEqualTo(root.get("dataFim"), filtro.getDataCriacaoFim()));
			 }
			 
			 
				 
			 return builder.and(predicates.toArray(new Predicate[0]));//instancia de array preenchido com todos predicates passados
		 };
	 }
	  

}

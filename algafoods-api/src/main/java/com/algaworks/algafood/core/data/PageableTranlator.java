package com.algaworks.algafood.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranlator { //tradutor do tipo pageable

	public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {
		//retorna uma lista de sort . orders, são as ordens já corretas que pode usar na nossa camada de persistencia		
       var orders = pageable.getSort().stream()
    		   .filter(order -> fieldsMapping.containsKey(order.getProperty())) //se a properiedade n existe agente ignora ela
    		   .map(order -> new Sort.Order(order.getDirection(), fieldsMapping.get(order.getProperty())))
        .collect(Collectors.toList()); 
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
	}
}

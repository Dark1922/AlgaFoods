package com.algaworks.algafood.core.jackson;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public class PageWrapper<T> extends PageImpl<T>{
	private static final long serialVersionUID = 1L;


	private Pageable pageable;
	
	public PageWrapper(Page<T> page, Pageable pageable ) {
		super(page.getContent(), pageable, page.getTotalElements());
		this.pageable = pageable;
	}
	
    public Pageable getPageable() {
    	return this.pageable;
    }

//ele recebe um Pageable que é o pageable que vai ser retornado no getPageable ele é improtando para fazer as consulta parar
    //trazer os dados de ordenação corretamente com a pesquisa dos link  certa
}

package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada."),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso,");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) { //path é o caminho que vai concatenar com a uri
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}
}

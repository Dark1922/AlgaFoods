package com.algaworks.algafood.domain.model;

public enum StatusPedido {

	CRIADO("Criado"),
	CONFIRMADO("Confirmado"),
	CANCELADO("Cancelado"),
	ENTREGUE("Entregue");
	
private String descricao;
	
	StatusPedido(String descricao) {
		this.descricao = descricao;
	} 
	public String getDescricao() {
		return this.descricao;
	}
}
	

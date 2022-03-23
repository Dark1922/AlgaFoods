package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

	CRIADO("Criado"),
	CONFIRMADO("Confirmado", CRIADO),
	CANCELADO("Cancelado", CRIADO),
	ENTREGUE("Entregue", CONFIRMADO);

	private String descricao;

	private List<StatusPedido> statusAnteriores; // status anteriores para chegar no próximo

	StatusPedido(String descricao, StatusPedido... statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}

	public String getDescricao() {
		return this.descricao;
	}
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
		//se o novo status que eu tiver passando n tiver na minha lista ele retorna true q n pode
	}

	public boolean PodeAlterarPara(StatusPedido novoStatus) {
		return !naoPodeAlterarPara(novoStatus);
		//return novoStatus.statusAnteriores.contains(this);	//que pode alterar com seu status igual
	
	}
}

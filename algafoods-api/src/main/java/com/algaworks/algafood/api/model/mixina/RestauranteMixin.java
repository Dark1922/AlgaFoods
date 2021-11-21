package com.algaworks.algafood.api.model.mixina;

import java.time.LocalDateTime;
import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class RestauranteMixin {


	@JsonIgnoreProperties(value = "nome", allowGetters = true ) //qnd for serializar a cozinha ignore o nome de cozinha
	private Cozinha cozinha;
	
	@JsonIgnore
	private List<FormaPagamento> formasPagamento;
	
	@JsonIgnore
	private Endereco endereco;
	
	@JsonIgnore
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	private List<Produto> produtos; 
}
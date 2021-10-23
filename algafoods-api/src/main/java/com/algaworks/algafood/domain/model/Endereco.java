package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Embeddable //classe encorporada , pode ser encorporada em uma entidade parte de uma outra entidade
public class Endereco {

	@Column(name = "endereco_cep")
	private String cep;
	
	@Column(name = "endereco_logradouro")
	private String logradouro;
	
	@Column(name = "endereco_numero")
	private String numero;
	
	@Column(name = "endereco_complementp")
	private String complemento;
	
	@Column(name = "endereco_bairro")
	private String bairro;

	@ManyToOne
	@JoinColumn(name = "endereco_cidade_id")
	private Cidade cidade;
}

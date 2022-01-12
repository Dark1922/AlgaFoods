package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FotoProduto {
	
	@Id
	@EqualsAndHashCode.Include
	@Column(name = "produto_id") //nosso @id é produto_id
	private Long id;
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
	@OneToOne(fetch = FetchType.LAZY)//se buscar uma fotoProduto n vai precisar de um produto necessáriamente evitar select desnecessarios
	@MapsId //a entidade é mapeada atráves do id do produto, qnd fazer um get produto jpa vai fazer uma consulta usando o produto_id
	//fizemos isso pq a propriedade já está declaram o nome do produto_id no column
	private Produto produto;
	
	public Long getRestauranteId() {
		if(getProduto() != null) {
			return getProduto().getRestaurante().getId();
		}
		return null;
	}
	
	
}

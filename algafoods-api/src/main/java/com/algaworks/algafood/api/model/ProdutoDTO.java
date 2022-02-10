package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoDTO extends RepresentationModel<ProdutoDTO>{
	
	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Pizza de Frango", required = true)
    private String nome;
    
	@ApiModelProperty(example = "Queijo , catupiry, recheio nas borda", required = true)
    private String descricao;
    
	@ApiModelProperty(example = "19.99", required = true)
    private BigDecimal preco;
    
	@ApiModelProperty(example = "true", required = true)
    private Boolean ativo;
}

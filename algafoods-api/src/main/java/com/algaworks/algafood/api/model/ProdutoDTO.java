package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {
	
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

package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	/*passando os dados de entrada pros RestauranteInput os dados que vamos entrar pra fazer requisição na api*/
	 public Restaurante toDomainObject(RestauranteInput restauranteInput) {
	        Restaurante restaurante = new Restaurante();
	        restaurante.setNome(restauranteInput.getNome());
	        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
	        
	        Cozinha cozinha = new Cozinha();
	        cozinha.setId(restauranteInput.getCozinha().getId());
	        
	        restaurante.setCozinha(cozinha);
	        
	        return restaurante;
	    }

}

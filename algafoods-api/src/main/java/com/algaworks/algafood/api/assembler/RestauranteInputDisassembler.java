package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	/*
	 * passando os dados de entrada pros RestauranteInput os dados que vamos entrar
	 * pra fazer requisição na api 
	 */ 
      	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
 
            return modelMapper.map(restauranteInput, Restaurante.class);	
		}
      	
      	//vai passar o input e o restaurante onde agente quer atribuir
      	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
      		restaurante.setCozinha(new Cozinha()); //pra evitar erro de mudança do id da cozinha
      		
      		if(restaurante.getEndereco() != null) {
      			restaurante.getEndereco().setCidade(new Cidade());
      		}
      		
      		modelMapper.map(restauranteInput, restaurante);
      	}

}

package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInput;
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

}

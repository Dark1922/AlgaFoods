package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {
	
	//instancia do model mapper dentro do nosso contexto do spring pra funcionar a injeção dela
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class) //classe que queremos transcrever uma para outra
		.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete); //adicionar um mapeamento
		
		return modelMapper;
	}

}

package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.EnderecoDTO;
import com.algaworks.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {
	
	//instancia do model mapper dentro do nosso contexto do spring pra funcionar a injeção dela
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class) //classe que queremos transcrever uma para outra
//		.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete); //adicionar um mapeamento
		
		//mapeamento de tipo do endreço pro type model/dto
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		//origem e destino aonde quer atribuir o tipo dele é string, destino e valor = src , src pega a origem do nome do estado
		enderecoToEnderecoModelTypeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoDtoDest, value) -> enderecoDtoDest.getCidade().setEstado(value));
		return modelMapper;
	}

}

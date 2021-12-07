package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	//representa os dados de representação dto
	public RestauranteDTO toModel(Restaurante restaurante) {
		//oq eu estou recebendo de origem entrada restaurante e qual o destino dele que vai ser representado e passado seus dados
		return modelMapper.map(restaurante, RestauranteDTO.class); 
	}
	
	//para o get busca todos dados daquela classe com os atributos do model ou dto representation model
	public List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream() //cada restaurante vai converter em restaurante DTO Ou Model
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
}

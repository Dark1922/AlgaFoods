package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {

	//representa os dados de representação dto
	public RestauranteDTO toModel(Restaurante restaurante) {
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		RestauranteDTO restauranteDTO = new RestauranteDTO();

		restauranteDTO.setId(restaurante.getId());//atribui a propriedade model o id do restaurante
		restauranteDTO.setNome(restaurante.getNome());
	    restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
	    restauranteDTO.setCozinha(cozinhaDTO);
		return restauranteDTO; 
	}
	
	//para o get busca todos dados daquela classe com os atributos do model ou dto representation model
	public List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream() //cada restaurante vai converter em restaurante DTO Ou Model
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
}

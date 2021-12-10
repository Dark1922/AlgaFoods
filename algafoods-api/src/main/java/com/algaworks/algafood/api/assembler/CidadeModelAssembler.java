package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler {
	
	 @Autowired
	    private ModelMapper modelMapper;
	
	//representa os dados de representação dto
		public CidadeDTO toModel(Cidade cidade)  {
			//oq eu estou recebendo de origem entrada cidade e qual o destino dele que vai ser representado e passado seus dados
			return modelMapper.map(cidade, CidadeDTO.class); 
		}
	
	//para o get busca todos dados daquela classe com os atributos do model ou dto representation model
		public List<CidadeDTO> toCollectionModel(List<Cidade> cidades) {
			return cidades.stream() //cada cidade vai converter em restaurante DTO Ou Model
					.map(cidade -> toModel(cidade))
					.collect(Collectors.toList());
		}
}

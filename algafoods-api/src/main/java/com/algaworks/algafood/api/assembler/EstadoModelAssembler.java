package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler {

	  @Autowired
	    private ModelMapper modelMapper;
	    
	    public EstadoDTO toModel(Estado estado) {
	        return modelMapper.map(estado, EstadoDTO.class);
	    }
	    
	    public List<EstadoDTO> toCollectionModel(List<Estado> estados) {
	        return estados.stream()
	                .map(estado -> toModel(estado))
	                .collect(Collectors.toList());
	    }
}

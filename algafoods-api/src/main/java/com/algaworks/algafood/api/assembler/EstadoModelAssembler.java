package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

	  @Autowired
	    private ModelMapper modelMapper;
	    
	  public EstadoModelAssembler() {
	        super(EstadoController.class, EstadoDTO.class);
	    }
	    
	    @Override
	    public EstadoDTO toModel(Estado estado) {
	    	EstadoDTO estadoModel = createModelWithId(estado.getId(), estado);
	        modelMapper.map(estado, estadoModel);
	        
	        estadoModel.add(linkTo(EstadoController.class).withRel("estados"));
	        
	        return estadoModel;
	    }
	    
	    @Override
	    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
	        return super.toCollectionModel(entities)
	            .add(linkTo(EstadoController.class).withSelfRel());
	    }   
}

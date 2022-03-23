package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.core.linkhateos.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

	  @Autowired
	  private ModelMapper modelMapper;
	  
	  @Autowired
	  private AlgaLinks algaLinks;
	  
	  @Autowired
	  private AlgaSecurity algaSecurity;
	    
	  public EstadoModelAssembler() {
	        super(EstadoController.class, EstadoDTO.class);
	    }
	    
	    @Override
	    public EstadoDTO toModel(Estado estado) {
	    	EstadoDTO estadoModel = createModelWithId(estado.getId(), estado);
	        modelMapper.map(estado, estadoModel);
	        
	       if (algaSecurity.podeConsultarEstados()) {
	        estadoModel.add(algaLinks.linkToEstados("estados"));
	       }
	       
	        return estadoModel;
	    }
	    
	    @Override
	    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
	    	 CollectionModel<EstadoDTO> collectionModel = super.toCollectionModel(entities);
	    	    
	    	    if (algaSecurity.podeConsultarEstados()) {
	    	        collectionModel.add(algaLinks.linkToEstados());
	    	    }
	    	    
	    	    return collectionModel;
	    }   
}

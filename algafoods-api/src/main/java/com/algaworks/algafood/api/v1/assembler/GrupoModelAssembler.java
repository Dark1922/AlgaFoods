package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.model.GrupoDTO;
import com.algaworks.algafood.core.linkhateos.AlgaLinks;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO>{

	 @Autowired
	    private ModelMapper modelMapper;
	 
	    @Autowired
	    private AlgaLinks algaLinks;
	    
	    public GrupoModelAssembler() {
	        super(GrupoController.class, GrupoDTO.class);
	    }
	    
	    @Override
	    public GrupoDTO toModel(Grupo grupo) {
	    	GrupoDTO grupoModel = createModelWithId(grupo.getId(), grupo);
	        modelMapper.map(grupo, grupoModel);
	        
	        grupoModel.add(algaLinks.linkToGrupos("grupos"));
	        
	        grupoModel.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
	        
	        return grupoModel;
	    }
	    
	    @Override
	    public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
	        return super.toCollectionModel(entities)
	                .add(algaLinks.linkToGrupos());
	    }            
	}   


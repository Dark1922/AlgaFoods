package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

    @Autowired
    private ModelMapper modelMapper;
    
    public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaDTO.class);	
	}
    
    @Override //sobrescreve o RepresentationModelAssemblerSupport do super
    public CozinhaDTO toModel(Cozinha cozinha) {
    	//adicionando links
    	CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);//link de cozinha por id
    	modelMapper.map(cozinha, cozinhaDTO);//fazendo uma cópia de cozinha para cozinhaDTO
    	
    	cozinhaDTO.add(linkTo(CozinhaController.class).withRel("cozinhas"));//criando link de busca de todas cozinha
    	
    	return cozinhaDTO;
        
    }
    
   //tocollectionModel já vem implementado do   RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO>  
}
package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.model.CozinhaDTO;
import com.algaworks.algafood.core.linkhateos.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private AlgaLinks algaLinks;
    
    @Autowired
    private AlgaSecurity algaSecurity; 
    
    public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaDTO.class);	
	}
    
    @Override //sobrescreve o RepresentationModelAssemblerSupport do super
    public CozinhaDTO toModel(Cozinha cozinha) {
    	
    	//adicionando links
    	CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);//link de cozinha por id
    	
    	modelMapper.map(cozinha, cozinhaDTO);//fazendo uma cópia de cozinha para cozinhaDTO
    	
    if (algaSecurity.podeConsultarCozinhas()) {
    	cozinhaDTO.add(algaLinks.linkToCozinhas("cozinhas"));//criando link de busca de todas cozinha
    }
    	return cozinhaDTO;
        
    }
    
   //tocollectionModel já vem implementado do   RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO>  
}
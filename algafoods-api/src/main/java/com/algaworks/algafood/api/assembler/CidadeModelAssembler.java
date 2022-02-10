package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.core.linkhateos.AlgaLinks;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO>{

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    @Autowired
	    private AlgaLinks algaLinks;
	
	    @Override //método que sobescreve da classe pai
		public CidadeDTO toModel(Cidade cidade)  {
	    	
	    	CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);
	    	modelMapper.map(cidade, cidadeDTO); 
	    	
	    	cidadeDTO.add(algaLinks.linkToCidades("cidades"));
	    	    
	    	cidadeDTO.getEstado().add(algaLinks.linkToEstado(cidadeDTO.getEstado().getId()));
	    	
	    	return cidadeDTO; 
		}

	    @Override
	    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
	    	return super.toCollectionModel(entities) //na resposta adiciona o link no final da página que ela está representando
	    			.add(algaLinks.linkToCidades());
	    }
		
		 public CidadeModelAssembler() {
				super(CidadeController.class, CidadeDTO.class);
			}
}

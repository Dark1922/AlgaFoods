package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO>{

	    @Autowired
	    private ModelMapper modelMapper;
	
	    @Override //método que sobescreve da classe pai
		public CidadeDTO toModel(Cidade cidade)  {
	    	
	    	CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);
	    	modelMapper.map(cidade, cidadeDTO); 
	    	
	    	
	    	//CidadeDTO cidadeDTO = modelMapper.map(cidade, CidadeDTO.class);
	    	//cidadeDTO.add(linkTo(methodOn(CidadeController.class)
				//	.buscar(cidadeDTO.getId())).withSelfRel());
			
			cidadeDTO.add(linkTo(methodOn(CidadeController.class)
					.listar()).withRel("cidades"));
			
			cidadeDTO.getEstado().add(linkTo(methodOn(EstadoController.class)
					.buscar(cidadeDTO.getEstado().getId())).withSelfRel());
	    	
	    	return cidadeDTO; 
		}

	    @Override
	    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
	    	return super.toCollectionModel(entities) //na resposta adiciona o link no final da página que ela está representando
	    			.add(linkTo(CidadeController.class).withSelfRel());
	    }
		
		 public CidadeModelAssembler(Class<?> controllerClass, Class<CidadeDTO> resourceType) {
				super(CidadeController.class, CidadeDTO.class);
			}
}

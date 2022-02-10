package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.core.linkhateos.AlgaLinks;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler  extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO>{
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
    private AlgaLinks algaLinks;
    
    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoDTO.class);
    }

    @Override
    public ProdutoDTO toModel(Produto produto) {
    	ProdutoDTO produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        
        modelMapper.map(produto, produtoModel);
        
        produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        
        produtoModel.add(algaLinks.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));
              
        
        return produtoModel;
    } 

	
}

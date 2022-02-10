package com.algaworks.algafood.core.linkhateos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.TemplateVariable.VariableType;

import com.algaworks.algafood.api.controller.PedidoController;

@Component
public class AlgaLinks {
	
   //cria constantes para podermos aproveitar o código em outras classe que precisar informar paginação
	public static final TemplateVariables PAGINACAO_VARIABLES  = new TemplateVariables(
	        		new TemplateVariable("page",VariableType.REQUEST_PARAM),
	        		new TemplateVariable("size",VariableType.REQUEST_PARAM),
	        		new TemplateVariable("sort",VariableType.REQUEST_PARAM)
	        		);
	        
	
	  public Link linkToPedidos() {
		  
		   //cria os templates que queremos informar no link
	        TemplateVariables filtroVariables = new TemplateVariables(
	        		new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
	        		new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
	        		new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
	        		new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
	      
	        //cria a url dinâmica do nosso controoler uri pr toString
	        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
	        	
	        //cocatena a váriavel estátiva PAGINACAO_VARIABLES com o filtrovaraibles do filtro do pedidos
	        return  Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), "pedidos");
	        //pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos"));
	}
}

package com.algaworks.algafood.core.linkhateos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;

@Component
public class AlgaLinks {
	
	/*                                       Links de resposta da páginação                                                   */
	
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
	  
	  /*                                       métodos para criação dos links necessários. ToModel                               */    
	  
	  /*Restaurantes*/
	  public Link linkToRestaurante(Long restauranteId, String rel) {
		    return linkTo(methodOn(RestauranteController.class)
		            .buscar(restauranteId)).withRel(rel);
		}

		public Link linkToRestaurante(Long restauranteId) {
		    return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
		}
		 /*Usuario*/
		public Link linkToUsuario(Long usuarioId, String rel) {
		    return linkTo(methodOn(UsuarioController.class)
		            .buscar(usuarioId)).withRel(rel);
		}

		public Link linkToUsuario(Long usuarioId) {
		    return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
		}

		public Link linkToUsuarios(String rel) {
		    return linkTo(UsuarioController.class).withRel(rel);
		}
		 /*Grupos*/
		public Link linkToUsuarios() {
		    return linkToUsuarios(IanaLinkRelations.SELF.value());
		}

		public Link linkToGruposUsuario(Long usuarioId, String rel) {
		    return linkTo(methodOn(UsuarioGrupoController.class)
		            .listar(usuarioId)).withRel(rel);
		}

		public Link linkToGruposUsuario(Long usuarioId) {
		    return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
		}
		 /*responsavel Restaurante*/
		public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
		    return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
		            .listar(restauranteId)).withRel(rel);
		}

		public Link linkToResponsaveisRestaurante(Long restauranteId) {
		    return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
		}
		 /*Forma Pagamento*/
		public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
		    return linkTo(methodOn(FormaPagamentoController.class)
		            .buscar(formaPagamentoId, null)).withRel(rel);
		}

		public Link linkToFormaPagamento(Long formaPagamentoId) {
		    return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
		}
		 /*Cidades*/
		public Link linkToCidade(Long cidadeId, String rel) {
		    return linkTo(methodOn(CidadeController.class)
		            .buscar(cidadeId)).withRel(rel);
		}

		public Link linkToCidade(Long cidadeId) {
		    return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
		}

		public Link linkToCidades(String rel) {
		    return linkTo(CidadeController.class).withRel(rel);
		}

		public Link linkToCidades() {
		    return linkToCidades(IanaLinkRelations.SELF.value());
		}
         /*Estados*/
		public Link linkToEstado(Long estadoId, String rel) {
		    return linkTo(methodOn(EstadoController.class)
		            .buscar(estadoId)).withRel(rel);
		}

		public Link linkToEstado(Long estadoId) {
		    return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
		}
       
		public Link linkToEstados(String rel) {
		    return linkTo(EstadoController.class).withRel(rel);
		}

		public Link linkToEstados() {
		    return linkToEstados(IanaLinkRelations.SELF.value());
		}
         /*Produtos*/
		public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
		    return linkTo(methodOn(RestauranteProdutoController.class)
		            .buscar(restauranteId, produtoId))
		            .withRel(rel);
		}

		public Link linkToProduto(Long restauranteId, Long produtoId) {
		    return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
		}
		 /*Cozinhas*/
		public Link linkToCozinhas(String rel) {
		    return linkTo(CozinhaController.class).withRel(rel);
		}

		public Link linkToCozinhas() {
		    return linkToCozinhas(IanaLinkRelations.SELF.value());
		}
}

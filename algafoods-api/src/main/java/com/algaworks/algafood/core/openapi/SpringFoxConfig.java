package com.algaworks.algafood.core.openapi;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.CozinhaDTO;
import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.model.GrupoDTO;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.PermissaoDTO;
import com.algaworks.algafood.api.v1.model.ProdutoDTO;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.api.v1.openapi.model.CidadesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.EstadosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.GruposModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PermissoesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.ProdutosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.RestaurantesBasicoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.UsuariosModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig  implements WebMvcConfigurer  {

	@Bean //um método que vai produzir uma instancia de docket e vai registrar como um component spring
	public Docket apiDocket() {//docket = sumario pra pegar o conjunto de serviços que vai ser documentado, e ir configurando
		
		var typeResolver = new TypeResolver();
		
		return  new Docket(DocumentationType.SWAGGER_2) 
				.groupName("V1")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")) //tudo que tiver no projeto pode colocar , os endpoint que quer documentar
				.paths(PathSelectors.any()) //já fica por padrão
				.build()//.paths(PathSelectors.ant("/restaurantes/*"))
				.useDefaultResponseMessages(false)//deixa como false as mensagem de erro que o swagger preve pra nós
				
				.globalResponses(HttpMethod.GET, globalGetResponseMessages()) //descrever os métodos globais de erro do get
				.globalResponses(HttpMethod.POST, globalPostResponseMessages()) 
				.globalResponses(HttpMethod.PUT, globalPutResponseMessages()) 
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages()) 
				.globalResponses(HttpMethod.PATCH, globalPostResponseMessages()) 
				
				.apiInfo(apiInfo())
				
				.ignoredParameterTypes(ServletWebRequest.class,URL.class, URI.class,
				 URLStreamHandler.class, Resource.class, File.class, InputStream.class) //ignora esse pacote pra forma-pagamentos ficar limpo
				
				.additionalModels(typeResolver.resolve(com.algaworks.algafood.api.exceptionhandler.Problem.class))
				
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, CozinhaDTO.class),
					CozinhasModelOpenApi.class)) //formata paginação da cozinhan a resposta
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
					    PedidosResumoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, CidadeDTO.class),
						CidadesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, EstadoDTO.class),
						EstadosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, FormaPagamentoDTO.class),FormasPagamentoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, GrupoDTO.class),GruposModelOpenApi.class))
					.alternateTypeRules(AlternateTypeRules.newRule(
					        typeResolver.resolve(CollectionModel.class, PermissaoDTO.class),PermissoesModelOpenApi.class))
					.alternateTypeRules(AlternateTypeRules.newRule(
					        typeResolver.resolve(CollectionModel.class, ProdutoDTO.class),ProdutosModelOpenApi.class))
					.alternateTypeRules(AlternateTypeRules.newRule(
					  typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class), RestaurantesBasicoModelOpenApi.class))
						.alternateTypeRules(AlternateTypeRules.newRule(
						        typeResolver.resolve(CollectionModel.class, UsuarioDTO.class),  UsuariosModelOpenApi.class))
					    
				.tags(new Tag("Cidades","Gerencia as Cidades"),
						new Tag("Grupos", "Gerencia os grupos de Usuários"),
						  new Tag("Cozinhas", "Gerencia as Cozinhas"),
						  new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
						  new Tag("Pedidos", "Gerencia os Produto"),
						  new Tag("Restaurantes", "Gerencia os Restaurantes"),
						  new Tag("Estados", "Gerencia os Estados"),
						  new Tag("Produtos", "Gerencia os Produtos de Restaurante"),
						  new Tag("Usuários", "Gerencia os Usuários"),
						  new Tag("Estatísticas", "Estatísticas da AlgaFood"),
						  new Tag("Permissões", "Gerencia as permissões"),
						  new Tag("Grupos Permissões", "Gerencia permissões dos grupos"));
	   
	}
	
	private List<Response> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
						.code(comoString(HttpStatus.INTERNAL_SERVER_ERROR))
						.description("Erro interno do Servidor")
//						.representation( MediaType.APPLICATION_JSON )
//						.apply(problemBuilder())
						.build(),
				new ResponseBuilder()
						.code(comoString(HttpStatus.NOT_ACCEPTABLE))
						.description("Recurso não possui representação que pode ser aceita pelo consumidor")
						.build()
		);
	}
	
	private List<Response> globalPostResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
				.code(comoString(HttpStatus.BAD_REQUEST))
				.description("Requisição inválida (erro do cliente)")
			    .representation( MediaType.APPLICATION_JSON )
				.apply(problemBuilder())
				.build(),
				new ResponseBuilder()
				.code(comoString(HttpStatus.INTERNAL_SERVER_ERROR))
				.description("Erro interno do Servidor")
				//.representation( MediaType.APPLICATION_JSON )
			//	.apply(problemBuilder())
				.build(),
				new ResponseBuilder()
				.code(comoString(HttpStatus.NOT_ACCEPTABLE))
				.description("Recurso não possui representação que pode ser aceita pelo consumidor")
				.build(),
				new ResponseBuilder()
				.code(comoString(HttpStatus.UNSUPPORTED_MEDIA_TYPE))
				.description("Requisição recusada porque o corpo está em um formato não suportado")
				.representation( MediaType.APPLICATION_JSON )
				.apply(problemBuilder())
				.build()
				);
	}
	private List<Response> globalPutResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
				.code(comoString(HttpStatus.BAD_REQUEST))
				.description("Requisição inválida (erro do cliente)")
				.representation( MediaType.APPLICATION_JSON )
				.apply(problemBuilder())
				.build(),
				new ResponseBuilder()
				.code(comoString(HttpStatus.INTERNAL_SERVER_ERROR))
				.description("Erro interno do Servidor")
				//.representation( MediaType.APPLICATION_JSON )
				//.apply(problemBuilder())
				.build(),
				new ResponseBuilder()
				.code(comoString(HttpStatus.NOT_ACCEPTABLE))
				.description("Recurso não possui representação que pode ser aceita pelo consumidor")
				.build(),
				new ResponseBuilder()
				.code(comoString(HttpStatus.UNSUPPORTED_MEDIA_TYPE))
				.description("Requisição recusada porque o corpo está em um formato não suportado")
				.representation( MediaType.APPLICATION_JSON )
				.apply(problemBuilder())
				.build()
				);
	}
	private List<Response> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
				.code(comoString(HttpStatus.BAD_REQUEST))
				.description("Requisição inválida (erro do cliente)")
				.representation( MediaType.APPLICATION_JSON )
				.apply(problemBuilder())
				.build(),
				new ResponseBuilder()
				.code(comoString(HttpStatus.INTERNAL_SERVER_ERROR))
				.description("Erro interno do Servidor")
				//.representation( MediaType.APPLICATION_JSON )
				//.apply(problemBuilder())
				.build()
				);
	}
	
	
	private String comoString(HttpStatus httpStatus) {
		return String.valueOf(httpStatus.value());
	}
	
	private Consumer<RepresentationBuilder> problemBuilder() {
		return r -> r.model(m -> m.name("Problema")
				.referenceModel(
						ref -> ref.key(
								k -> k.qualifiedModelName(
										q -> q.name("Problema")
											  .namespace("com.algaworks.algafood.api.exceptionhandler")
								))));
	}
	
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("index.html")
	    	.addResourceLocations("classpath:/META-INF/resources/");
	    
	    registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Algafood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Algaworks", "https://www.algaworks.com", "contato@algaworks.com"))
				.build();
	}
	/*para fazer com que o SpringFox carregue o módulo de conversão de datas:*/
	@Bean 
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}
}
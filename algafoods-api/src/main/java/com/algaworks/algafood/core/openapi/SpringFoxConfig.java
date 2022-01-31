package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.openapi.model.PageModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PageableModelOpenApi;
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
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")) //tudo que tiver no projeto pode colocar , os endpoint que quer documentar
				.paths(PathSelectors.any()) //já fica por padrão
				//.paths(PathSelectors.ant("/restaurantes/*"))
				.build()
				.useDefaultResponseMessages(false)//deixa como false as mensagem de erro que o swagger preve pra nós
				.globalResponses(HttpMethod.GET, globalGetResponseMessages()) //descrever os métodos globais de erro do get
				.globalResponses(HttpMethod.POST, globalPostResponseMessages()) 
				.globalResponses(HttpMethod.PUT, globalPutResponseMessages()) 
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages()) 
				.apiInfo(apiInfo())
				.ignoredParameterTypes(ServletWebRequest.class) //ignora esse pacote pra forma-pagamentos ficar limpo
				.additionalModels(typeResolver.resolve(com.algaworks.algafood.api.exceptionhandler.Problem.class))
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)//tratar os dados que queremos da paginação no endpoint sw
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, CozinhaDTO.class),
						typeResolver.resolve(PageModelOpenApi.class, CozinhaDTO.class))) //formata paginação da cozinhan a resposta
				.tags(new Tag("Cidades","Gerencia as cidades"),
						new Tag("Grupos", "Gerencia os grupos de usuários"),
						  new Tag("Cozinhas", "Gerencia as cozinhas"));
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
				.representation( MediaType.APPLICATION_JSON )
				.apply(problemBuilder())
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
				.representation( MediaType.APPLICATION_JSON )
				.apply(problemBuilder())
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
				.representation( MediaType.APPLICATION_JSON )
				.apply(problemBuilder())
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
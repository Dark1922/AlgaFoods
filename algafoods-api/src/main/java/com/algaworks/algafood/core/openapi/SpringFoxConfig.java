package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig  implements WebMvcConfigurer  {

	@Bean //um método que vai produzir uma instancia de docket e vai registrar como um component spring
	public Docket apiDocket() {//docket = sumario pra pegar o conjunto de serviços que vai ser documentado, e ir configurando
		return  new Docket(DocumentationType.OAS_30)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")) //tudo que tiver no projeto pode colocar , os endpoint que quer documentar
				.paths(PathSelectors.any()) //já fica por padrão
				//.paths(PathSelectors.ant("/restaurantes/*"))
				.build()
				.useDefaultResponseMessages(false)//deixa como false as mensagem de erro que o swagger preve pra nós
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades","Gerencia as cidades"));
	}
	
	private List<Response> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
						.code(comoString(HttpStatus.INTERNAL_SERVER_ERROR))
						.description("Erro interno do Servidor")
						.build(),
				new ResponseBuilder()
						.code(comoString(HttpStatus.NOT_ACCEPTABLE))
						.description("Recurso não possui representação que pode ser aceita pelo consumidor")
						.build()
		);
	}
	
	private String comoString(HttpStatus httpStatus) {
		return String.valueOf(httpStatus.value());
	}
	@SuppressWarnings("unused")
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

}
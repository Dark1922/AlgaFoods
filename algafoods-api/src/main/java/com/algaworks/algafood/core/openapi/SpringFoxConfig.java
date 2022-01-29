package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig  implements WebMvcConfigurer  {

	@Bean //um método que vai produzir uma instancia de docket e vai registrar como um component spring
	public Docket apiDocket() {//docket = sumario pra pegar o conjunto de serviços que vai ser documentado, e ir configurando
		return  new Docket(DocumentationType.OAS_30)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")) //tudo que tiver no projeto pode colocar , os endpoint que quer documentar
				.paths(PathSelectors.any()) //já fica por padrão
				//.paths(PathSelectors.ant("/restaurantes/*"))
				.build()
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades","Gerencia as cidades"));
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("index.html")
	    	.addResourceLocations("classpath:/META-INF/resources/");
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
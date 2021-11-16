package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//libera porta pra testes de api sem te que subir
@TestPropertySource("/application-test.properties") //usa o properties-teste
class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private Flyway flyway;
	
	@BeforeEach
	public void setUp() { //vao ser reiniciado várias vezes em cada teste com as configuração daqui
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); //se falhar mostra o log
		RestAssured.port = port; //pega as porta aleatoria
		RestAssured.basePath = "/cozinhas"; //nossa caminho de teste
		
		flyway.migrate(); //vai reiniciar o banco em cada teste
	}
	
	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
		//cenário
		Cozinha novaCozinha	= new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		//ação
		novaCozinha = cadastroCozinha.adicionar(novaCozinha);
		
		//validação
		assertThat(novaCozinha).isNotNull(); //validar q n retorna cozinha nula
		assertThat(novaCozinha.getId()).isNotNull(); //tenha um id que não é nulo
		
	}
	
	@Test //podemos passar assim tb
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Cozinha  novaCozinha = new Cozinha();
		novaCozinha.setNome(null); 
		
		ConstraintViolationException erroEsperado =
			      Assertions.assertThrows(ConstraintViolationException.class, () -> {
			         cadastroCozinha.adicionar(novaCozinha);
			      });
			   
			   assertThat(erroEsperado).isNotNull();
	}
	 
	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		
		EntidadeEmUsoException erroEsperado =
			      Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
			    	  cadastroCozinha.Excluir(1L);
			      });
			   
			   assertThat(erroEsperado);
		
	} 

	@Test 
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		
		CozinhaNaoEncontradaException erroEsperado =
			      Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
			    	  cadastroCozinha.Excluir(100L);
			      });
			   
			   assertThat(erroEsperado);
	}
	
	/* Testes de API*/
	
	@LocalServerPort
	private int port; //vai receber a porta que vai ser gerada pra subir o servidor
	
	@Test
	public void deveRetornarStatus200_quandoConsultarCozinhas() {
		
		RestAssured.given() //dado que agente tem um path /cozinhas
		.accept(ContentType.JSON) //quer do tipo json
		   .when() //onde
		.get() //no get oque está fornecendo pro when/ requisição
		    .then() //oque
		.statusCode(HttpStatus.OK.value()); //o status code tem que ser 200 ok
	}
	
	@Test
	public void deveConter4Cozinhas_QunadoConsultarCozinha() {
		
		RestAssured.given() //dado que agente tem um path /cozinhas
		.accept(ContentType.JSON) //quer do tipo json
		.when() //onde
		   .get() //no get oque está fornecendo pro when/ requisição
		.then() //oque
		    .body("", Matchers.hasSize(4)); //tem q ter 4 objetos dentro do array de cozinhas cadastradas
	}
	
	@Test
	public void testRetornarStatus201_QuandoCadastrarCozinha() {
		RestAssured.given()
		.body("{\"nome\": \"Chinesa\"}")
		.contentType(ContentType.JSON)
		.when()
		.post()
		.then()
		.statusCode(HttpStatus.CREATED.value());
	}
}

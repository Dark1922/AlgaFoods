package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice //pode adicionar  exceptionHandler que sera tratada globalmente
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class) //pode passar mais de uma por um objeto e virgula
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = e.getMessage();
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), 
				status, request);
	}
	
	@ExceptionHandler(NegocioException.class) //pode passar mais de uma por um objeto e virgula
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail = e.getMessage();
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), 
				status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class) //pode passar mais de uma por um objeto e virgula
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		String detail = e.getMessage();
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), 
				status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
            
		if(body == null) {
		
		body = Problem.builder()
				.timestamp(LocalDateTime.now())
				.title(status.getReasonPhrase())
				.build();
		
		}else if (body instanceof String) {
			body = Problem.builder()
					.timestamp(LocalDateTime.now())
					.title((String) body)
					.status(status.value()) //codigo do status qnd pega o value
					.build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex); //cauxa raiz do erro na api
		
		if (rootCause instanceof InvalidFormatException) { //se for um formato invalido vai ser tratado aqui
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
			
		} else if (rootCause instanceof PropertyBindingException) {
	        return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request); 
	    }
		
		//se n for vau ser trata por aqui
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está invalido. Verifique erro de sintaxe.";
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), 
				status, request);

	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		String path = joinPath(ex.getPath()); //stream com resultado dos caminho executados
				
		
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compátivel com o tipo %s." , 
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.timestamp(LocalDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	/* método que irá tratar PropertyBindingException, ficando genérico para outros métodos. */
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {

	    // Criei o método joinPath para reaproveitar em todos os métodos que precisam
	    // concatenar os nomes das propriedades (separando por ".")
	    String path = joinPath(ex.getPath());
	    
	    ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
	    String detail = String.format("A propriedade '%s' não existe. "
	            + "Corrija ou remova essa propriedade e tente novamente.", path);

	    Problem problem = createProblemBuilder(status, problemType, detail)
	    		.timestamp(LocalDateTime.now())
	    		.build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}        
	
	/* O método joinPath irá concatenar os nomes das propriedades, separando-as por . */
	private String joinPath(List<Reference> references) {
	    return references.stream()
	        .map(ref -> ref.getFieldName())
	        .collect(Collectors.joining("."));
	}     
}

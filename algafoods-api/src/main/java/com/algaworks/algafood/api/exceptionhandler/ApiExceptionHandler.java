package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;

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
		/*
		Problem problem = Problem.builder().status(status.value())
				.type("https://algafood.com.br/entidade-nao-encontrada")
				.title("Entidae não encontrada")
				.detail(detail)
				.timestamp(LocalDateTime.now())
				.build();
		*/
		return handleExceptionInternal(e, problem, new HttpHeaders(), 
				status, request);
	}
	
	@ExceptionHandler(NegocioException.class) //pode passar mais de uma por um objeto e virgula
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), 
				HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class) //pode passar mais de uma por um objeto e virgula
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
		
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), 
				HttpStatus.CONFLICT, request);
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
}

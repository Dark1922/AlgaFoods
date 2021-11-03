package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

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
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), 
				HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(NegocioException.class) //pode passar mais de uma por um objeto e virgula
	public ResponseEntity<?> tratarNegocioException(NegocioException e, WebRequest request) {
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), 
				HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class) //pode passar mais de uma por um objeto e virgula
	public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
		
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), 
				HttpStatus.CONFLICT, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
            
		if(body == null) {
		
		body = Problema.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(status.getReasonPhrase())
				.build();
		
		}else if (body instanceof String) {
			body = Problema.builder()
					.dataHora(LocalDateTime.now())
					.mensagem((String) body)
					.build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
}

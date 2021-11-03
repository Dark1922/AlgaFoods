package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)// reason = "Entidade não encontrada")
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegocioException(String msg) {
		super(msg); //resultado padrão not found
	}
	
	public NegocioException(String mensagem, Throwable causa) {//Throwable pai de todas exceções que é a causa
		super(mensagem, causa);
	}

}

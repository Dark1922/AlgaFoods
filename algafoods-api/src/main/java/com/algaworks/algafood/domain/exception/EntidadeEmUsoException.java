package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) //está em uso e conflict
public class EntidadeEmUsoException extends NegocioException {//vai herdar o rumtimeexception
	private static final long serialVersionUID = 1L;
	
public EntidadeEmUsoException(String msg) {
	super(msg);//construtor super do rumtimeExeption passando a msg da entidadeemusoexception
  }

}

package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder //linguagem mais fluente
public class Problema {

	@JsonFormat(timezone = "GMT-3",pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime dataHora; // do acontecimento do erro
	
    private String mensagem;
}

package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder //linguagem mais fluente
public class Problema {

	private LocalDateTime dataHora; // do acontecimento do erro
    private String mensagem;
}

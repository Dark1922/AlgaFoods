package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradaException(String msg) {
		super(msg); //resultado padrão not found
	}

	public RestauranteNaoEncontradaException(Long restauranteId) {//constructor dando o this no construtor acima mandando uma msg padrão
		this(String.format("Não existe um cadastro de restaurante com o código %d", restauranteId));
	}
}

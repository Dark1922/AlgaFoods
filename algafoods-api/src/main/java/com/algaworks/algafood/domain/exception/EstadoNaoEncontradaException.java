package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradaException(String msg) {
		super(msg); //resultado padrão not found
	}

	public EstadoNaoEncontradaException(Long estadoId) {//constructor dando o this no construtor acima mandando uma msg padrão
		this(String.format("Não existe um cadastro de estado com o código %d", estadoId));
	}
}

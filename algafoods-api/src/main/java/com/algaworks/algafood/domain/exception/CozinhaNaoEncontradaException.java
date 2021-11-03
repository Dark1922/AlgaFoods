package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String msg) {
		super(msg); //resultado padrão not found
	}

	public CozinhaNaoEncontradaException(Long cozinhaId) {//constructor dando o this no construtor acima mandando uma msg padrão
		this(String.format("Não existe um cadastro de cozinha com o código %d", cozinhaId));
	}
}

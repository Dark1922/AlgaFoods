package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String msg) {
		super(msg); //resultado padrão not found
	}

	public CidadeNaoEncontradaException(Long cidadeId) {//constructor dando o this no construtor acima mandando uma msg padrão
		this(String.format("Não existe um cadastro de cidade com o código %d", cidadeId));
	}
}

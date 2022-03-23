package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	private static final String NAO_EXISTE_CADASTRO_COM_O_CODIGO_INFORMADO = "Não existe um cadastro de forma de pagamento com código %d";

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
        this(String.format(NAO_EXISTE_CADASTRO_COM_O_CODIGO_INFORMADO, formaPagamentoId));
    }
}

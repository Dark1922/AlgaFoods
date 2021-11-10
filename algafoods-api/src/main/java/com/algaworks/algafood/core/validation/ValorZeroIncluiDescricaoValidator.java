package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

//trata qualquer tipo de objeto
public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

	private String valorField;
	private String descricaoField;
	private String descricaoObrigatoria;

	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {

	boolean valido = true;
	                 //retorna a classe do restaurante polimorfismo //valor field é a taxaFrete do tipo restaurante
	try {
		BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valorField)
				.getReadMethod().invoke(objetoValidacao);
		
		String descricao = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descricaoField)
				.getReadMethod().invoke(objetoValidacao);
		
		if(valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) { //se n for nulo e for igual a zero
			//verificando se contem o nome freteGratis
			valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase()); 
		}
		
	} catch (Exception e) {
		throw new ValidationException(e);
	}
	
	return valido;
	}

	@Override
	public void initialize(ValorZeroIncluiDescricao constraint) {
		this.valorField = constraint.valorField();
		this.descricaoField = constraint.descricaoField();
		this.descricaoObrigatoria = constraint.descricaoObrigatoria();
	}

}

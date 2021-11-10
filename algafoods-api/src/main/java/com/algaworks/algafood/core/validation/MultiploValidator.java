package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number>{
	
	private int numeroMultiplo;

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		boolean valido = true;
		if(value != null) {
			var valorDecimal = BigDecimal.valueOf(value.doubleValue());
			var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
			var resto = valorDecimal.remainder(multiploDecimal);// esses dois valor divido e me retorna o resto
			valido = BigDecimal.ZERO.compareTo(resto) == 0; //se retorna 0 é que les sao iguais
		}
		return valido;
	}
	
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.numeroMultiplo = constraintAnnotation.numero();		
	}

}

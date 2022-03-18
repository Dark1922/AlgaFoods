package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.exception.NegocioException;


public class StrongPassword {
	
	public static void validatePassword(String password) {

		int charactersValidated = 0;
		

		if (password.length() < 6 ) {
			charactersValidated++;
			
		} else {
			
			if (password.length() > 20) {
				charactersValidated++;
			}
			if (!password.matches("(.*)[0-9](.*)")) {
				charactersValidated++;
			}
			if (!password.matches("(.*)[a-z](.*)")) {
				charactersValidated++;
			}
			if (!password.matches("(.*)[A-Z](.*)")) {
				charactersValidated++;
			}
			if (!password.matches("(.*)[!@#$%^&*()-+](.*)")) {
				charactersValidated++;
			}
		}

		if (charactersValidated > 0) {
			throw new NegocioException(String.format("Senha precisa de ao menos um caracteres especial !@$..,"
					+ "um número 0-9" + ", uma letra maíuscula A-Z e uma minuscula a-z , e no mínimo 6 digitos "
							+ "e máximo 20 digitos"));
		}
	}
	

}

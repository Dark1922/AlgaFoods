package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.exception.NegocioException;

public class StrongPassword {

	public static void validatePassword(String password) {

		int charactersValidated = 0;

	//digito 0-9 (?=.*\\d) , sem espaço em branco (?=\\S+$), caractere especial [@#$%!&?*], {6,20} digitos q pode ter	
		
		if (!password.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&?*])(?=\\S+$).{6,20})")) {
			charactersValidated++;
		}

		if (charactersValidated > 0) {
			throw new NegocioException(String.format("Senha precisa de ao menos um caracteres especial !@$..,"
					+ "um número 0-9" + ", uma letra maíuscula A-Z e uma minuscula a-z , e no mínimo 6 digitos "
					+ "e máximo 20 digitos"));
		}
	}

}

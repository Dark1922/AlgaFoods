package com.algaworks.algafood.api.model;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NonNull;

@JacksonXmlRootElement(localName = "cozinhas")
@Data
public class CozinhasXmlWrapper {

	@NonNull //properidade é obrigatório e gera construtor  com data
	@JsonProperty("cozinha")
	@JacksonXmlElementWrapper(useWrapping = false) //embrulho de um elemento no xml desabilitar
	private List<Cozinha> cozinhas;
}

package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonRootName("Cozinha")
public class Cozinha {

	@NotNull(groups = Groups.CozinhaId.class)
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@JsonIgnore
	//@JsonProperty("titulo") 
	@NotBlank(message = "Nome é Obrigatório")
	@Column(nullable = false)
	private String nome;
	 
	//orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY para deletar em cascata
	//faz um one to many aonde foi mapeado por cozinha no restaurante o manytoOne se chama cozinha e passa esse nome por mappedBy
	@OneToMany(mappedBy = "cozinha")  //Many é uma coção pq tem muitos ent tem o List
	private List<Restaurante> restaurantes = new ArrayList<>();//instacia null por causa do nullpointerexception
}

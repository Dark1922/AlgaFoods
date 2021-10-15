package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastrocidadeService;	
	
	@GetMapping()
	public List<Cidade> listar() {
		return cidadeRepository.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cidade> buscarPorId(@PathVariable Long id) {
		
		Cidade cidade = cidadeRepository.buscarId(id);
		
		if(cidade != null) {
			return ResponseEntity.ok(cidade);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try {
			
			cidade = cadastrocidadeService.salvar(cidade);
		return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());	
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@RequestBody Cidade cidade, @PathVariable Long id) {
		try {
			
			Cidade cidadeAtual  =  cidadeRepository.buscarId(id);
		
			if (cidadeAtual != null) {
				BeanUtils.copyProperties(cidade, cidadeAtual, "id");
				
				cidadeAtual = cadastrocidadeService.salvar(cidadeAtual);
				return ResponseEntity.ok(cidadeAtual);
			}
			return ResponseEntity.notFound().build();
		
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());	
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cidade> remover(@PathVariable Long id) {
		try {
			
	        cadastrocidadeService.excluir(id);
	        return ResponseEntity.noContent().build();

			} catch(EntidadeNaoEncontradaException e) {
				  return ResponseEntity.notFound().build();
	        
			}catch(EntidadeEmUsoException e) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
	}
	
}

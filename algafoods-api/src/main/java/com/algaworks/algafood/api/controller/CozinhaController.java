package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RequestMapping("/cozinhas")
@RestController // tem o responseBody dentro dela
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@GetMapping()
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}


	@GetMapping("/{id}")
	public Cozinha buscar(@PathVariable Long id) {
		return cadastroCozinhaService.buscarOuFalhar(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinhaService.adicionar(cozinha);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Optional<Cozinha>> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {

		Optional<Cozinha> cozinhaATUAL = cozinhaRepository.findById(id);

		if (cozinhaATUAL.isPresent()) {//optional nunca vem nulo então temos que vê se ela está presente

			// cozinhaATUAL.setNome(cozinha.getNome());
			// copia os valores da cozinha vai pegar o get no set da cozinha
			BeanUtils.copyProperties(cozinha, cozinhaATUAL.get(), "id");
                //.get pq está dentro do optional vai pega as propriedada da cozinha e passar pra atual tem que ter o get pra funcionar
			cadastroCozinhaService.adicionar(cozinhaATUAL.get());
			return ResponseEntity.ok(cozinhaATUAL);
		}

		return ResponseEntity.notFound().build();
	}
/*
	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long id) {
		
		try {
			
        cadastroCozinhaService.Excluir(id);
        return ResponseEntity.noContent().build();

		} catch(EntidadeNaoEncontradaException e) {
			  return ResponseEntity.notFound().build();
        
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
*/
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) //se der certo n vai retornana nada
	public void remover(@PathVariable Long id) {
        cadastroCozinhaService.Excluir(id);
	}
}

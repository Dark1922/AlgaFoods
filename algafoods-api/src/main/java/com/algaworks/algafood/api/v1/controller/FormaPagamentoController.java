package com.algaworks.algafood.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(path = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
	public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

	    @Autowired
	    private FormaPagamentoRepository formaPagamentoRepository;
	    
	    @Autowired
	    private CadastroFormaPagamentoService cadastroFormaPagamento;
	    
	    @Autowired
	    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	    
	    @Autowired
	    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	    
	    @CheckSecurity.FormasPagamento.PodeConsultar
	    @GetMapping
	    public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request) {
	    	
	    	ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());//desabilita o Etag pra usar o Deep Etags
	    	
	    	String eTag = "0"; //se n ouver alguma atualição continua 0 se tiver passa a atualização na condição if
	    	
	    	OffsetDateTime dataUltimaDataAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
	    	if(dataUltimaDataAtualizacao != null) {
	    		eTag = String.valueOf(dataUltimaDataAtualizacao.toEpochSecond());//transforma  a data em segunos e transfere pro eTag
	    	}
	    	 //vai comparar o if-none-match com essa eTag e vai retorna true ou false
	    	if (request.checkNotModified(eTag)) {
				return null; //se não mudou nada retorna null
			}
	    	
	        List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
	        
	        CollectionModel<FormaPagamentoDTO> formasPagamentoDTO = formaPagamentoModelAssembler.toCollectionModel(todasFormasPagamentos);
	        
	        return ResponseEntity.ok()//qnd tempo quer q o cache fique armazenando os dados
	        		.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic()) 
	        		.eTag(eTag) //adiciona no cabeçalho 
	        		.body(formasPagamentoDTO);
	    }
	    
	    @CheckSecurity.FormasPagamento.PodeConsultar
	    @GetMapping("/{formaPagamentoId}")
	    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
	    	
	    	ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
	        
	        String eTag = "0";
	        
	        OffsetDateTime dataAtualizacao = formaPagamentoRepository
	                .getDataAtualizacaoById(formaPagamentoId);
	        
	        if (dataAtualizacao != null) {
	            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
	        }
	         
	        if (request.checkNotModified(eTag)) {
	            return null;
	        }
	    	
	        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
	        
	        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoModelAssembler.toModel(formaPagamento);
	        return ResponseEntity.ok()
	        		.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
	        		.eTag(eTag)
	        		.body(formaPagamentoDTO);
	    }
	    
	    @CheckSecurity.FormasPagamento.PodeEditar
	    @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
	        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
	        
	        formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);
	        
	        return formaPagamentoModelAssembler.toModel(formaPagamento);
	    }
	    
	    @CheckSecurity.FormasPagamento.PodeEditar
	    @PutMapping("/{formaPagamentoId}")
	    public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
	            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
	        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
	        
	        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
	        
	        formaPagamentoAtual = cadastroFormaPagamento.salvar(formaPagamentoAtual);
	        
	        return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
	    }
	    
	    @CheckSecurity.FormasPagamento.PodeEditar
	    @DeleteMapping("/{formaPagamentoId}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void remover(@PathVariable Long formaPagamentoId) {
	        cadastroFormaPagamento.excluir(formaPagamentoId);	
	    }   
	}                

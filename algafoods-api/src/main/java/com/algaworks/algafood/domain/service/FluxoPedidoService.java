package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	
	@Transactional //transição de status
	public void confirmar(Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId); //pega o id desse pedido
		pedido.confirmar();
	
	}
	
	@Transactional
	public void entregar(Long pedidoId) {
	    Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
	    pedido.entregar();
	}
	
	@Transactional
	public void cancelar(Long pedidoId) {
	    Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
	    pedido.cancelar();;
	}
}

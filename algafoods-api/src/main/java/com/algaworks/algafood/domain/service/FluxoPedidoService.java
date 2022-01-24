package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional //transição de status
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido); //pega o id desse pedido
		pedido.confirmar();
		 pedidoRepository.save(pedido); //usar o save do repository spring data  pra gerar o evento
	}
	
	@Transactional
	public void entregar(String codigoPedido) {
	    Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
	    pedido.entregar(); //salvaria sem o save
	   
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
	    Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
	    pedido.cancelar();
	    pedidoRepository.save(pedido);
	}
}

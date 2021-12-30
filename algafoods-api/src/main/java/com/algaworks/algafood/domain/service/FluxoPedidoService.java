package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	
	@Transactional //transição de status
	public void confirmar(Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId); //pega o id desse pedido
		
		if(!pedido.getStatus().equals(StatusPedido.CRIADO)) { //vai verificar se o pedido é diferente de confirmado
			throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s", 
                  pedido.getId(), pedido.getStatus().getDescricao(),
                  StatusPedido.CONFIRMADO.getDescricao()));//pega o id e o status e o que quer mudar
		}
		
		pedido.setStatus(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
}

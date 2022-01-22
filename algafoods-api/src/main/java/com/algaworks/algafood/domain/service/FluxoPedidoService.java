package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private EnvioEmailService envioEmailService;
	
	
	@Transactional //transição de status
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido); //pega o id desse pedido
		pedido.confirmar();
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - PedidoConfirmado")
				.corpo("pedido-confirmado.html")
				.variavel("pedido", pedido) //pedido que é o objeto pedido completo
				.destinatario(pedido.getCliente().getEmail())
				//.destinatario("teste@destinatario.com") poderia enviar para outro destinatario tb
				.build();
		
		envioEmailService.enviar(mensagem);
	
	}
	
	@Transactional
	public void entregar(String codigoPedido) {
	    Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
	    pedido.entregar();
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
	    Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
	    pedido.cancelar();;
	}
}

package com.algaworks.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Component //bean spring
public class NotificaçãoPedidoCanceladoListener {
     
	@Autowired  
	private EnvioEmailService envioEmailService;
	
	//um método de evento que vai está interessado qnd esse evento for lançado PedidoConfirmadoEvent
	@TransactionalEventListener 
	public void aoConfirmarPedido(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido(); //pega uma instancia do pedido que acabou de ser confirmado
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido Cancelado")
				.corpo("pedido-cancelado.html")
				.variavel("pedido", pedido) //pedido que é o objeto pedido completo
				.destinatario(pedido.getCliente().getEmail())
				.build();
		 
		envioEmailService.enviar(mensagem);
	}
}

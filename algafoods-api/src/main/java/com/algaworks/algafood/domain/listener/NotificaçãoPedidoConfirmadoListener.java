package com.algaworks.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Component //bean spring
public class NotificaçãoPedidoConfirmadoListener {
     
	@Autowired
	private EnvioEmailService envioEmailService;
	
	@EventListener //um método de evento que vai está interessado qnd esse evento for lançado PedidoConfirmadoEvent
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido(); //pega uma instancia do pedido que acabou de ser confirmado
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - PedidoConfirmado")
				.corpo("pedido-confirmado.html")
				.variavel("pedido", pedido) //pedido que é o objeto pedido completo
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		envioEmailService.enviar(mensagem);
	}
}

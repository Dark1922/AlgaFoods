package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.internet.MimeMessage;
import javax.sound.midi.MidiMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Override
	public void enviar(Mensagem mensagem) {
		try {
		MimeMessage mimeMessage = mailSender.createMimeMessage();  //simboliza a mensagem que queremos enviar por email
		MimeMessageHelper helper  = new MimeMessageHelper(mimeMessage, "UTF-8");//classe auxiliar que ajuda a atribuir os dados do mimeMessage
		helper.setFrom(emailProperties.getRemetente()); //quem vai ser o remetente
		helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));//array dos destinatários de email
		helper.setSubject(mensagem.getAssunto()); //assunto do e-mail
		helper.setText(mensagem.getCorpo(), true); //true que esse texto é em html e n texto puro
		
		
		
		mailSender.send(mimeMessage);
		}catch (Exception e) {
			throw new EmailException("Não foi posivel enviar e-mail", e);
		}
	}

}

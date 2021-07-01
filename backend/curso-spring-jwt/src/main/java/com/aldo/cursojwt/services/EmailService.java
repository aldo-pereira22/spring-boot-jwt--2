package com.aldo.cursojwt.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.aldo.cursojwt.domain.Cliente;
import com.aldo.cursojwt.domain.Pedido;

public interface EmailService {

	public void sendOrderConfirmationEmail(Pedido obj);
	
	public void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}

package com.aldo.cursojwt.services;

import org.springframework.mail.SimpleMailMessage;

import com.aldo.cursojwt.domain.Pedido;

public interface EmailService {

	public void sendOrderConfirmationEmail(Pedido obj);
	
	public void sendEmail(SimpleMailMessage msg);
}

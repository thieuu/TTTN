package com.example.API.service;

import org.springframework.stereotype.Service;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
public boolean sendEmail(String subject,String message,String to) {
		
		boolean f = false;
		String from = "nguyenhieu191299@gmail.com";
		
		String host = "smtp.gmail.com";
		
		Properties properties = System.getProperties();
		
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("nguyenhieu191299@gmail.com","nguyeNhieuU00");
			}
		});
		
		session.setDebug(true);
		
		MimeMessage m = new MimeMessage(session);
		try {
			m.setFrom(from);
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
			m.setSubject(subject);
			m.setText(message);
			
			Transport.send(m);
			System.out.println("Send success........");
			
			f = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return f;
	}
}

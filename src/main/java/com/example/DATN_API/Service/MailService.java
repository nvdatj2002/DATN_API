package com.example.DATN_API.Service;

import com.example.DATN_API.Entity.MailInformation;

import jakarta.mail.MessagingException;

public interface MailService {
	void send(MailInformation mail) throws MessagingException;
	void send(String to ,String subject,String body) throws MessagingException;
	void queue(MailInformation mail);
	void queue(String to,String subject,String body);
}
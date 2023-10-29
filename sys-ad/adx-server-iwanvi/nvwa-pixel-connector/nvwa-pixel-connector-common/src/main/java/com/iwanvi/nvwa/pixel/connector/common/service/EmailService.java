package com.iwanvi.nvwa.pixel.connector.common.service;

public interface EmailService {

	String sendEmail(String subject, String content);
	
	String sendEmail(String subject, String content, String send_to);
	
	String sendEmailBySSL(String subject, String content, String send_to);
}

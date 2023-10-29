package com.iwanvi.nvwa.pixel.connector.common.service.impl;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.PropertyGetter;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.pixel.connector.common.service.EmailService;

import ai.houyi.dorado.rest.server.DoradoServer;
import ai.houyi.dorado.rest.server.DoradoServerBuilder;
import ai.houyi.dorado.spring.SpringContainer;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

	private static String SMTP_HOST = PropertyGetter.getString("email.smtp");
	private static String USER_NAME = PropertyGetter.getString("email.username");
	private static String PASSWORD = PropertyGetter.getString("email.password");
	private static String EMAIL_TO = PropertyGetter.getString("email.to");
	
	@Override
	public String sendEmail(String subject, String content) {
		return sendEmail(subject, content, null);
	}

	public String sendEmail(String subject, String content, String send_to) {
		if(StringUtils.isBlank(send_to)){
			send_to = EMAIL_TO;
		}
		String[] to = send_to.split(Constants.SIGN_COMMA);
		try {
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost(SMTP_HOST);
			mailSender.setUsername(USER_NAME);
			mailSender.setPassword(PASSWORD);
			
			Properties javaMailProperties = new Properties();
			javaMailProperties.put("mail.smtp.auth", "true");
			mailSender.setJavaMailProperties(javaMailProperties);
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, Constants.DEFAULT_ENCODING);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setFrom(USER_NAME);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(content, true);
			
			mailSender.send(mimeMessage);
			return JsonUtils.STATUS_OK();
		} catch (Exception e) {
			LOG.error("sendMail to {} error!", to.toString(), e);
			return JsonUtils.EXCEPTION_ERROR();
		}
	}
	
	public String sendEmailBySSL(String subject, String content, String send_to) {
		if(StringUtils.isBlank(send_to)){
			send_to = EMAIL_TO;
		}
		String[] to = send_to.split(Constants.SIGN_COMMA);
		try {
			JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
			javaMailSender.setHost(SMTP_HOST);//链接服务器
	        javaMailSender.setPort(465);
	        javaMailSender.setUsername(USER_NAME);//账号
	        javaMailSender.setPassword(PASSWORD);//密码
	        javaMailSender.setDefaultEncoding("UTF-8");
	 
	        Properties properties = new Properties();
	        properties.setProperty("mail.smtp.auth", "true");//开启认证
	        properties.setProperty("mail.debug", "true");//启用调试
	        properties.setProperty("mail.smtp.timeout", "10000");//设置链接超时
	        properties.setProperty("mail.smtp.port", Integer.toString(465));//设置端口
	        properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(465));//设置ssl端口
	        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
	        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        properties.setProperty("mail.smtp.ssl.enable", "true");
	        javaMailSender.setJavaMailProperties(properties);
	        
			SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(USER_NAME);
            mailMessage.setSubject(subject);
            mailMessage.setText(content);
            mailMessage.setTo(send_to);
            //发送邮件
            javaMailSender.send(mailMessage);
			return JsonUtils.STATUS_OK();
		} catch (Exception e) {
			LOG.error("sendMail to {} error!", to.toString(), e);
			return JsonUtils.EXCEPTION_ERROR();
		}
	}
	
	public static void main(String[] args) {
		DoradoServer server = DoradoServerBuilder.forPort(9320)
				.scanPackages("com.nvwa").springOn().build();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		SpringContainer.create(applicationContext);
		
		EmailService emailService = (EmailService)applicationContext.getBean("emailService");
		String result = emailService.sendEmail("test_subject", "test_content", null);
		
		System.out.println(result);
		
		LOG.warn("---------complete-------");
		System.exit(-1);
		server.start();
		System.exit(-1);
	}
}

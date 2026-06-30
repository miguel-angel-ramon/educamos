package es.jccm.edu.shared.configuration.mail;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailJccmEs {
	
	@Value("${spring.mail.host}")
	private String mailHost;	
	
	@Value("${spring.mail.username}")
	private String mailUsername;
	
	@Value("${spring.mail.password}")
	private String mailPaswword;

	
	@Bean
	@Qualifier("mailjccmes")
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl ret=new JavaMailSenderImpl();
		ret.setHost(mailHost);
		ret.setUsername(mailUsername);
		ret.setPassword(mailPaswword);
		return ret;
		
	}

}

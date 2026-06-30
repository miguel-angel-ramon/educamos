package es.jccm.edu.shared.configuration.recuperacionclave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import es.jccm.edu.gestionidentidades.adapter.out.mail.EnviadorCorreoRecuperacionClaveJavaMail;


@Configuration
@ConfigurationProperties(prefix = "recuperacion-clave")
public class RecuperacionClaveConfiguration {

	private final RecuperacionClaveConfig recuperacionClaveConfig=new RecuperacionClaveConfig();
	
	@Bean
	public RecuperacionClaveConfig getRecuperacionClaveConfig() {
		return recuperacionClaveConfig;
	}
	
	@Bean
	public int maxMinutosValidezToken(){
		return recuperacionClaveConfig.getMaxMinutosValidezToken();
	}
	
	@Autowired private JavaMailSender mailSender;
	
	@Bean
	public SimpleMailMessage templateMessage() {
		SimpleMailMessage ret=new SimpleMailMessage();
		ret.setFrom("educamosclm@jccm.es");
		ret.setSubject("educamosCLM: credenciales de acceso");
		return ret;
	}
	
	@Bean
	public EnviadorCorreoRecuperacionClaveJavaMail enviadorCorreoRecuperacionClave() {
		EnviadorCorreoRecuperacionClaveJavaMail enviador=new EnviadorCorreoRecuperacionClaveJavaMail();
		enviador.setUrlRecuperacionClave(recuperacionClaveConfig.getUrlRecuperacionClave());
		enviador.setMailSender(mailSender);
		enviador.setTemplateMessage(templateMessage());
		enviador.setMaxMinutosValidezToken(recuperacionClaveConfig.getMaxMinutosValidezToken());
		return enviador;
	}
	

}

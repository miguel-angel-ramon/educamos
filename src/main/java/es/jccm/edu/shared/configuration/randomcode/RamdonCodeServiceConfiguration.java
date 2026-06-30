package es.jccm.edu.shared.configuration.randomcode;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.jccm.cstic.marte.util.randomcodes.logic.impl.ConversorServiceImpl;
import es.jccm.cstic.marte.util.randomcodes.logic.impl.RandomCodeServiceImpl;

@Configuration
public class RamdonCodeServiceConfiguration {
		
	@Bean
	public ConversorServiceImpl conversorService() {
		char[] alfabeto="0123456789ABCDEFHJKLMNPRSTVXY".toCharArray();
		return new ConversorServiceImpl(alfabeto);
	}
	
	@Bean
	public RandomCodeServiceImpl randomCodeService() {
		return new RandomCodeServiceImpl(conversorService(),20,new SecureRandom());
	}
}

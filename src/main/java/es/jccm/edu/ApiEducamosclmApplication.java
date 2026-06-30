package es.jccm.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiEducamosclmApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ApiEducamosclmApplication.class, args);
	}

}

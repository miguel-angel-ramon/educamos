package es.jccm.edu.shared.adapter.ports.out.mail;

import java.io.File;
import java.util.Optional;

import org.apache.commons.lang3.CharSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.jccm.edu.shared.adapter.out.mail.MailSenderAdapter;
import es.jccm.edu.shared.application.ports.out.mail.EmailAttachment;
import es.jccm.edu.shared.application.ports.out.mail.EmailDetails;

@SpringBootTest
public class MailSenderAdapterIT {

	@Autowired
	MailSenderAdapter sut;

	@Test
	public void sentMailWithAttachment2() throws Exception{
		
		EmailAttachment attachment=new EmailAttachment();
		attachment.setFilename("ejemplo.txt");
		attachment.setDatos("hola mundo".getBytes("UTF-8"));
		
		EmailDetails mensaje=new EmailDetails();
		mensaje.setSubject("subject");
		mensaje.setMsgBody("body");
		mensaje.setRecipient("jmartind@jccm.es");
		mensaje.setEmailAttachment(Optional.of(attachment));
		
		sut.sendMailWithAttachment(mensaje);
		
	}
}

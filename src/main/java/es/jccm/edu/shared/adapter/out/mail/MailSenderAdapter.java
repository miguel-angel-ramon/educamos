package es.jccm.edu.shared.adapter.out.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import es.jccm.edu.shared.application.ports.out.mail.EmailAttachment;
import es.jccm.edu.shared.application.ports.out.mail.EmailDetails;
import es.jccm.edu.shared.application.ports.out.mail.MailSenderPortOut;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailSenderAdapter implements MailSenderPortOut{

	@Autowired 
    @Qualifier("mailjccmes")
    private JavaMailSender javaMailSender;
	
	@Value("${educamos.mail.sender}") private String sender;

	@Override
	public String sendSimpleMail(EmailDetails details) {
		// Try block to check for exceptions
        try {
 
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                = new SimpleMailMessage();
 
            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
 
            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail enviado correctamente.";
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
        	log.error("ERROR mientras se enviaba el MAIL.",e);
            return "Error mientras se enviaba el Mail!";
        }
	}


	
	@Override
	public String sendMailWithAttachment(EmailDetails details) {
		 // Creating a mime message
        MimeMessage mimeMessage
            = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
 
        try {
 
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                details.getSubject());
 
            if(details.getEmailAttachment().isPresent()) {
            	EmailAttachment emailAttachment = details.getEmailAttachment().get();
            	
            	mimeMessageHelper.addAttachment(emailAttachment.getFilename(),
            			new ByteArrayResource(emailAttachment.getDatos()));
            }
 
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail enviado Correctamente";
        }
 
        // Catch block to handle MessagingException
        catch (MessagingException e) {
        	log.error("ERROR mientras se enviaba el MAIL.",e);
            // Display message when exception occurred
            return "Error mientras se enviaba el Mail!";
        }
	}

	@Override
	public String sendFormatedMail(EmailDetails details) {
		 // Creating a mime message
        MimeMessage mimeMessage
            = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
 
        try {
 
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                details.getSubject());
 
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail enviado Correctamente";
        }
 
        // Catch block to handle MessagingException
        catch (MessagingException e) {
        	log.error("ERROR mientras se enviaba el MAIL.",e);
            // Display message when exception occurred
            return "Error mientras se enviaba el Mail!";
        }
	}
	

	 @Override
		public String sendFormatedMailWithImage(EmailDetails details, String image) {
			 // Creating a mime message
	        MimeMessage mimeMessage
	            = javaMailSender.createMimeMessage();
	        MimeMessageHelper mimeMessageHelper;
	 
	        try {
	 
	        	Log.info("Inicio sendFormatedMailWithImage");
	            // Setting multipart as true for attachments to
	            // be send
	            mimeMessageHelper
	                = new MimeMessageHelper(mimeMessage, true);
	            mimeMessageHelper.setFrom(sender);
	            mimeMessageHelper.setTo(details.getRecipient());
	            mimeMessageHelper.setText(details.getMsgBody(), true);
	            mimeMessageHelper.setSubject(
	                details.getSubject());

	            ClassPathResource classPathResource = new ClassPathResource("shared/logo_correo.gif");
	            mimeMessageHelper.addInline("imagen_plataforma", classPathResource);
	 
	            // Sending the mail
	            javaMailSender.send(mimeMessage);
	            
	            Log.info("Mail enviado Correctamente");
	            
	            return "Mail enviado Correctamente";
	        }
	 
	        // Catch block to handle MessagingException
	        catch (MessagingException e) {
	        	log.error("ERROR mientras se enviaba el MAIL.",e);
	            // Display message when exception occurred
	            return "Error mientras se enviaba el Mail!";
	        }
		}
}

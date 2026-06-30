package es.jccm.edu.shared.application.ports.out.mail;

public interface MailSenderPortOut {

	// Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    
    // Method
    // To send a simple formated email
    String sendFormatedMail(EmailDetails details);
 
    
    // Method
    // To send a simple formated email with image
    String sendFormatedMailWithImage(EmailDetails details, String image);

	String sendMailWithAttachment(EmailDetails details);
}

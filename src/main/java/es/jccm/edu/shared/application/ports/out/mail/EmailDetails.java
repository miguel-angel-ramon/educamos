package es.jccm.edu.shared.application.ports.out.mail;

import java.util.Optional;

import lombok.Data;

@Data
public class EmailDetails {

	private String recipient;
	private String msgBody;
	private String subject;
	private Optional<EmailAttachment> emailAttachment;
}

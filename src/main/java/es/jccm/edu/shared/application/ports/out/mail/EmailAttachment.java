package es.jccm.edu.shared.application.ports.out.mail;

import lombok.Data;

@Data
public class EmailAttachment {
	private String filename;
	private byte[] datos;
}

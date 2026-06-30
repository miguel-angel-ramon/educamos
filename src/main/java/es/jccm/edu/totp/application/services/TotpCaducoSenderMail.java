package es.jccm.edu.totp.application.services;

import org.springframework.stereotype.Service;

import es.jccm.edu.shared.application.ports.out.mail.EmailDetails;
import es.jccm.edu.shared.application.ports.out.mail.MailSenderPortOut;
import es.jccm.edu.totp.application.domain.PreferenciasUsuario;
import es.jccm.edu.totp.application.domain.TotpCaduco;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TotpCaducoSenderMail {
	
    private MailSenderPortOut mailSenderPortOut;

	public TotpCaducoSenderMail(MailSenderPortOut mailSenderPortOut) {
		super();
		this.mailSenderPortOut = mailSenderPortOut;
	}

	public boolean send(PreferenciasUsuario preferencias, TotpCaduco totp) {
		
		if(preferencias.getMail().isEmpty()) {
			return false;
		}

		String email=preferencias.getMail().get();
		
        EmailDetails emailDetails = new EmailDetails();

        emailDetails.setRecipient(email);
        emailDetails.setMsgBody("Para acceder a la funcionalidad de EducamosCLM, use el código siguiente para verificar su autorización. El código solamente funcionará durante " + totp.getDuracion() + " minutos. <br/>" +
                "Código de verificación: " + totp.getCodigo2FA() + "<br/> Si no se ha solicitado ningún código, no es necesario leer este correo electrónico."
                + "<br /><br /><img src='cid:"+"imagen_plataforma"+"' />");
        emailDetails.setSubject("Código de verificación");

        log.info("Send MailCode");
        
        mailSenderPortOut.sendFormatedMailWithImage(emailDetails, "imagen_plataforma");
        return true;

    }

}

package es.jccm.edu.totp.application.services;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.itextpdf.io.source.ByteArrayOutputStream;

import es.jccm.edu.shared.application.ports.out.mail.EmailAttachment;
import es.jccm.edu.shared.application.ports.out.mail.EmailDetails;
import es.jccm.edu.shared.application.ports.out.mail.MailSenderPortOut;
import es.jccm.edu.totp.application.domain.Cuenta2faGoogle;
import es.jccm.edu.totp.application.ports.in.GestionCuentaGoogleUC;
import es.jccm.edu.totp.application.ports.in.ValidarCodigoTotpUC;
import es.jccm.edu.totp.application.ports.out.Cuenta2faGoogleRepository;
import es.jccm.edu.totp.application.ports.out.GoogleAuthenticator;
import es.jccm.edu.totp.application.ports.out.generadorqr.GeneradorQr;

@Service
public class TotpGoogleService implements ValidarCodigoTotpUC, GestionCuentaGoogleUC{
	private Cuenta2faGoogleRepository cuenta2faGoogleRepository;
	private GoogleAuthenticator google;

	private GeneradorQr generadorQr;
    private MailSenderPortOut mailSenderPortOut;

	
	public TotpGoogleService(Cuenta2faGoogleRepository cuenta2faGoogleRepository,GoogleAuthenticator google, GeneradorQr generadorQr,MailSenderPortOut mailSenderPortOut) {
		super();
		this.cuenta2faGoogleRepository = cuenta2faGoogleRepository;
		this.google=google;
		this.generadorQr=generadorQr;
		this.mailSenderPortOut=mailSenderPortOut;
	}


	@Override
	public boolean validarCodigoTotp(String codigo, long idUsuario) {

    	Cuenta2faGoogle clave2fa = cuenta2faGoogleRepository.findByOid(idUsuario).orElseThrow();

        if (clave2fa.getSecretKey2FA() == null) {
            return false;
        }

        return Objects.equals(google.getTOTPCode(clave2fa.getSecretKey2FA()), codigo);
	}
	
    /**
     * genera un código qr que permite registrar un dispositivo con google authenticator....
     * TODO: revisar esto.
     * Creo que no debería de enviarse por email. Debería mostrarse el qr por pantalla una única vez para la gente que
     * necesite activar la doble autenticación.
     * 
     * El email te lo pueden pillar otros 
     */
    
    @Override
    public boolean enviarQrActivacionGoogle(long idUsuario, String email) {

        Cuenta2faGoogle usuario = cuenta2faGoogleRepository.findByOid(idUsuario).orElseThrow();

        if (email == null) return false;

        // TODO: REVISAR ESTO:... si el usuario ya tiene un secret generado no hay forma de volver generar otro enlace nuevo con otro secret
        // por ejemplo en el caso de que el usuario pierda el movil o simplemente quiera volver a generarlo porque no se fie de que le hayan
        // pillado el secret por otro lado
        
        if (usuario.getSecretKey2FA() == null) {
        	usuario=usuario.toBuilder().secretKey2FA(google.generateSecretKey()).build();
        	cuenta2faGoogleRepository.update(usuario);
        }
       
        String googleAuthenticatorBarCode = usuario.getUrlInstalacion();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        generadorQr.generate(googleAuthenticatorBarCode, byteArrayOutputStream);

        //TODO: no mandar por correo
        sendMailQRCode(email,byteArrayOutputStream.toByteArray());

        return true;
    }
    
    
    

    @Deprecated
    private void sendMailQRCode(String email,byte[] datosqr) {

        EmailDetails emailDetails = new EmailDetails();

        emailDetails.setRecipient(email);
        emailDetails.setMsgBody("Prueba");
        emailDetails.setSubject("Código QR 2FA Educamos");
        EmailAttachment attachment=new EmailAttachment();
        attachment.setFilename("qr2FA.png");
        attachment.setDatos(datosqr);
        
        emailDetails.setEmailAttachment(Optional.of(attachment));

        mailSenderPortOut.sendMailWithAttachment(emailDetails);
    }

}

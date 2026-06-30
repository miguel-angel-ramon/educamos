package es.jccm.edu.gestionidentidades.adapter.out.mail;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import es.jccm.edu.gestionidentidades.application.domain.SolicitudRecuperacionClave;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.ports.out.EnviadorCorreoRecuperacionClave;

public class EnviadorCorreoRecuperacionClaveJavaMail implements EnviadorCorreoRecuperacionClave{
	private static final String IMAGEN_EMBEBIDA = "imagen_plataforma";

	//TODO meter logger
	//TODO se ha cambiado UsuarioModuloAcceso por Usuario temporalmente hay que ajustarlo
	//private static Logger log = Logger.getLogger(EnviadorCorreoRecuperacionClaveJavaMail.class);
	
	private String urlRecuperacionClave;
	private JavaMailSender mailSender;
	private SimpleMailMessage templateMessage;
	private int maxMinutosValidezToken;

	public void setUrlRecuperacionClave(String urlRecuperacionClave) {
		this.urlRecuperacionClave = urlRecuperacionClave;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

	public void setMaxMinutosValidezToken(int maxMinutosValidezToken) {
		this.maxMinutosValidezToken = maxMinutosValidezToken;
	}

	@Override
	public String enviarCorreoDeRecuperacionDeClave(SolicitudRecuperacionClave datosRecuperacionClave, Usuario user) {

		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(user.getCorreoE());
			helper.setFrom(this.templateMessage.getFrom());
			helper.setSubject(this.templateMessage.getSubject());
			
			helper.setText(getCuerpoMensajeCorreoRegeneracion(datosRecuperacionClave, user), true);

			ClassPathResource classPathResource = new ClassPathResource("images/logo_correo.gif");
			helper.addInline(IMAGEN_EMBEBIDA, classPathResource);

			this.mailSender.send(message);
		} catch (MailException ex) {
			//log.error(ex);
			return ex.getMessage();
		} catch (MessagingException e) {
			//log.error("error no controlado al mandar mensaje",e);
			return e.getMessage();
		}
		return null;
	}
	
	public String getCuerpoMensajeCorreoRegeneracion(SolicitudRecuperacionClave datosRecuperacionClave, Usuario user) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String url = urlRecuperacionClave + "user=" + user.getLogin() + "&id=" + datosRecuperacionClave.getId() + "&token=" + datosRecuperacionClave.getToken();
		String cuerpo =
				" La plataforma educativa EducamosCLM, de la Junta de Comunidades de Castilla-La Mancha ha recibido una solicitud para restablecer la contrase&ntilde;a de su cuenta de acceso. <br/>"
				+ "Si usted no es usuario de esta plataforma, ignore este mensaje. </br>"
				+ "Si usted es usuario pero no ha realizado dicha solicitud, puede que alguien est&eacute; intentando interceptar su cuenta. Verifique que sus claves personales funcionan correctamente y si no puede realizar el acceso, p&oacute;ngase en contacto con el Centro de Atenci&oacute;n a Usuarios.</br></br>"
				+ "Si desea realizar un cambio de contrase&ntilde;a, por favor clique en el siguiente enlace personalizado (o copie y pegue la direcci&oacute;n URL en un navegador de Internet) que le permitir&aacute; realizar la operaci&oacute;n a trav&eacute;s de una conexi&oacute;n segura:</br>"
				+ "<a href='" + url + "'>" + url + "</a><br /><br />"
				+ "<b>Atenci&oacute;n:</b> El enlace tiene una validez de <b>" + maxMinutosValidezToken
				+ " minutos</b> desde que se solicit&oacute; en la plataforma, transcurrido ese tiempo, el enlace deja de funcionar por lo que para recuperar su contrase&ntilde;a deber&iacute;a volver a iniciar la operaci&oacute;n de nuevo."
				+ "<br />Recuerde que si persisten sus problemas de acceso puede contactar con el Centro de Atenci&oacute;n a Usuarios."
				+ "<br /><br />Momento de generaci&oacute;n de este correo: " + sdf.format(new Date())
				+ "<br /><br /><img src='cid:"+IMAGEN_EMBEBIDA+"' />";
				

		return cuerpo;
	}

}

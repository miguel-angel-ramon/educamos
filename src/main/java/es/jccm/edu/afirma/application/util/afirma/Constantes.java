package es.jccm.edu.afirma.application.util.afirma;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Constantes {

	@Value("${afirma.integrator.host}")
	private String hostAfirma;

	@Value ("${afirma.integrator.versionAfirma}")
	private String versionAfirma;

	@Value("${afirma.integrator.rutaAlmacenCert}")
	private String rutaAlmacenCert;

	@Value("${afirma.integrator.claveAlmacenCert}")
	private String claveAlmacenCert;

	@Value("${afirma.integrator.idApp}")
	private String idApp;

	@Value("${afirma.integrator.user}")
	private String user;

	@Value("${afirma.integrator.password}")
	private String password;

	@Value("${afirma.certificado.pruebas}")
	private String listaCertificadosPrueba;

	@Value("${afirma.parametros.algoritmoHash}")
	private String algoritmoHash;

	@Value("${afirma.parametros.formatoFirma}")
	private String formatoFirma;

	@Value("${afirma.parametros.modoFirmaXML}")
	private String modoFirmaXML;
}
package es.jccm.edu.sms.application.services.sms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.Normalizer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jccm.edu.ws.sms.Ws_adaptor;
import com.jccm.edu.ws.sms.Ws_adaptorLocator;
import com.jccm.edu.ws.sms.Ws_adaptorPortStub;
import com.jccm.edu.ws.sms.Ws_adaptorPort_PortType;

import es.jccm.edu.pdc.application.services.cuestionarios.CuestionarioService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.simulacion.adapter.in.rest.usuarios.model.DatosPersonalesUsuarioDto;
import es.jccm.edu.simulacion.adapter.in.rest.usuarios.model.DatosUsuarioDto;
import es.jccm.edu.sms.adapter.in.rest.sms.model.DocumentoFirmadoSmsDTO;
import es.jccm.edu.sms.adapter.in.rest.sms.model.DocumentoPendienteFirmaSmsDTO;
import es.jccm.edu.sms.adapter.out.repositories.sms.SmsRepository;
import es.jccm.edu.sms.application.domain.sms.entities.Sms;
import es.jccm.edu.sms.application.domain.sms.projection.FlujoAdjuntosProjection;
import es.jccm.edu.sms.application.domain.sms.projection.SmsProjection;
import es.jccm.edu.sms.application.ports.in.sms.ISmsService;


@Service
public class SmsService implements ISmsService {

	// TODO: ¿Rescate de comunica datos ?
	private static final int TIPO_CONTRATO = 14;
	private static final String SMS_MENSAJE = "Su codigo de acceso es: ";
	
	//private static final String USERNAME = "smseduc";
	//private static final String PASSWORD = "64fhs&gy";
	//private static final String URL = "https://sms2022-pre.jccm.es/limsp-ws-mt-connector/services/ws_adaptorPort";
	
	@Autowired
	private SmsRepository smsRepository;
	
	private static final Logger LOG = LogManager.getLogger(CuestionarioService.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${sms.entorno.username}")
	private  String USERNAME;
	
	@Value("${sms.entorno.password}")
	private  String PASSWORD;
	
	@Value("${sms.entorno.url}")
	private  String URL;
	
	/**
	 * Método que envía el sms al usuario con el código de verificación
	 * 
	 * @param datosUsuarioPersonales Datos del usuario.
	 * @param xUsuario               Identificador del usuario.
	 * @return No devuelve nada.
	 */
	@Override
	public void envia(DatosPersonalesUsuarioDto datosUsuarioPersonales, Long xUsuario) throws Exception {		
		Ws_adaptor adaptor = new Ws_adaptorLocator();
		Ws_adaptorPort_PortType port;
		URL url = new URL(URL);
		String username = USERNAME;
		String password = PASSWORD;
		try {
			port = adaptor.getws_adaptorPort(url);
			((Ws_adaptorPortStub) port).setUsername(username);
			((Ws_adaptorPortStub) port).setPassword(password);
			port.putMessage(getMensajeXml(datosUsuarioPersonales, xUsuario));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage() + " " + e.getCause() + " " + e.getLocalizedMessage());
			throw new Exception("Error en el envío", e);
		}
	}

	/**
	 * Método que genera el mensaje xml para enviar al servicio de sms en nuestro
	 * caso Mentes. El código puede contener 6 caracteres alfanuméricos. Se define
	 * además lActivo a true (1).
	 * 
	 * @param datosUsuarioPersonales Datos del usuario.
	 * @param xUsuario               Identificador del usuario.
	 * @return Devuelve el mensaje xml.
	 */
	@Override
	public String getMensajeXml(DatosPersonalesUsuarioDto datosUsuarioPersonales, Long xUsuario) {

		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String codigoVerificacion = RandomStringUtils.random(6, characters);
		String msgId = "123456";
		String finalCode = SMS_MENSAJE + codigoVerificacion;

		Long telefono = Long.parseLong(datosUsuarioPersonales.getTelefono1());
		Long lActivo = 1L;
		smsRepository.saveDatosEnvio(codigoVerificacion, telefono, lActivo, xUsuario);

		String xml = "<?xml version='1.0' encoding='UTF-8'?>";
		xml += "<message id = '" + msgId + "' ts = '" + String.valueOf(System.currentTimeMillis()) + "' >";
		xml += "<head>";
		xml += "<type ref='sms'>";
		xml += "<format>text</format>";
		xml += "<mroute>MT</mroute>";
		xml += "</type>";
		xml += "<info>";
		xml += "<gsmDest>" + telefono + "</gsmDest>";
		xml += "<idContract>" + TIPO_CONTRATO + "</idContract>";
		xml += "</info>";
		xml += "</head>";
		xml += "<body>";
		xml += "<contentOut>" + finalCode + "</contentOut>";
		xml += "</body>";
		xml += "</message>";
		return xml;
	}

	/**
	 * Método el cual rescata los datos como lActivo, fecha de creación y fecha de
	 * modificación. Realiza un control de si el código de verificación es válido o
	 * no. En caso de que lo sea devuelve true, en caso contrario false. Si es true,
	 * se actualiza el campo lActivo a false (0).
	 * 
	 * @param codigoVerificacion Código de verificación que llega desde la petición.
	 * @return Devuelve un true si el código está activo y además han pasado menos
	 *         de 5 minutos desde su creación.
	 */
	@Override
	public Boolean getVerificacionSmsByCodigo(String codigoVerificacion, Long xUsuario) {

		String mensaje = String.format("Obteniendo los registros para el codigo de verificacion: %s",
				codigoVerificacion);
		LOG.info(mensaje);
		try {
			SmsProjection smsProjection = smsRepository.getVerificacionByCodigo(codigoVerificacion, xUsuario);
			if (smsProjection == null) {
				return false;
			} else {
				Sms smsOut = modelMapper.map(smsProjection, Sms.class);
				if (smsOut.getLActivo() == 1
						&& smsOut.getFechaCreacion().isAfter(LocalDateTime.now().minusMinutes(5))) {
					smsRepository.updateEstadoCodigoVerificacion(codigoVerificacion);
					return true;
				} else {
					return false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Método que comprueba si el código de verificación es válido. En caso de que
	 * lo sea, se llama al método addFirmaPdfBox.
	 * 
	 * @param documentosBase64   Documentos en base64.
	 * @param codigoVerificacion Código de verificación que llega desde la petición.
	 * @param datosUsuario       Identificador del usuario.
	 * @return Devuelve un objeto DocumentoFirmadoDto con el documento firmado en
	 *         formato base64.
	 * @throws IOException Si ocurre un error al leer o guardar el documento.
	 */
	@Override
	public List<DocumentoFirmadoSmsDTO> verificarFirmar(List<DocumentoPendienteFirmaSmsDTO> documentosBase64,
			DatosUsuarioJwt datosUsuario) throws IOException {
		/*
		 * if (smsRepository.verificarCodigoFirma(codigoVerificacion,
		 * datosUsuario.getXUsuarioComunica()) != 1) { throw new
		 * IllegalArgumentException("El código de verificación no es válido o ha caducado."
		 * ); }
		 */
		return addFirmaPdfBox(documentosBase64, datosUsuario);
	}

	/**
	 * Método que realiza un firmado masivo de documentos mediante llamada al método
	 * addFirmaPdfBox.
	 * 
	 * @param documentosBase64 Documentos en base64.
	 * @param datosUsuario     Identificador del usuario.
	 * @return Devuelve un objeto DocumentoFirmadoDto con el documento firmado en
	 *         formato base64.
	 * @throws IOException Si ocurre un error al leer o guardar el documento.
	 */
	@Override
	public List<DocumentoFirmadoSmsDTO> firmarDocumentos(List<DocumentoPendienteFirmaSmsDTO> documentosBase64,
			DatosUsuarioJwt datosUsuario) throws IOException {
		return addFirmaPdfBox(documentosBase64, datosUsuario);
	}

	/**
	 * Este método permite agregar una firma a un documento en formato PDF
	 * utilizando la librería PDFBox.
	 * 
	 * @param datosUsuario Cadena en base64 del documento a firmar.
	 * @return Retorna un objeto DocumentoFirmadoDto con el documento firmado en
	 *         formato base64.
	 * @throws IOException Si ocurre un error al leer o guardar el documento.
	 */

	public List<DocumentoFirmadoSmsDTO> addFirmaPdfBox(List<DocumentoPendienteFirmaSmsDTO> documentosBase64,
			DatosUsuarioJwt datosUsuario) throws IOException {

		List<DocumentoFirmadoSmsDTO> documentosFirmados = new ArrayList<DocumentoFirmadoSmsDTO>();
		for (DocumentoPendienteFirmaSmsDTO documentoBase64 : documentosBase64) {
			
			DocumentoFirmadoSmsDTO documentoFirmado = new DocumentoFirmadoSmsDTO();
			
			if (documentoBase64.getTipodocumento() != null && !documentoBase64.getTipodocumento().equals("")  && documentoBase64.getTipodocumento().equals("Parte mensual")) 	
			{				
				documentoFirmado.setDocumentoBase64(documentoBase64.getDocumentoBase64());
				documentosFirmados.add(documentoFirmado);
				
			} else {
				
				List<FlujoAdjuntosProjection> flujo = smsRepository.getFlujoFirma(documentoBase64.getIdDocumento());
				byte[] documentBytes = Base64.getDecoder().decode(documentoBase64.getDocumentoBase64().getBytes());
				PDDocument pdd = PDDocument.load(documentBytes);

				PDPageContentStream contentStream = null;

				PDPage lastPage = pdd.getPage(pdd.getNumberOfPages() - 1);
				PDFTextStripper striper = new PDFTextStripper();
				String cadena = striper.getText(pdd);

				String firmado = "Firmado por";
				String cabecera = "Hoja de firmas";
				int numeroFirmas = 1;
				int startIndex = 0;
				int posicionFirmante = 0;
				boolean tieneFirmas = false;
				while (cadena.contains(cabecera)) {
					
					tieneFirmas = true;
					startIndex = cadena.indexOf(cabecera) + cabecera.length();
					cadena = cadena.substring(startIndex);
				}
				if (tieneFirmas) {
					while (cadena.contains(firmado)) {
						numeroFirmas++;
						startIndex = cadena.indexOf(firmado) + firmado.length();
						cadena = cadena.substring(startIndex);
					}
				}
				
			

				if (true) {
					numeroFirmas = 1;
					
					PDPage clonedPage = pdd.getPage(pdd.getNumberOfPages() - 1);
					
					if(!tieneFirmas) {
						
						clonedPage = pdd.importPage(lastPage);	
						
					} 
					
					pdd.getPage(pdd.getNumberOfPages() - 1).setContents(new PDStream(pdd));
					int rotacion = pdd.getPage(pdd.getNumberOfPages() - 1).getRotation();
					if (rotacion == 0) {
						pdd.getPage(pdd.getNumberOfPages() - 1).setRotation(0);
					}
					clonedPage = pdd.getPage(pdd.getNumberOfPages() - 1);
					contentStream = new PDPageContentStream(pdd, clonedPage, PDPageContentStream.AppendMode.OVERWRITE, true);
					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
					contentStream.newLineAtOffset(40, clonedPage.getMediaBox().getHeight() - 40);
					contentStream.showText(cabecera);
					contentStream.endText();
					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
					contentStream.newLineAtOffset(40, clonedPage.getMediaBox().getHeight() - 60 - numeroFirmas * 20);
					contentStream.showText("Nombre y Apellidos");
					contentStream.newLineAtOffset(190, 0);
					contentStream.showText("DNI / NIE / Pasaporte");
					contentStream.newLineAtOffset(150, 0);
					contentStream.showText("Fecha de firma");
					contentStream.endText();
					numeroFirmas++;
					contentStream.lineTo(40, clonedPage.getMediaBox().getHeight() - 50);
					for (FlujoAdjuntosProjection flujoIndividual : flujo) {
						contentStream.beginText();
						contentStream.setFont(PDType1Font.HELVETICA, 10);
						float coordenadaY = clonedPage.getMediaBox().getHeight() - 60 - numeroFirmas * 20;
						contentStream.newLineAtOffset(40, coordenadaY);
						contentStream.showText(flujoIndividual.getNombre() + " " + flujoIndividual.getApellido1() + " "
								+ flujoIndividual.getApellido2());
						contentStream.newLineAtOffset(190, 0);
						contentStream.showText(flujoIndividual.getDni());
						if(flujoIndividual.getFirmado() == 1L) {
							contentStream.newLineAtOffset(150, 0);
							String str = new SimpleDateFormat("dd-MM-yyyy").format(flujoIndividual.getFechaFirma());
							contentStream.showText("Firmado el día " + str);
							
						}
						contentStream.endText();				
						numeroFirmas++;

					}
					contentStream.close(); // Move the close() call outside the loop
				}

				striper.setStartPage(pdd.getNumberOfPages());
				striper.setEndPage(pdd.getNumberOfPages());
				String firmantes = striper.getText(pdd);
				String nombre =datosUsuario.getNif();
				
				 String normalizedStr1 = Normalizer.normalize(nombre.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
	                     .replaceAll("\\s", "");
				String normalizedFirmantes= Normalizer.normalize(firmantes.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
	                    .replaceAll("\\s", "");

				
				if (normalizedFirmantes.contains(normalizedStr1)) {
					int positionFirmante = -1;
					for (int i = 0; i < flujo.size(); i++) {
					    FlujoAdjuntosProjection flujoIndividual = flujo.get(i);
					    String nombreFlujo = flujoIndividual.getDni();
					    String normalizedStr2 = Normalizer.normalize(nombreFlujo.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
	                            .replaceAll("\\s", "");
					    
					    if(normalizedStr2.contains(normalizedStr1)) {
					    	positionFirmante = i +2;
					    	
					    }
					}
					PDPage lastPageF = pdd.getPage(pdd.getNumberOfPages() - 1);
					contentStream = new PDPageContentStream(pdd, lastPageF, PDPageContentStream.AppendMode.APPEND, true);
					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA, 10);
					float coordenadaYFirma = lastPageF.getMediaBox().getHeight() - 60 - positionFirmante * 20;
					
					contentStream.newLineAtOffset(380, coordenadaYFirma);
					

					// Text
					Date date = new Date();
					String str = new SimpleDateFormat("dd-MM-yyyy").format(date);
					contentStream.showText("Firmado el día " + str);
					contentStream.endText();
					contentStream.close();
					//String mensaje = String.format("Los datos son: %s", datosPersonalesOut);
					//LOG.info(mensaje);

					// Guardamos archivo en base64
				
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				pdd.save(baos);
				pdd.close();
				String documentoBase64Out = Base64.getEncoder().encodeToString(baos.toByteArray());
				documentoFirmado.setDocumentoBase64(documentoBase64Out);
				documentosFirmados.add(documentoFirmado);		
			}
		}

		return documentosFirmados;
	}
    @Override
    public String getTelefonoUsuarioLogado(Long idEmpleadoComunica) {
    	String telefono = smsRepository.getTelefonoUsuarioLogado(idEmpleadoComunica);
    	
    	if(telefono != null) {
    		telefono = telefono.substring(0, 5).replaceAll(".", "*") + telefono.substring(5);	
    	}
    	
        return telefono;
    }


}

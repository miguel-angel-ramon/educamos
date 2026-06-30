package es.jccm.edu.afirma.application.services.afirma;

import es.jccm.edu.afirma.adapter.in.rest.afirma.model.DocumentoFirmadoDto;
import es.jccm.edu.afirma.application.ports.in.afirma.IAfirmaService;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;




@Service
@PropertySource("classpath:afirma.properties")
@Slf4j
public class AfirmaService  implements IAfirmaService {

	@Value("${cesar.url}")
	private String URL; 
	
	@Value("${cesar.token}")
	private String token;
	
	@Value("${cesar.urlactualizada}")
	private String URL2; 
	
	@Value("${cesar.usuario}")
	private String usuario; 
	
	@Value("${cesar.password}")
	private String password;

	public DocumentoFirmadoDto verificarFirma(String signBase64, String documentoBase64)
			throws SOAPException, ParserConfigurationException, IOException, SAXException {

			
			DocumentoFirmadoDto respuesta = new DocumentoFirmadoDto(); 
			URL url = new URL(URL);
			HttpsURLConnection http = (HttpsURLConnection)url.openConnection();
			 SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			http.setSSLSocketFactory(sslsocketfactory);
			http.setDoOutput(true);
			http.setHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						// TODO Auto-generated method stub
						return true;
					}
			    });
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Content-Type", "application/soap+xml");
			http.setRequestProperty("Authorization", token);

			
			String data2 = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:rfc:functions\"> "
					+ "<soapenv:Header/> "
					+ "<soapenv:Body> "
					+ "<urn:Z_CS_VALIDAR> "
					+ "<DATFIRMA>"+signBase64+"</DATFIRMA> "
					+ "<FIRMA>"+documentoBase64+"</FIRMA> "
					+ "<IDAPLIC>jccm.modernizacion</IDAPLIC> "
					+ "<TIPO>P</TIPO> "
					+ "</urn:Z_CS_VALIDAR> "
					+ "</soapenv:Body> "
					+ "</soapenv:Envelope>";

			byte[] out = data2.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = http.getOutputStream();
			stream.write(out);
			BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = in.readLine()) != null)
			{
				response.append(inputLine);
			}
			in.close();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document document = builder.parse(new InputSource(new StringReader(response.toString())));
			Element rootElement = document.getDocumentElement();
			String DENERROR = getString("DENERROR", rootElement);
			String RESUL = getString("RESUL", rootElement);
			String SUBJECT = getString("SUBJECT", rootElement);
			String CERTIFICADO = getString("CERTIFICADO", rootElement);
			String URITIPOFIRMA = getString("URITIPOFIRMA", rootElement);
			String URIFORMAVA = getString("URIFORMAVA", rootElement);
			respuesta.setCertificado(CERTIFICADO);
			respuesta.setDescripccion(DENERROR);
			respuesta.setResultado(RESUL);
			respuesta.setSubject(SUBJECT);
			respuesta.setUritipofirma(URITIPOFIRMA);
			respuesta.setUriformava(URIFORMAVA);
			http.disconnect();
			return respuesta;
		}
	
	protected String getString(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        String resp = null;
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                resp =  subList.item(0).getNodeValue().toString();
            }
        }
		return resp;
	}
	
	
	public Integer verificarFirma2(String signBase64, String documentoBase64, String nif)
			       throws SOAPException, ParserConfigurationException, IOException, SAXException {


		Integer valido = esDocFirmado(documentoBase64,nif);
		
		return valido;
		
	
	}

	public Integer esDocFirmado(String documentoBase64, String nif) throws SOAPException, ParserConfigurationException, IOException, SAXException {
		
		String auth =  usuario + ":" + password;
		String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
			
		URL url = new URL(URL2);
		HttpsURLConnection http = (HttpsURLConnection)url.openConnection();
	    SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		http.setSSLSocketFactory(sslsocketfactory);
		http.setHostnameVerifier(new HostnameVerifier() {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			// TODO Auto-generated method stub
			return true;
		}
	    });
	    http.setRequestMethod("POST");
	    http.setDoOutput(true);
		http.setRequestProperty("Content-Type", "application/soap+xml");
		http.setRequestProperty ("Authorization", "Basic " + encodedAuth);
		
		String data2 = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:val=\"http://jccm.es/po/afirma/ValidarFirma\">"
			+ "   <soapenv:Header/>"
			+ "   <soapenv:Body>"
			+ "      <val:MT_AFIRMA_VaidarFirma_req>"
			+ "        <DATFIRMA></DATFIRMA>"
			+ "         <FIRMA>"+documentoBase64+"</FIRMA>"
			+ "         <IDAPLIC>jccm.modernizacion</IDAPLIC>"
			+ "         <TIPO>P</TIPO>"
			+ "      </val:MT_AFIRMA_VaidarFirma_req>"
			+ "   </soapenv:Body>"
			+ "</soapenv:Envelope>";

		byte[] out = data2.getBytes(StandardCharsets.UTF_8);
		OutputStream stream = http.getOutputStream();
		stream.write(out);
			
		int responseCode = http.getResponseCode();
		BufferedReader in = null;
		if (responseCode == java.net.HttpURLConnection.HTTP_OK) {
		    // La solicitud fue exitosa, puedes obtener el InputStream
			in =new BufferedReader(new InputStreamReader(http.getInputStream()));
		    // Resto del código aquí
		} else {
    	    // Manejar el código de respuesta HTTP no exitoso
		    log.error("Error en la solicitud HTTP: " + responseCode);
		    throw new IllegalStateException();
		}
			
		
		//BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while((inputLine = in.readLine()) != null)
		{
			response.append(inputLine);
		}
		in.close();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document document = builder.parse(new InputSource(new StringReader(response.toString())));
		Element rootElement = document.getDocumentElement();
		String RESUL = getString("RESUL", rootElement);			
		http.disconnect();
		
		if (RESUL.equals("0")) {
			
			RESUL = getResul(nif, rootElement, RESUL);					
		}		
	
		return Integer.parseInt(RESUL);
	}
	
	private String getResul(String nif, Element rootElement, String rESUL) {
		NodeList firmanteList = rootElement.getElementsByTagName("FIRMANTE");
		
		if (firmanteList.getLength() > 0) { 				  
			
			rESUL = "1"; 				
			
			for (int i = 0; i < firmanteList.getLength(); i++) 
			{
			
			Element firmanteElement = (Element) firmanteList.item(i);
			
			Element subjectElement = (Element) firmanteElement.getElementsByTagName("SUBJECT").item(0);
			
			if (subjectElement != null) {
				
				 String subjectValue = subjectElement.getTextContent();

				 if (subjectValue.contains(nif)) {
					 rESUL = "0";
				 }
			}
		  }
		}
		return rESUL;
	}
   
}




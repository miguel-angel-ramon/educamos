package es.jccm.edu.comunicaciones.application.services.mensajes;

import java.util.List;

import es.jccm.edu.shared.application.ports.out.resttemplate.comunica.ClientComunicaRestTemplatePortOut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import es.jccm.edu.comunicaciones.application.ports.in.mensajes.IAdjuntosMensajesService;

@Service
public class AdjuntosMensajesService implements IAdjuntosMensajesService {
	
	private static final Logger LOG = LogManager.getLogger(AdjuntosMensajesService.class);

	@Autowired
	private ClientComunicaRestTemplatePortOut clientComunicaRestTemplatePortOut;

	@Value("${comunica.api.path}")
	private String comunicaApiUrl;
	
	public byte[] getFicheroAdjuntoMensaje(String idAdjunto) {
		
		LOG.info("Obteniendo fichero adjunto con idAdjunto = {}", idAdjunto);

		byte[] fichero = null;
		
		if(idAdjunto != null) {
			
			var builder = 
					UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl+"adjunto/mensaje/");
			
			builder.pathSegment(idAdjunto);
			
			try {
				ResponseEntity<byte[]> response = 
						clientComunicaRestTemplatePortOut.getForEntity(builder.toUriString(), byte[].class);
					
				fichero = response.getBody();
				
			} catch (RestClientException e) {
				LOG.error("error comunicación servicio rest obteniendo fichero con idAdjunto = {}", idAdjunto, e);
				throw e;
			}
			
		}
		
		return fichero;
	}
	
	public byte[] getFicherosAdjuntosZIPMensaje(List<String> idsAdjuntos) {
		
		LOG.info("Obteniendo fichero zip con idsAdjuntos = {}", idsAdjuntos);

		byte[] fichero = null;
		
		if(idsAdjuntos != null && !idsAdjuntos.isEmpty()) {
			
			var builder = 
					UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl+"adjunto/all/mensaje");
			
			try {
				ResponseEntity<byte[]> response = 
						clientComunicaRestTemplatePortOut.postForEntity(builder.toUriString(), idsAdjuntos, byte[].class);
					
				fichero = response.getBody();
				
			} catch (RestClientException e) {
				LOG.error("error comunicación servicio rest obteniendo fichero zip con idsAdjuntos = {}", idsAdjuntos, e);
				throw e;
			}
			
		}
		
		return fichero;
	}

}

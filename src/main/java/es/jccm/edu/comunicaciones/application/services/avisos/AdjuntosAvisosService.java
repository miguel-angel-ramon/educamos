package es.jccm.edu.comunicaciones.application.services.avisos;

import java.util.Arrays;
import java.util.List;

import es.jccm.edu.shared.application.ports.out.resttemplate.comunica.ClientComunicaRestTemplatePortOut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import es.jccm.edu.comunicaciones.adapter.in.rest.avisos.model.AvisoAdjuntosDto;
import es.jccm.edu.comunicaciones.application.ports.in.avisos.IAdjuntosAvisosService;

@Service
public class AdjuntosAvisosService implements IAdjuntosAvisosService {

	private static final Logger LOG = LogManager.getLogger(AdjuntosAvisosService.class);

	@Autowired
	private ClientComunicaRestTemplatePortOut clientComunicaRestTemplatePortOut;

	@Value("${comunica.api.path}")
	private String comunicaApiUrl;

	public List<AvisoAdjuntosDto> getListaFicherosAdjuntoAviso(String idAviso) {

		LOG.info("Obteniendo lista de ficheros zip con idsAdjuntos = {}", idAviso);

		List<AvisoAdjuntosDto> avisoAdjuntos;
		var builder = UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl + "aviso/adjuntos/");

		if (idAviso != null) {
			builder.pathSegment(idAviso);
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("endpoint comunica = {} ", builder.toUriString());
		}

		try {
			ResponseEntity<AvisoAdjuntosDto[]> response = clientComunicaRestTemplatePortOut.getForEntity(builder.toUriString(),
					AvisoAdjuntosDto[].class);

			avisoAdjuntos = Arrays.asList(response.getBody());
		} catch (RestClientException e) {
			LOG.error("error comunicación servicio rest que suministra avisos", e);
			throw e;
		}

		return avisoAdjuntos;
	}

	public byte[] getFicheroAdjuntoAviso(String idAdjunto) {

		LOG.info("Obteniendo fichero adjunto con idAdjunto = {}", idAdjunto);

		byte[] fichero = null;

		if (idAdjunto != null) {

			var builder = UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl + "adjunto/aviso/");

			builder.pathSegment(idAdjunto);

			try {
				ResponseEntity<byte[]> response = clientComunicaRestTemplatePortOut.getForEntity(builder.toUriString(), byte[].class);

				fichero = response.getBody();

			} catch (RestClientException e) {
				LOG.error("error comunicación servicio rest obteniendo fichero con idAdjunto = {} ", idAdjunto, e);
				throw e;
			}

		}

		return fichero;
	}

	public byte[] getFicherosAdjuntosZIPAviso(List<String> idsAdjuntos) {

		LOG.info("Obteniendo fichero zip con idsAdjuntos = {}", idsAdjuntos);

		byte[] fichero = null;

		if (idsAdjuntos != null && !idsAdjuntos.isEmpty()) {

			var builder = UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl + "adjunto/all/aviso");

			try {
				ResponseEntity<byte[]> response = clientComunicaRestTemplatePortOut.postForEntity(builder.toUriString(), idsAdjuntos,
						byte[].class);

				fichero = response.getBody();

			} catch (RestClientException e) {
				LOG.error("error comunicación servicio rest obteniendo fichero zip con idsAdjuntos = {}", idsAdjuntos,
						e);
				throw e;
			}

		}

		return fichero;
	}

}

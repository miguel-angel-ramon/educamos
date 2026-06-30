package es.jccm.edu.comunicaciones.application.services.mensajes;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.util.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model.ColectivoDto;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model.MiembrosColectivoDto;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.ColectivosProjection;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.GrupoDeAlumnosProjection;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.MiembrosColectivosProjection;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.OfertaMatriculaProjection;
import es.jccm.edu.comunicaciones.adapter.out.repositories.mensajes.MensajeRepository;
import es.jccm.edu.comunicaciones.application.domain.mensajes.Adjunto;
import es.jccm.edu.comunicaciones.application.domain.mensajes.Mensaje;
import es.jccm.edu.comunicaciones.application.domain.mensajes.MensajeDestinatario;
import es.jccm.edu.comunicaciones.application.domain.mensajes.MensajeDetalle;
import es.jccm.edu.comunicaciones.application.domain.mensajes.Miembro;
import es.jccm.edu.comunicaciones.application.domain.mensajes.projection.MensajeProjection;
import es.jccm.edu.comunicaciones.application.ports.in.mensajes.IMensajesService;
import es.jccm.edu.shared.application.ports.out.resttemplate.comunica.ClientComunicaRestTemplatePortOut;

@Service
public class MensajesService implements IMensajesService {

	private static final String F_ENVIDO = "F_ENVIDO";
	private static final Logger LOG = LogManager.getLogger(MensajesService.class);
	@Autowired
	private ClientComunicaRestTemplatePortOut clientComunicaRestTemplatePortOut;

	@Value("${comunica.api.path}")
	private String comunicaApiUrl;

	@Autowired
	private MensajeRepository mensajeRepository;
	
	@Autowired
	private MensajesServiceDataSource mensajesServiceDataSource;


	@Autowired
	private ModelMapper modelMapper;

	private static final String CHAR_S = "S";


	
	public Page<Mensaje> getMensajesRecibidos(String idUsuario, int page, int numItems, int idColectivo, String leido) {

		LOG.info("Obteniendo mensajes recibidos del usuario = {}", idUsuario);

		Page<Mensaje> mensajesOut;

		try {
			Pageable paging = PageRequest.of(page, numItems, Sort.by(F_ENVIDO).descending());

			Page<MensajeProjection> mensajesRecibidos = mensajeRepository
					.getMensajesRecibidos(idUsuario, idColectivo, leido, paging);

			mensajesOut = mensajesRecibidos.map(mensaje -> modelMapper.map(mensaje, Mensaje.class));
			
			for (Mensaje mens : mensajesOut.getContent()) {
				Long idRemitente = mens.getIdRemitente();
				if(idRemitente != null) {
					Blob img = mensajeRepository.getFotoRemitente(idRemitente);
					try {
						if (img != null) {
							mens.setFotoRemitente(img.getBytes(1, (int) img.length()));
						}
					} catch (SQLException e) {
					}
				}
			}
		} catch (Exception e) {
			LOG.error("error comunicación servicio rest que suministra mensajes recibidos", e);
			throw e;
		}

		return mensajesOut;
	}

	public Page<Mensaje> getMensajesEnviados(String idUsuario, int page, int numItems, int idColectivo, String leido, Long xUsuario) {
		
		LOG.info("Obteniendo mensajes enviados del usuario = {}", idUsuario);
		
		Page<Mensaje> mensajesOut;

		try {
			Pageable paging = PageRequest.of(page, numItems, Sort.by(F_ENVIDO).descending());

			Page<MensajeProjection> mensajesEnviados = mensajeRepository
					.getMensajesEnviados(idUsuario, idColectivo, leido, paging);

			mensajesOut = mensajesEnviados.map(mensaje -> modelMapper.map(mensaje, Mensaje.class));
			
			for (Mensaje mens : mensajesOut.getContent()) {
				Blob img = mensajeRepository.getFotoRemitente(xUsuario);
				try {
					if (img != null) {
						mens.setFotoRemitente(img.getBytes(1, (int) img.length()));
					}
				} catch (SQLException e) {
				}
			}
		} catch (Exception e) {
			LOG.error("error comunicación servicio rest que suministra mensajes enviados", e);
			throw e;
		}

		return mensajesOut;
	}


	public MensajeDetalle getDetalleMensaje(String idUsuario, String idMensaje, Boolean enviado) throws SQLException {

		LOG.info("Obteniendo el detalle del mensaje = {} del usuario = {}", idMensaje, idUsuario);

		MensajeDetalle mensajeDetalle;

		var builder = UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl + "mensaje/");

		if (Boolean.TRUE.equals(enviado)) {
			builder.path("enviado/");
		}

		if (idMensaje != null && idUsuario != null) {
			builder.pathSegment(idMensaje);
			builder.pathSegment(idUsuario);
		}

		try {
			ResponseEntity<MensajeDetalle> response = clientComunicaRestTemplatePortOut.getForEntity(builder.toUriString(),
					MensajeDetalle.class);

			mensajeDetalle = response.getBody();

		} catch (RestClientException e) {
			LOG.error("error comunicación servicio rest que suministra el detalle del mensaje", e);
			e.printStackTrace();
			throw e;
		}

		if (mensajeDetalle != null && mensajeDetalle.getFicherosAdjuntos() != null) {
			rellenarDatosFicherosAdjuntos(mensajeDetalle.getFicherosAdjuntos());
		}

		return mensajeDetalle;
	}

	private void rellenarDatosFicherosAdjuntos(List<Adjunto> adjuntos) throws SQLException {
		Blob blob;
		for (Adjunto adjunto : adjuntos) {
			if (adjunto.getId() != null){
				blob = mensajeRepository.getDatosAdjuntosById(adjunto.getId());
				if (blob != null) {
					byte[] bytes = blob.getBytes(1, (int)blob.length());
					blob.free();
					adjunto.setDatos(bytes);
				}
			}
		}
	}

	public void responderMensaje(String idUsuario, MensajeDetalle mensaje) {

		LOG.info("Respondiendo mensaje del usuario = {}", idUsuario);

		UriComponentsBuilder builder;

		if (mensaje != null && mensaje.getResponderTodos() != null && mensaje.getResponderTodos().equals(CHAR_S)) {
			builder = UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl + "mensaje/responder/todos");
		} else {
			builder = UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl + "mensaje/responder");
		}

		builder.pathSegment(idUsuario);

		try {

			//comunicaClient.postForEntity(builder.toUriString(), mensaje, MensajeDetalle.class);

			clientComunicaRestTemplatePortOut.postForEntity(builder.toUriString(), mensaje, MensajeDetalle.class);

		} catch (RestClientException e) {
			String idMensaje = mensaje != null && mensaje.getIdMensajeOrigen() != null
					? mensaje.getIdMensajeOrigen().toString()
					: "";
			LOG.error("error comunicación servicio rest respondiendo mensaje id = {}", idMensaje, e);
			throw e;
		}
	}

	public void enviarMensaje(String idUsuario, MensajeDetalle mensaje, String idCentro, String idMatricula) {

		LOG.info("Enviando nuevo mensaje del usuario = {}", idUsuario);

		UriComponentsBuilder builder;

		builder = UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl + "mensaje/enviar");

		builder.pathSegment(idCentro);
		builder.pathSegment(idMatricula);
		builder.pathSegment(idUsuario);
		System.out.println(builder.toUriString() + "AYUDA");

		try {

			clientComunicaRestTemplatePortOut.postForEntity(builder.toUriString(), mensaje, MensajeDetalle.class);

		} catch (RestClientException e) {
			LOG.error("error comunicación servicio rest enviando nuevo mensaje", e);
			throw e;
		}
	}

	public void postEliminarMensajes(String idUsuario, List<Mensaje> mensajes, String tipoEliminacion) {

		LOG.info("Eliminado mensajes para el usuario = {} | mensajes = {}", idUsuario, mensajes);

		UriComponentsBuilder builder;
		String urlToBuild = this.comunicaApiUrl;

		switch (tipoEliminacion) {
		case "enviados":
			urlToBuild += "mensaje/borrar/enviados";
			break;

		case "recibidos":
			urlToBuild += "mensaje/borrar/recibidos";
			break;

		case "archivados":
			urlToBuild += "mensaje/borrar/archivado/definitivo";
			break;

		default:
			urlToBuild += "mensaje/borrar/archivado/definitivo";
			break;
		}

		builder = UriComponentsBuilder.fromHttpUrl(urlToBuild);

		if (idUsuario != null) {
			builder.pathSegment(idUsuario);
		}

		try {

			clientComunicaRestTemplatePortOut.postForEntity(builder.toUriString(), mensajes, Mensaje[].class);

		} catch (RestClientException e) {
			LOG.error(builder.toUriString());
			LOG.error("error comunicación servicio rest eliminar mensajes por tipos", e);
			throw e;
		}
	}

	public void setMensajeLeido(String idUsuario, String idDestinatarioMensaje) {

		LOG.info("Marcando el mensaje con idDestinatarioMensaje = {} del usuario = {} como leído", idDestinatarioMensaje, idUsuario);

		if (idDestinatarioMensaje != null && !idDestinatarioMensaje.isEmpty() && idUsuario != null
				&& !idUsuario.isEmpty()) {

			var builder = UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl + "mensaje/leido");

			builder.pathSegment(idDestinatarioMensaje);
			builder.pathSegment(idUsuario);

			try {

				clientComunicaRestTemplatePortOut.postForEntity(builder.toUriString(), null, Mensaje.class);

			} catch (RestClientException e) {
				LOG.error("error comunicación servicio rest marcando mensaje como leido idDestinatarioMensaje = {}",
						idDestinatarioMensaje, e);
				throw e;
			}

		}

	}
	
	public List<ColectivoDto> getColectivos(Long xPerfil) {
		
		List<ColectivoDto> colectivosD = new ArrayList<>();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt=(Jwt)auth.getPrincipal();

        Long idUsuarioComunica = Long.parseLong(jwt.getClaim("idUsuarioComunica").toString());

		List <ColectivosProjection> colectivosP = mensajeRepository.getColectivosUsuario(idUsuarioComunica, xPerfil);

		for (ColectivosProjection colectivo : colectivosP) {
			ColectivoDto colectivoD = new ColectivoDto();
			colectivoD.setId(colectivo.getId());
			colectivoD.setCodigoColectivo(colectivo.getCodigoColectivo());
			colectivoD.setDescripcionCortaColectivo(colectivo.getDescripcionCortaColectivo());
			colectivoD.setDescripcionLargaColectivo(colectivo.getDescripcionLargaColectivo());
			colectivoD.setPermiteFiltrar(colectivo.getPermiteFiltrar());
			colectivoD.setPermiteRespuesta(colectivo.getPermiteRespuesta());
			colectivoD.setXPerfil(colectivo.getXPerfil());
			
			colectivosD.add(colectivoD);		
		}

		return colectivosD;
	}
	
	public List<Miembro> getMiembrosColectivo(MiembrosColectivoDto datos){
				
		 String query = getQueryMiembrosColectivo(datos);
		
		 if (!query.isEmpty()) {
			 LOG.debug("QUERY: " + query);
			try {
		        return new ArrayList<>(mensajesServiceDataSource.ejecutarQuery(query).stream()
		                .collect(Collectors.toMap(Miembro::getIndice, obj -> obj, (existente, nuevo) -> existente))
		                .values()).stream()
		        	    .sorted(Comparator.comparing(Miembro::getDescripcion))
		        	    .collect(Collectors.toList());
			} catch (Exception e) {
				LOG.error("Error obteniendo los miembros del colectivo: "+datos.getXColectivo());
			}
		 }else {
			 LOG.debug("La query viene vacía");
		 }
		 return new ArrayList<>();
	}
	
	private List<MiembrosColectivosProjection> getMiembrosColectivoProjection(MiembrosColectivoDto datos) {
		List<MiembrosColectivosProjection> miembrosColectivo = mensajeRepository.getMiembrosColectivo(datos.getXColectivo());
		;
		if(miembrosColectivo == null || miembrosColectivo.isEmpty()) {
			
			LOG.debug("DATOS VACÍOS XCOLECTIVO: "+ datos.getXColectivo() + "XUSUARIO: " +datos.getXUsuario());
			return new ArrayList<>();
		}
		return miembrosColectivo;
	}
	
	private String getQueryMiembrosColectivo(MiembrosColectivoDto datos) {
		List<MiembrosColectivosProjection> miembrosColectivo = getMiembrosColectivoProjection(datos);
		if(miembrosColectivo.isEmpty()) {
			LOG.debug("Miembros colectivo es vacío");
			return "";
		}
		String query = miembrosColectivo.get(0).getTSentencia();
		if(miembrosColectivo.get(0).getTSentenciaContinua() != null && miembrosColectivo.get(0).getTSentenciaContinua().length() > 0) {
			query += miembrosColectivo.get(0).getTSentenciaContinua();
		}
		String[] replacements = miembrosColectivo.stream()
			    .map(MiembrosColectivosProjection::getCTipParmEnCol)
			    .toArray(String[]::new);
		 Map<String, String> campoAColumna = new HashMap<>();
	        campoAColumna.put("xColectivo", "X_COLECTIVO");
	        campoAColumna.put("xUnidadResCen", "X_UNIDAD_RES_CEN");
	        campoAColumna.put("xAmpcen", "X_AMPCEN");
	        campoAColumna.put("codCentro", "COD_CENTRO");
	        campoAColumna.put("fTomaPos", "F_TOMA_POS");
	        campoAColumna.put("xCentro", "X_CENTRO");
	        campoAColumna.put("xEmpleado", "X_EMPLEADO");
	        campoAColumna.put("xPerfil", "X_PERFIL");
	        campoAColumna.put("xUsuario", "X_USUARIO");
	        campoAColumna.put("xUsuOperacion", "X_USU_OPERACION");
	        campoAColumna.put("xMatAlu", "X_MAT_ALU");
	        campoAColumna.put("xUnidad", "X_UNIDAD");
	        campoAColumna.put("xGruactproaluSelec", "X_GRUACTPROALU_SELEC");
	        campoAColumna.put("xGruactproaluUsu", "X_GRUACTPROALU_USU");
	        campoAColumna.put("xAmpcenResAmpa", "X_AMPCEN_RES_AMPA");
	        campoAColumna.put("cAnno", "C_ANNO");
	        campoAColumna.put("xDepartcen", "X_DEPARTCEN");
	        campoAColumna.put("xUnidadTutorAlu", "X_UNIDADTUTORALU");

		for (String rep : replacements) {
			String clave = obtenerClavePorValor(campoAColumna, rep);			
		    query = query.replaceFirst("\\?",  obtenerValorDelCampo(datos, clave));
		}
		return query;	
	}
	
	  public static String obtenerValorDelCampo(Object objeto, String nombreCampo) {
	        try {
	            // Obtener el campo a partir del nombre
	            Field field = objeto.getClass().getDeclaredField(nombreCampo);
	            field.setAccessible(true);  // Asegurarse de que el campo sea accesible

	            // Obtener el valor del campo
	            return String.valueOf( field.get(objeto));
	        } catch (NoSuchFieldException | IllegalAccessException e) {
	            e.printStackTrace();
	            return null;  // Si el campo no existe o hay un error
	        }
	    }
	
	 public static String obtenerClavePorValor(Map<String, String> map, String valor) {
	        for (Map.Entry<String, String> entry : map.entrySet()) {
	            if (entry.getValue().equals(valor)) {
	                return entry.getKey(); // Devolver la clave correspondiente
	            }
	        }
	        return null; // Si no se encuentra el valor
	    }
	
	private static class MiembrosRowMapper implements RowMapper<Miembro> {
	    @Override
	    public Miembro mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Miembro miembro = new Miembro();
	        miembro.setIndice(rs.getString("indice"));
	        miembro.setDescripcion(rs.getString("descripcion"));	     
	        return miembro;
	    }
	}

	
	public Page<Mensaje> getMensajesArchivados(String idUsuario, int page, int numItems, String idColectivo, String leido){
		
		Page<Mensaje> mensajesOut;
		try {
			Pageable paging = PageRequest.of(page, numItems, Sort.by(F_ENVIDO).descending());
			Page<MensajeProjection> mensajesArchivados = mensajeRepository.getMensajesArchivados(idUsuario, idColectivo, leido, paging);
				
			
			mensajesOut = mensajesArchivados.map(mensaje -> modelMapper.map(mensaje, Mensaje.class));
			
			for (Mensaje mens : mensajesOut.getContent()) {
				Long idRemitente = mens.getIdRemitente();
				if(idRemitente != null) {
					Blob img = mensajeRepository.getFotoRemitente(idRemitente);
					try {
						if (img != null) {
							mens.setFotoRemitente(img.getBytes(1, (int) img.length()));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			LOG.error("error comunicación servicio rest que suministra mensajes recibidos", e);
			e.printStackTrace();
			throw e;
		}

		return mensajesOut;
		
	}
	
	public void postArchivarMensajesRecibidos(String idUsuario, List<Mensaje> mensajes) {
		
		LOG.info("Archivando mensajes recibidos para el usuario = {} | mensajes = {}", idUsuario, mensajes);

		var builder = UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl + "mensaje/archivar/recibidos");
		
		if (idUsuario != null) {
			builder.pathSegment(idUsuario);
		}

		try {

			clientComunicaRestTemplatePortOut.postForEntity(builder.toUriString(), mensajes, Mensaje[].class);

		} catch (RestClientException e) {
			LOG.error(builder.toUriString());
			LOG.error("error comunicación servicio rest archivar mensajes recibidos", e);
			throw e;
		}
		
	}
	
	public void postRestaurarMensajesArchivados(String idUsuario, List<Mensaje> mensajes) {
		
		LOG.info("Restaurando mensajes archivados para el usuario = {} | mensajes = {}", idUsuario, mensajes);

		var builder = UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl + "mensaje/restaurar/archivados");
		
		if (idUsuario != null) {
			builder.pathSegment(idUsuario);
		}

		try {

			clientComunicaRestTemplatePortOut.postForEntity(builder.toUriString(), mensajes, Mensaje[].class);

		} catch (RestClientException e) {
			LOG.error(builder.toUriString());
			LOG.error("error comunicación servicio rest restaurar mensajes archivados", e);
			throw e;
		}
		
	}
	
	public List<MensajeDestinatario> getDestinatariosMensajeEnviado(String idUsuario, String idMensaje) {
		
		LOG.info("Obteniendo destinatarios del mensaje enviado = {} por el usuario = {}", idMensaje, idUsuario);
		
		List<MensajeDestinatario> destinatarios;
		var builder = UriComponentsBuilder.fromHttpUrl(this.comunicaApiUrl + "mensaje/destinatarios/");
		
		if (idMensaje != null && idUsuario != null) {
			builder.pathSegment(idMensaje);
			builder.pathSegment(idUsuario);
		}
		
		try {
			
			ResponseEntity<MensajeDestinatario[]> response = clientComunicaRestTemplatePortOut.getForEntity(builder.toUriString(), MensajeDestinatario[].class);
			
			destinatarios = response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
			
		} catch (RestClientException e) {
			LOG.error("error comunicación servicio rest que suministra destinatarios de mensajes enviados", e);
//			e.printStackTrace();
			throw e;
		}
		
		return destinatarios;
		
	}
	
	public List<OfertaMatriculaProjection> getOfertaMatriculabyCodCentro(Long codigoCentro, Long anyo){
		try {
		    List<OfertaMatriculaProjection> result = mensajeRepository.getOfertaMatriculabyCodCentro(codigoCentro, anyo);

		    return result!= null? result : Collections.emptyList();
		}catch(RestClientException e) {
			LOG.error("error comunicación al obtener las matriculas", e);
		 return Collections.emptyList();
		
		}
	}
	
	public List<GrupoDeAlumnosProjection> getGrupoDeAlumnos(Long codigoCentro, Long anyo){
		try {
		    List<GrupoDeAlumnosProjection> result = mensajeRepository.getGrupoDeAlumnos(codigoCentro, anyo);

		    return result!= null? result : Collections.emptyList();
		}catch(RestClientException e) {
		 return Collections.emptyList();
		
		}
	}
	

}

package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.server.ResponseStatusException;

import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model.ColectivoDto;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model.MensajeDestinatarioDto;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model.MensajeDetalleDto;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model.MensajeDto;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model.MensajeEnviarDto;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model.MensajeMiembroColectivoDto;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model.MiembrosColectivoDto;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.GrupoDeAlumnosProjection;
import es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.projection.OfertaMatriculaProjection;
import es.jccm.edu.comunicaciones.application.domain.mensajes.Mensaje;
import es.jccm.edu.comunicaciones.application.domain.mensajes.MensajeDetalle;
import es.jccm.edu.comunicaciones.application.ports.in.mensajes.IMensajesService;
import es.jccm.edu.comunicaciones.application.ports.in.restablecerClaveUseCase.RestablecerClaveException;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/comunicaciones")
@Tag(name = "Servicio Mensajes Escritorio", description = "Servicio para recuperar el módulo de mensajes del escritorio")

@Slf4j
public class MensajesRestController {

	@Autowired
	private IMensajesService mensajesService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	private ModelMapper modelMapper;

	private static final String CHAR = "[";

	/**
	 * Conecta con el proyecto de comunica y trae todos los mensajes recibidos de un
	 * usuario.
	 *
	 * @param String idUsuario
	 * @return List<MensajeDto>
	 * @throws UnsupportedEncodingException 
	 */
	////@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Recupera mensajes recibidos", description = "Este metodo devuelve un objeto List con todos los mensajes recibidos por el usuario introducido", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/mensajes/recibidos")
	public ResponseEntity<Page<MensajeDto>> getMensajesRecibidos(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("page") int page, @RequestParam("numItems") int numItems, 
			@RequestParam("idColectivo") int idColectivo, @RequestParam("leido") String leido) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		
		Page<Mensaje> mensajes = mensajesService.getMensajesRecibidos(datosUsuario.getXUsuarioComunica().toString(), page, numItems, idColectivo, leido);
		List<MensajeDto> mensajesOut = mensajes.getContent().stream()
				.map(mensaje -> modelMapper.map(mensaje, MensajeDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(
				new PageImpl<>(mensajesOut, mensajes.getPageable(), mensajes.getTotalElements()),
				HttpStatus.OK);
	}

	/**
	 * Conecta con el proyecto de comunica y trae todos los mensajes enviados de un
	 * usuario.
	 *
	 * @param String idUsuario
	 * @return List<MensajeDto>
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Recupera mensajes enviados", description = "Este metodo devuelve un objeto List con todos los mensajes enviados por el usuario introducido", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/mensajes/enviados")
	public ResponseEntity<Page<MensajeDto>> getMensajesEnviados(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("page") int page, @RequestParam("numItems") int numItems,
			@RequestParam("idColectivo") int idColectivo, @RequestParam("leido") String leido) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Page<Mensaje> mensajes = mensajesService.getMensajesEnviados(datosUsuario.getXUsuarioComunica().toString(), page, numItems, idColectivo, leido, datosUsuario.getXUsuarioComunica());
		List<MensajeDto> mensajesOut = mensajes.getContent().stream()
				.map(mensaje -> modelMapper.map(mensaje, MensajeDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(
				new PageImpl<>(mensajesOut, mensajes.getPageable(), mensajes.getTotalElements()),
				HttpStatus.OK);
	}

	/**
	 * Conecta con el proyecto de comunica y recupera más detalles y contenido de un
	 * mensaje.
	 *
	 * @param String  idUsuario
	 * @param String  idMensaje
	 * @param Boolean enviado
	 * @return MensajeDetalleDto
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Recupera detalles de mensajes recibidos", description = "Este metodo devuelve un objeto List con todos los mensajes recibidos por el usuario introducido", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/mensajes/{idMensaje}")
	public ResponseEntity<MensajeDetalleDto> getDetalleMensaje(@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@PathVariable("idMensaje") String idMensaje, @RequestParam("enviado") Boolean enviado) throws SQLException {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		MensajeDetalleDto mensajeDetalleOut = modelMapper
				.map(mensajesService.getDetalleMensaje(datosUsuario.getUsuarioComunica(), idMensaje, enviado), MensajeDetalleDto.class);

		return new ResponseEntity<>(mensajeDetalleOut, HttpStatus.OK);
	}

	/**
	 * Conecta con el proyecto de comunica y envia un mensaje de respuesta a uno
	 * recibido previamente.
	 *
	 * @param String            idUsuario
	 * @param MensajeDetalleDto mensaje
	 * @return HttpStatus
	 * @throws UnsupportedEncodingException 
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Responder mensaje", description = "Este metodo envía un mensaje de respuseta a uno previamente recibido", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/mensajes/responder")
	public HttpStatus responderMensaje(@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@RequestBody MensajeDetalleDto mensaje) {

		HttpStatus status;
		String message;

		try {
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			
			MensajeDetalle mensajeIn = modelMapper.map(mensaje, MensajeDetalle.class);

			mensajesService.responderMensaje(datosUsuario.getUsuarioComunica(), mensajeIn);
			status = HttpStatus.OK;
		} catch (RestablecerClaveException e) {
			log.error("error al responder mensaje id = " + mensaje.getId().toString(), e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			throw new ResponseStatusException(status, "error al responder mensaje id = " + mensaje.getId().toString());
		} catch (HttpClientErrorException e) {
			status = HttpStatus.valueOf(e.getRawStatusCode());
			message = e.getMessage() != null ? getErrorMessage(e.getMessage()) : "";
			log.error(message, status);
			throw new ResponseStatusException(status, message);
		}

		return status;
	}

	/**
	 * Conecta con el proyecto de comunica y envia un mensaje nuevo.
	 *
	 * @param String            idUsuario
	 * @param MensajeDetalleDto mensaje
	 * @param String            idCentro
	 * @param String            idMatricula
	 * @return HttpStatus
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Enviar mensaje", description = "Este metodo envía un mensaje nuevo", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/mensajes/enviar/{idCentro}/{idMatricula}")
    public HttpStatus enviarMensaje(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestBody MensajeEnviarDto mensaje,
			@PathVariable String idCentro, @PathVariable String idMatricula) {

		HttpStatus status;
		String message;

		try {
			
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			
			MensajeDetalle mensajeIn = modelMapper.map(mensaje, MensajeDetalle.class);

			mensajesService.enviarMensaje(datosUsuario.getUsuarioComunica(), mensajeIn, idCentro, idMatricula);
			status = HttpStatus.OK;
		} catch (HttpClientErrorException e) {
			status = HttpStatus.valueOf(e.getRawStatusCode());
			if(e.getMessage() != null) {
				message = getErrorMessage(e.getMessage());
			} else {
				message = "Error al intentar enviar el mensaje";
			}
			log.error(message, status);
			throw new ResponseStatusException(status, message);
		}

		return status;
	}

	/**
	 * Conecta con el proyecto de comunica y elimina los mensajes recibidos.
	 *
	 * @param String     idUsuario
	 * @param MensajeDto mensajes
	 * @param String     tipoEliminacion (enviados | recibidos | archivados)
	 * @return HttpStatus
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Eliminar mensajes", description = "Este metodo envía los mensajes recibidos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/mensajes/borrar/{tipoEliminacion}")
	public void postEliminarMensajes(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestBody List<MensajeDto> mensajes,
			@PathVariable String tipoEliminacion) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<Mensaje> mensajesIn = mensajes.stream().map(mensaje -> modelMapper.map(mensaje, Mensaje.class))
				.collect(Collectors.toList());

		mensajesService.postEliminarMensajes(datosUsuario.getUsuarioComunica(), mensajesIn, tipoEliminacion);
	}

	/**
	 * Conecta con el proyecto de comunica y marca un mensaje como leído.
	 *
	 * @param String idUsuario
	 * @param String idDestinatarioMensaje
	 * @return HttpStatus
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Marcar mensaje como leído", description = "Este método marca un mensaje como leído", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/mensaje/leido/{idDestinatarioMensaje}")
	public HttpStatus setMensajeLeido(@RequestHeader(Constants.AUTHORIZATION) String jwt,
			@PathVariable("idDestinatarioMensaje") String idDestinatarioMensaje) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		HttpStatus status;

		if (idDestinatarioMensaje != null && !idDestinatarioMensaje.isEmpty() && datosUsuario.getUsuarioComunica() != null
				&& !datosUsuario.getUsuarioComunica().isEmpty()) {

			try {
				mensajesService.setMensajeLeido(datosUsuario.getUsuarioComunica(), idDestinatarioMensaje);

				status = HttpStatus.OK;
			} catch (RestablecerClaveException e) {
				log.error("error al marcar mensaje como leido idDestinatarioMensaje = " + idDestinatarioMensaje, e);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				throw new ResponseStatusException(status, "error al marcar mensaje como leido");
			} catch (HttpClientErrorException e) {
				status = HttpStatus.valueOf(e.getRawStatusCode());
				log.error(e.getMessage(), status);
				throw new ResponseStatusException(status, e.getMessage());
			}

		} else {
			status = HttpStatus.BAD_REQUEST;
			throw new ResponseStatusException(status, "idDestinatarioMensaje e idUsuario son obligatorios");
		}

		return status;
	}
	
	/**
	 * Trae todos los colectivos a los que
	 * pertenece un usuario.
	 *
	 * @param String idUsuario
	 * @return List<MensajeColectivoDto>
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Recupera colectivos", description = "Este metodo devuelve un objeto List con todos los colectivos a los que pertenece el usuario introducido", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/mensaje/colectivos")
	//public ResponseEntity<List<ColectivoDto>> getColectivos(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
	public ResponseEntity<List<ColectivoDto>> getColectivos(@RequestParam Long xPerfil) {	
		//DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<ColectivoDto> colectivosOut = mensajesService.getColectivos(xPerfil);

		return new ResponseEntity<>(colectivosOut, HttpStatus.OK);
		
	}
	
	/**
	 * Conecta con el proyecto de comunica y trae todos los miembros
	 * de un colectivo.
	 *
	 * @param String idCentro
	 * @param String idMatricula
	 * @param String idGrupo
	 * @param String idUsuario
	 * @return List<MensajeMiembroColectivoDto>
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Recupera miembros colectivo", description = "Este metodo devuelve un objeto List con todos los miembros del colectivo introducido", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/mensaje/colectivo/miembros")
	public ResponseEntity<List<MensajeMiembroColectivoDto>> getMiembrosColectivo(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestBody MiembrosColectivoDto datos) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<MensajeMiembroColectivoDto> miembrosColectivoOut = mensajesService.getMiembrosColectivo(datos).stream()
				.map(miembroColectivo -> modelMapper.map(miembroColectivo, MensajeMiembroColectivoDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(miembrosColectivoOut, HttpStatus.OK);
		
	}
	
	/**
	 * Conecta con el proyecto de comunica y trae todos los mensajes archivados
	 * de un usuario y opcionalmente de un colectivo.
	 * 
	 * @param String idUsuario
	 * @param idColectivo
	 * @return
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Recupera mensajes archivados", description = "Este metodo devuelve un objeto List con todos los mensajes archivados del usario y/o del colectivo introducido", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/mensajes/archivados")
	public ResponseEntity<Page<MensajeDto>> getMensajesArchivados(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("page") int page, @RequestParam("numItems") int numItems,
			@RequestParam("idColectivo") String idColectivo, @RequestParam("leido") String leido){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Page<Mensaje> mensajes = mensajesService.getMensajesArchivados(datosUsuario.getUsuarioComunica(), page, numItems, idColectivo, leido);
		List<MensajeDto> mensajesOut = mensajes.getContent().stream()
				.map(mensaje -> modelMapper.map(mensaje, MensajeDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(
				new PageImpl<>(mensajesOut, mensajes.getPageable(), mensajes.getTotalElements()),
				HttpStatus.OK);
		
	}
	
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Archivar mensajes recibidos", description = "Este metodo archiva los mensajes recibidos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/mensajes/archivar/recibidos")
	public void postArchivarMensajesRecibidos(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestBody List<MensajeDto> mensajes) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<Mensaje> mensajesIn = mensajes.stream().map(mensaje -> modelMapper.map(mensaje, Mensaje.class))
				.collect(Collectors.toList());
		
		mensajesService.postArchivarMensajesRecibidos(datosUsuario.getUsuarioComunica(), mensajesIn);
	}
	
	/**
	 * Conecta con el proyecto de comunica y restaura los mensajes archivados.
	 *
	 * @param String     idUsuario
	 * @param MensajeDto mensajes
	 * @return HttpStatus
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Restaurar mensajes archivados", description = "Este metodo restaura los mensajes archivados", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/mensajes/restaurar/archivados")
	public void postRestaurarMensajesArchivados(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestBody List<MensajeDto> mensajes) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<Mensaje> mensajesIn = mensajes.stream().map(mensaje -> modelMapper.map(mensaje, Mensaje.class))
				.collect(Collectors.toList());
		
		mensajesService.postRestaurarMensajesArchivados(datosUsuario.getUsuarioComunica(), mensajesIn);
		
	}
	
	/**
	 * Conecta con el proyecto de comunica y trae todos los destinatarios de un mensaje
	 * enviado por un usuario.
	 * 
	 * @param String idUsuario
	 * @param String idMensaje
	 * @return List<MensajeDestinatarioDto>
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Recupera destinatarios del mensaje enviado", description = "Este metodo devuelve un objeto List con todos los destinatarios del mensaje enviado introducido", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/mensaje/destinatarios")
	public ResponseEntity<List<MensajeDestinatarioDto>> getDestinatariosMensajeEnviado(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam String idMensaje){
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<MensajeDestinatarioDto> destinatariosOut = mensajesService.getDestinatariosMensajeEnviado(datosUsuario.getUsuarioComunica(), idMensaje).stream()
				.map(destinatario -> modelMapper.map(destinatario, MensajeDestinatarioDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(destinatariosOut, HttpStatus.OK);
		
	}

	private static String getErrorMessage(String message) {
		if (message.contains(CHAR)) {
			int charPosition = message.indexOf(CHAR) + 1;
			return message.substring(charPosition, message.length() - 1);
		} else {
			return message;
		}
	}

	/**
	 * Traemos la matriculas de los almnnos.
	 *
	 *
	 * @param Long codigoCentro
	 * @param Long anyo
	 * @return List<OfertaMatriculaDTO>
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Recupera las matriculas de los alumnos", description = "Este metodo devuelve un objeto List con todas las matriculas", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/mensaje/matriculas")
	public ResponseEntity<List<OfertaMatriculaProjection>> getOfertaMatriculabyCodCentro(@RequestParam Long codigoCentro, @RequestParam Long anyo){
		
		try {
			List<OfertaMatriculaProjection> matriculas = mensajesService.getOfertaMatriculabyCodCentro(codigoCentro, anyo);
			
			return new ResponseEntity<>(matriculas, HttpStatus.OK);
		
		  } catch (Exception e) {
			  e.getMessage();
	            return handleException(e);
	        }
		
		
	}
	
	/**
	 * Traemos los grupos de alumnos.
	 *
	 *
	 * @param Long codigoCentro
	 * @param Long anyo
	 * @return List<OfertaMatriculaDTO>
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Recupera los grupos de los alumnos", description = "Este metodo devuelve un objeto List con todas los grupos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/mensaje/grupoDeAlumnos")
	public ResponseEntity<List<GrupoDeAlumnosProjection>> getGruposDeAlumnos(@RequestParam Long codigoCentro, @RequestParam Long anyo){
		
		try {
			List<GrupoDeAlumnosProjection> matriculas = mensajesService.getGrupoDeAlumnos(codigoCentro, anyo);
			
			return new ResponseEntity<>(matriculas, HttpStatus.OK);
		
		  } catch (Exception e) {
			  e.getMessage();
	            return handleException(e);
	        }
	}

	// Método auxiliar para manejar excepciones
		private <T> ResponseEntity<T> handleException(Exception e) {
	        if (e instanceof Unauthorized) {
	        	//e.printStackTrace();            
	        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        } else if (e instanceof Forbidden) {
	        	//e.printStackTrace();   
	            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	        } else {
	        	//e.printStackTrace();   
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	
}
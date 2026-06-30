package es.jccm.edu.alumnos.adapter.in.rest.notificacionesFalta;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.alumnos.adapter.in.rest.BasePath;
import es.jccm.edu.alumnos.adapter.in.rest.notificacionesFalta.models.NotificationFaltaDto;
import es.jccm.edu.alumnos.application.ports.in.notificacionesFalta.INotificacionesFaltasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.AlumnosBasePath)
@Tag(name = "Servicio Notificaciones Alumnos Escritorio", description = "Servicio para recuperar las notificaciones de las faltas del módulo de alumnos del escritorio")
//@CrossOrigin
public class NotificacionFaltaRestController {

	@Autowired
	private INotificacionesFaltasService notificacionesFaltasService;
	
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Devuelve la cantidad de notificaciones de faltas de una clase de una
	 * asignatura concreta
	 *
	 * @param idMateria
	 * @param idUnidad
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Contador de notificaciones por asignatura", description = "Devuelve la cantidad de notificaciones de faltas de una clase de una asignatura concreta", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/countNotificacionesFaltasByAsignatura")
	public ResponseEntity<Long> getCountNotificacionFaltaByAsignatura(@RequestParam("idUnidad") Long idUnidad,
			@RequestParam("idMateria") Long idMateria) {
		return new ResponseEntity<>(notificacionesFaltasService.countNotificacionFaltaByAsignatura(idUnidad, idMateria),
				HttpStatus.OK);
	}
	
	
	/**
	 * Detalle notificacion
	 *
	 * @param idNotificacion
	 * @return the response entity
	 */
	@Operation(summary = "Contador de notificaciones por asignatura", description = "Devuelve la cantidad de notificaciones de faltas de una clase de una asignatura concreta", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDetailNotification")
	public ResponseEntity<NotificationFaltaDto> getDetailNotification(@RequestParam("idNotificacion") Long idNotificacion) {
		return new ResponseEntity<>(modelMapper.map(notificacionesFaltasService.getDetailNotification(idNotificacion), NotificationFaltaDto.class),
				HttpStatus.OK);
	}

}

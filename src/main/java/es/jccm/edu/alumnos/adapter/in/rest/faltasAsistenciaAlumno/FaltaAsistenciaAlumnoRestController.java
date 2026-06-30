package es.jccm.edu.alumnos.adapter.in.rest.faltasAsistenciaAlumno;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.alumnos.adapter.in.rest.BasePath;
import es.jccm.edu.alumnos.adapter.in.rest.faltasAsistenciaAlumno.model.ConvAlumnCentroDTO;
import es.jccm.edu.alumnos.adapter.in.rest.faltasAsistenciaAlumno.model.FaltaAlumnoCentroDto;
import es.jccm.edu.alumnos.adapter.in.rest.faltasAsistenciaAlumno.model.FaltaAsistenciaAlumnoDto;
import es.jccm.edu.alumnos.adapter.in.rest.faltasAsistenciaAlumno.model.MotivoJustificacionDto;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.ConvAlumnCentro;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.FaltaAlumnoCentro;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.FaltaAsistenciaAlumno;
import es.jccm.edu.alumnos.application.domain.faltasAsistenciaAlumno.MotivoJustificacion;
import es.jccm.edu.alumnos.application.ports.in.faltasAsistenciaAlumno.IFaltaAsistenciaAlumnoService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(BasePath.FixmeAlumnosFaltasBasePath)
//@CrossOrigin
@Tag(name = "Servicio Faltas Asistencia Escritorio", description = "Servicio para actualizar y registrar las faltas de asistencia y retasos de los alumnos")
public class FaltaAsistenciaAlumnoRestController {

	@Autowired
	private IFaltaAsistenciaAlumnoService faltaAsistenciaAlumnoService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Endpoint que recibe una lista de alumnos con sus faltas y retrasos del día y
	 * los actualiza en base de datos.
	 *
	 * @param faltasAsistenciaAlumno Listado de alumnos con sus faltas y retrasos
	 *                               del día
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Actualización faltas/retrasos", description = "Este metodo recibe una lista de alumnos con sus faltas y retrasos del día y los actualiza en base de datos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setFaltaAsistenciaAlumno/{idUsuario}/{idCentro}")
	public void setFaltaAsistenciaAlumno(@RequestHeader(Constants.AUTHORIZATION) String jwt, @PathVariable Long idCentro,
			@RequestBody List<FaltaAsistenciaAlumnoDto> faltasAsistenciaAlumno) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<FaltaAsistenciaAlumno> faltasAsistenciaAlumnoIn = faltasAsistenciaAlumno.stream()
				.map(x -> modelMapper.map(x, FaltaAsistenciaAlumno.class)).collect(Collectors.toList());

		faltaAsistenciaAlumnoService.setFaltaAsistenciaAlumno(datosUsuario.getUsuarioComunica(), idCentro, faltasAsistenciaAlumnoIn);
	}

	
	/**
	 * Devuelve un listado de las convocatorias de un alumno
	 *
	 * @param idMatricula
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve un listado de Convocatoraias del alumno", description = "Devuelve un listado de Convocatorias del alumno", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvAlumno")
	public ResponseEntity<List<ConvAlumnCentroDTO>> getConvAlumno(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idMatricula") Long idMatricula) {

		List<ConvAlumnCentro> Convs = faltaAsistenciaAlumnoService.getConvAlumn(idMatricula);

		List<ConvAlumnCentroDTO> listaConvAlumn = Convs.stream()
				.map(x -> modelMapper.map(x, ConvAlumnCentroDTO.class)).collect(Collectors.toList());

		return new ResponseEntity<>(listaConvAlumn, HttpStatus.OK);

	}
	
	/**
	 * Endpoint que recibe las faltas de asistencia de un alumno
	 *
	 * @param idMatricula, 
	 * @param fecini, 
	 * @param fecfin, 
	 * @param type
	 * @param idgroup
	 * 
	 * @return the response entity
	 * @throws UnsupportedEncodingException 
	 */
	@Operation(summary = "Actualización faltas/retrasos", description = "Este metodo recibe una lista de alumnos con sus faltas y retrasos del día y los actualiza en base de datos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFaltasAlumno")
	public ResponseEntity<List<FaltaAlumnoCentroDto>> getFaltasAlumno(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idMatricula") Long idMatricula, 
			@RequestParam("fecIni") String fecIni, @RequestParam("fecFin") String fecFin, @RequestParam("type") String type, @RequestParam("idGroup") String idGroup) throws UnsupportedEncodingException {
		
		String getType = new String(Base64.getDecoder().decode(type),"ISO-8859-1");
		
		List<FaltaAlumnoCentro> listfal = faltaAsistenciaAlumnoService.getFaltasAlumnoByFilter(idMatricula, fecIni, fecFin, getType, idGroup);
		
		List<FaltaAlumnoCentroDto> faltasOut = listfal.stream()
				.map(falta -> modelMapper.map(falta, FaltaAlumnoCentroDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(faltasOut, HttpStatus.OK);
	}
	
	/**
	 * Settea en BBDD una justificación para una falta de asistencia de un alumno
	 * 
	 *
	 * @param idFaltaAsistencia
	 * @param idMotivo
	 * @param observacion
	 * @return HttpStatus
	 * @throws Exception 
	 */
	@Operation(summary = "Settea en BBDD una justificación para una falta de asistencia de un alumno", description = "Este metodo inserta una justificación para una falta de asistenciade un alumno",
			responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setJustificacionFaltaAlumno")
	public HttpStatus setJustificacionFaltaAlumno(@RequestParam("idFaltaAsistencia") Long idFaltaAsistencia, 
			@RequestParam("idMotivo") Long idMotivo, @RequestParam("observacion") String observacion) throws Exception {

		HttpStatus status;
		
		try {
			
			faltaAsistenciaAlumnoService.setJustificacionFaltaAlumno(idFaltaAsistencia, idMotivo, new String(Base64.getDecoder().decode(observacion),"ISO-8859-1"));
			
			status = HttpStatus.OK;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Actualiza en BBDD una justificación para una falta de asistencia de un alumno
	 * 
	 *
	 * @param idFaltaAsistencia
	 * @param idMotivo
	 * @param observacion
	 * @return HttpStatus
	 * @throws Exception 
	 */
	@Operation(summary = "Actualiza en BBDD una justificación para una falta de asistencia de un alumno", description = "Este metodo actualiza una justificación para una falta de asistenciade un alumno",
			responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateJustificacionFaltaAlumno")
	public HttpStatus updateJustificacionFaltaAlumno(@RequestParam("idJustificacion") Long idJustificacion, 
			@RequestParam("idMotivo") Long idMotivo, @RequestParam("observacion") String observacion) throws Exception {

		HttpStatus status;
		
		try {
			faltaAsistenciaAlumnoService.updateJustificacionFaltaAlumno(idJustificacion, idMotivo, new String(Base64.getDecoder().decode(observacion),"ISO-8859-1"));
			
			status = HttpStatus.OK;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Borra de BBDD una justificación para una falta de asistencia de un alumno
	 * 
	 *
	 * @param idFaltaAsistencia
	 * @return HttpStatus
	 * @throws Exception 
	 */
	@Operation(summary = "Borra de BBDD una justificación para una falta de asistencia de un alumno", description = "Este metodo borra una justificación para una falta de asistenciade un alumno",
			responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/deleteJustificacionFaltaAlumno")
	public HttpStatus deleteJustificacionFaltaAlumno(@RequestParam("idFaltaAsistencia") Long idFaltaAsistencia) throws Exception {

		HttpStatus status;
		
		try {
			faltaAsistenciaAlumnoService.deleteJustificacionFaltaAlumno(idFaltaAsistencia);
			
			status = HttpStatus.OK;
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Rescata de BBDD los motivos de justificación de una falta de asistencia de un alumno
	 * 
	 *
	 * @param idFaltaAsistencia
	 * @return HttpStatus
	 * @throws Exception 
	 */
	@Operation(summary = "Rescata de BBDD los motivos de justificación de una falta de asistencia de un alumno", description = "Este metodo rescata los motivos de justificación de una falta de asistencia de un alumno",
			responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getMotivosJustificacionFaltaAlumno")
	public ResponseEntity<List<MotivoJustificacionDto>> getMotivosJustificacionFaltaAlumno() {

		List<MotivoJustificacion> motivos = faltaAsistenciaAlumnoService.getMotivosJustificacionFaltaAlumno();
		
		List<MotivoJustificacionDto> motivosOut = motivos.stream()
				.map(motivo -> modelMapper.map(motivo, MotivoJustificacionDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(motivosOut, HttpStatus.OK);
	}

	/**
	 * Rescata de BBDD los motivos de justificación de una falta de asistencia de un alumno
	 * 
	 *
	 * @param idFaltaAsistencia
	 * @return HttpStatus
	 * @throws Exception 
	 */
	@Operation(summary = "Borra una notificacion", description = "Este metodo borra una notificación de una falta de asistencia o retraso de un alumno",
			responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
    @DeleteMapping("/deleteNotificacion/{idNotificacion}")
    public ResponseEntity<Void> borrarNotificacionCompleta(@PathVariable Long idNotificacion) {
        faltaAsistenciaAlumnoService.borrarNotificacionCompleta(idNotificacion);
        return ResponseEntity.noContent().build();
    }
	
}

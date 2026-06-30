package es.jccm.edu.buzon.adapter.in.rest.buzon;

import es.jccm.edu.buzon.adapter.in.rest.buzon.model.*;
import es.jccm.edu.buzon.application.domain.solicitudCorreoAlumno.SolicitudActivacionCorreoDto;
import es.jccm.edu.buzon.application.ports.in.SolicitudCorreoAlumno.ISolicitudCorreoAlumnoService;
import es.jccm.edu.buzon.application.services.solicitudCorreoAlumno.SolicitudCorreoAlumnoService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

@Slf4j
@RestController
@RequestMapping("${spring.data.rest.base-path:/api}/buzon/solicitudCorreoAlumno")
//@CrossOrigin
public class SolicitudCorreoAlumnoController {

	@Autowired
	private final ISolicitudCorreoAlumnoService solicitudCorreoAlumnoService;

	@Autowired
	public SolicitudCorreoAlumnoController(SolicitudCorreoAlumnoService solicitudCorreoAlumnoService) {
		this.solicitudCorreoAlumnoService = solicitudCorreoAlumnoService;
	}

	@PostMapping("/guardarSolicitud")
	public ResponseEntity<Map<String, String>> guardarSolicitud(@RequestBody SolicitudCorreoUsuarioDTO solicitudCorreoAlumno){
		Long newIdSolicitud=null;

		// Guarda la nueva Solicitud.
		try {
			newIdSolicitud=solicitudCorreoAlumnoService.guardarSolicitud(solicitudCorreoAlumno);
		} catch (Exception ex) {
			return handleException(ex);
		}
		// Actualiza USUARIOS_T.CRT_PT con el ID Solicitud.
		try{
			if(newIdSolicitud!=null){
				LocalDate localDate = solicitudCorreoAlumno.getFechaSolicitud().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				Long anno = (long) localDate.getYear();
				List<UsuariotDto> datosAlumnos = solicitudCorreoAlumnoService.getAlumnosFromCursoCentro(
					solicitudCorreoAlumno.getIdCentro()
					,solicitudCorreoAlumno.getIdOfertamatricCurso()
					,anno
					,Optional.ofNullable(null)
				);
				for(UsuariotDto alumno : datosAlumnos){
					alumno.setCRT_PT(newIdSolicitud);
					solicitudCorreoAlumnoService.guardarAlumno(alumno);
				}
			}
			// RESPONSE
			Map<String, String> response = new HashMap<>();
			response.put("message", "Solicitud guardada correctamente");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception ex){
			return handleException(ex);
		}
	}

	@PostMapping("/guardarSolicitudes")
	public ResponseEntity<String> guardarSolicitudes(@RequestBody List<SolicitudCorreoUsuarioDTO> solicitudesCorreoAlumno) {
		solicitudCorreoAlumnoService.guardarSolicitudes(solicitudesCorreoAlumno);
		return ResponseEntity.status(HttpStatus.OK).body("Solicitudes guardadas correctamente");
	}
	
	@Operation(
		summary = "Crea las actualizaciones si hay una petición de correo previa",
		description = "Este método llama a un procedimiento de BD que actualiza los datos",
		responses = { @ApiResponse(
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	) } )
	@ApiResponses( value = {
		@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/actualizarSolicitudes")
	public <T> ResponseEntity<T> actualizarSolicitudes() {
		try{
			solicitudCorreoAlumnoService.updateCrtptFromUsuariost();
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return handleException(e);
		}
	}

	@Operation(
		summary = "Devuelve todas las solicitudes de correo",
		description = "Este método devuelve una lista del personal elegible para ser administrador del buzón del centro",
		responses = { @ApiResponse(
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	) } )
	@ApiResponses( value = {
		@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") 
	} )
	@GetMapping("/getSolicitudes")
	public ResponseEntity<List<SolicitudCorreoUsuarioDTO>> getSolicitudes() {
		try{
			List<SolicitudCorreoUsuarioDTO> solicitudesDto = solicitudCorreoAlumnoService.findAll();
			return new ResponseEntity<List<SolicitudCorreoUsuarioDTO>>(solicitudesDto,HttpStatus.OK);
		}catch (Exception e) {
			return handleException(e);
		}
	}

	@Operation(
		summary = "Devuelve todas las solicitudes de correo",
		description = "Este método devuelve una lista del personal elegible para ser administrador del buzón del centro",
		responses = { @ApiResponse(
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	) } )
	@ApiResponses( value = {
		@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSolicitudesv2")
	public ResponseEntity<List<SolicitudActivacionCorreoDto>> getSolicitudesv2() {
		try{
			List<SolicitudActivacionCorreoDto> solicitudesDto = solicitudCorreoAlumnoService.buscarSolicitudes();
			return new ResponseEntity<List<SolicitudActivacionCorreoDto>>(solicitudesDto,HttpStatus.OK);
		}catch (Exception e) {
			return handleException(e);
		}
	}

	@Operation(
		summary = "Devuelve todas las solicitudes de correo de un mismo curso",
		description = "Este método devuelve una lista de las solicitudes de buzón de correo de un curso.",
		responses = { @ApiResponse(
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	) } )
	@ApiResponses( value = {
		@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSolicitudById")
	public ResponseEntity<List<SolicitudActivacionCorreoDto>> getSolicitudById(Long idOfertamatricCurso) {
		try{
			List<SolicitudActivacionCorreoDto> solicitudesDto = solicitudCorreoAlumnoService.buscarSolicitudes(idOfertamatricCurso);
			return new ResponseEntity<List<SolicitudActivacionCorreoDto>>(solicitudesDto,HttpStatus.OK);
		}catch (Exception e) {
			return handleException(e);
		}
	}

	@Operation(
		summary = "Devuelve todas las solicitudes de correo de un mismo curso y centro.",
		description = "Este método devuelve una lista de las solicitudes de buzón de correo de un curso.",
		responses = { @ApiResponse(
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	) } )
	@ApiResponses( value = {
		@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSolicitudesByCursoCentro")
	public ResponseEntity<List<SolicitudActivacionCorreoDto>> getSolicitudesByCursoCentro(Long idCurso, Long idCentro) {
		try{
			List<SolicitudActivacionCorreoDto> solicitudesDto = solicitudCorreoAlumnoService.getSolicitudesByCursoCentro(idCurso, idCentro);
			return new ResponseEntity<List<SolicitudActivacionCorreoDto>>(solicitudesDto,HttpStatus.OK);
		}catch (Exception e) {
			return handleException(e);
		}
	}

	@Operation(
		summary = "Devuelve todas las solicitudes de correo",
		description = "Este método devuelve una lista del personal elegible para ser administrador del buzón del centro",
		responses = { @ApiResponse(
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	) } )
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSolicitudesCentro")
	public ResponseEntity<List<SolicitudCorreoUsuarioDTO>> getSolicitudesByCentro(Long idCentro) {
		try{
			List<SolicitudCorreoUsuarioDTO> solicitudesDto = solicitudCorreoAlumnoService.findAllByCentro(idCentro);
			return new ResponseEntity<List<SolicitudCorreoUsuarioDTO>>(solicitudesDto,HttpStatus.OK);
		}catch (Exception e) {
			return handleException(e);
		}
	}

	@Operation(
		summary = "Devuelve todas las solicitudes de correo",
		description = "Este método devuelve una lista del personal elegible para ser administrador del buzón del centro",
		responses = { @ApiResponse(
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))
	) } )
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCursoSolicitudDatos")
	public ResponseEntity<List<CursoSolicitudDatosDTO>> getCursoSolicitudDatos(Long idCentro, Long anno) {
		try{
			List<CursoSolicitudDatosDTO> cursoSolicitudDatosDTO = solicitudCorreoAlumnoService.getCursoSolicitudDatos(idCentro, anno);
			return new ResponseEntity<List<CursoSolicitudDatosDTO>>(cursoSolicitudDatosDTO,HttpStatus.OK);
		}catch (Exception e) {
			return handleException(e);
		}
	}

  @Operation(
	summary = "Devuelve los datos de los alumnos de un curso",
	description = "Este método devuelve los datos de los alumnos de un curso y un centro concretos",
	responses = { @ApiResponse(
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
	@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
	@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
	@ApiResponse(responseCode = "404", description = "No encontrado") })
  @GetMapping("/getDatosAlumnos")
  public ResponseEntity<List<UsuariotDto>> getDatosAlumnosCurso(@RequestParam("idCentro") Long idCentro, @RequestParam("idOfertaMatrig") Long idOfertaMatrig,
											 @RequestParam(name = "anno", required = false) Long anno,	@RequestParam("idSolicitud") Long idSolicitud){
	try{
		List<UsuariotDto> datosAlumnos = solicitudCorreoAlumnoService.getAlumnosFromCursoCentro(
			idCentro
			,idOfertaMatrig
			,anno
			,Optional.ofNullable(idSolicitud)
		);
		return ResponseEntity.ok(datosAlumnos);
	}catch (Exception e) {
        log.error(e.getMessage());
		return handleException(e);
	}
  }

	// Método auxiliar para manejar excepciones
	private <T> ResponseEntity<T> handleException(Exception e) {
		if (e instanceof Unauthorized) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else if (e instanceof Forbidden) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Devuelve las unidades de los alumnos de un curso",
			   description = "Este método devuelve los datos de las unidades de un centro, año y curso",
			   responses = { @ApiResponse(
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
							@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
							@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
							@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getUnidadesCorreoAlumnado")
	public ResponseEntity<List<UnidadDto>> getUnidadesCorreoAlumnado(@RequestParam Long idCentro, @RequestParam Integer anno, @RequestParam Long idCurso) {
		try{

			List<UnidadDto> listadoUnidades = solicitudCorreoAlumnoService.getUnidadesCorreoAlumnado(idCentro, anno, idCurso);

			return new ResponseEntity<List<UnidadDto>>(listadoUnidades, HttpStatus.OK);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@Operation(summary = "Devuelve los permisos del alumnado en el correo",
			description = "Este método devuelve todos los permisos que puede tener el alumnado en su correo",
			responses = { @ApiResponse(
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getPermisosCorreoAlumnado")
	public ResponseEntity<List<PermisoDto>> getPermisosCorreoAlumnado() {
		try{

			List<PermisoDto> listadoPermisos = solicitudCorreoAlumnoService.getPermisosCorreoAlumnado();

			return new ResponseEntity<List<PermisoDto>>(listadoPermisos, HttpStatus.OK);
		} catch (Exception e) {
			return handleException(e);
		}
	}

	@Operation(summary = "Devuelve los alumnos de un curso",
			description = "Este método devuelve los datos de los alumnos de un centro, año y curso",
			responses = { @ApiResponse(
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosDetalleCorreoAlumnado")
	public ResponseEntity<List<AlumnoDetalleDto>> getAlumnosDetalleCorreoAlumnado(@RequestParam Long idCentro, @RequestParam Integer anno, @RequestParam Long idCurso) {
		try{

			List<AlumnoDetalleDto> listadoAlumnos = solicitudCorreoAlumnoService.getAlumnosDetalleCorreoAlumnado(idCentro, anno, idCurso);

			return new ResponseEntity<List<AlumnoDetalleDto>>(listadoAlumnos, HttpStatus.OK);
		} catch (Exception e) {
			return handleException(e);
		}
	}


	// @PostMapping("/actualizarPermisoUsuariosT")
	@Operation(summary = "Actualiza el permiso de correo de alumnos",
			description = "Actualiza el permiso de correo de alumnos",
			responses = { @ApiResponse(
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PutMapping("/actualizarPermisoUsuariosT")
	public ResponseEntity<Void> actualizarPermisoUsuariosT(@RequestBody ActualizarPermisoUsuariosDTO actualizarPermisoUsuariosDTO){
		try{
			for(Long usuariot : actualizarPermisoUsuariosDTO.getListaOidUsuariosAModificar()){
				solicitudCorreoAlumnoService.actualizarPermisoUsuariosT(usuariot, actualizarPermisoUsuariosDTO.getIdPermiso());
			}
			return ResponseEntity.ok().build();
		}catch(Exception ex){
			log.error(ex.getMessage());
			return handleException(ex);
		}
	}

}

package es.jccm.edu.buzon.adapter.in.rest.buzon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import es.jccm.edu.buzon.adapter.in.rest.buzon.model.AlumnoUnidadDto;
import es.jccm.edu.buzon.adapter.in.rest.buzon.model.CursoSolicitudDto;
import es.jccm.edu.buzon.adapter.in.rest.buzon.model.UsuariotBuzonDto;
import es.jccm.edu.buzon.application.domain.buzon.BuzonAlumnado;
import es.jccm.edu.buzon.application.ports.in.buzonAlumnado.IBuzonAlumnado;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.services.datosUsuarioJwt.DatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import es.jccm.edu.simulacion.adapter.in.rest.centros.model.UsuariotDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


//@CrossOrigin
@Controller
@RequestMapping("${spring.data.rest.base-path:/api}" + "/buzon/buzonAlumnado")
public class BuzonAlumnadoController {

	@Autowired
	DatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	IBuzonAlumnado buzonAlumnado;
    
    @Operation(summary = "Comprueba si un usuario pertenece al equipo directivo.", description = "Este método comprueba si el usuario pertenece al equipo directivo.",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/isEquipoDirectivo")
	public ResponseEntity<Boolean> isEquipoDirectivo(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try{
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			Boolean isEquipoDirectivo = buzonAlumnado.isEquipoDirectivo(datosUsuario);
			return new ResponseEntity<Boolean>(isEquipoDirectivo,HttpStatus.OK);
		}catch (Exception e) {
            return handleException(e);
        }
	}
    
    @Operation(summary = "Devuelve alumnos del centro", description = "Este método devuelve una lista con los alumnos del centro.",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnado")
	public ResponseEntity <List<UsuariotDto>>getFechasVigenciaAlumnado(@RequestParam String oidCentro) {
    	List<UsuariotDto> usuarios = new ArrayList<>();
		try{
			usuarios = buzonAlumnado.getAlumnado(oidCentro);
			return new ResponseEntity<List<UsuariotDto>>(usuarios,HttpStatus.OK);
		}catch (Exception e) {
            return handleException(e);
        }
	}   
    
    @Operation(summary = "Devuelve lista personal centro", description = "Este método devuelve una lista del personal elegible para ser administrador del buzón del centro",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAccesosBuzon")
	public ResponseEntity<List<UsuariotDto>> getAccesosBuzonAlumnado(@RequestParam String oidCentro) {
		try{
			List<UsuariotDto> usuariostDto = buzonAlumnado.getPersonalAccesoBuzonAlumnado(oidCentro);
			return new ResponseEntity<List<UsuariotDto>>(usuariostDto,HttpStatus.OK);
		}catch (Exception e) {
            return handleException(e);
        }
	}
    
    @Operation(summary = "Actualiza fecha vigencia", description = "Este método actualiza la fecha de vigencia del acceso al buzón del centro de un usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateFechaVigencia")
	public ResponseEntity<Boolean> editFechaVigencia(@RequestParam Long oidPersona, @RequestParam String fechaBaja, @RequestParam String fechaAlta) {
		try{
			Boolean completado = buzonAlumnado.updateFechaVigencia(oidPersona, fechaBaja, fechaAlta);
			return new ResponseEntity<Boolean>(completado,HttpStatus.OK);
		}catch (Exception e) {
            return handleException(e);
        }	
	}
    
    @Operation(summary = "Devuelve alumnos del centro y sus unidades", description = "Este método devuelve una lista con los alumnos del centro y a qué unidad pertenece cada uno.",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosUnidades")
	public ResponseEntity<List<AlumnoUnidadDto>> getAlumnosUnidades(@RequestParam String oidCentro) {
    	List<AlumnoUnidadDto> alumnos = new ArrayList<>();
		try{
			alumnos = buzonAlumnado.getAlumnosUnidades(oidCentro);
			return new ResponseEntity<List<AlumnoUnidadDto>>(alumnos,HttpStatus.OK);
		}catch (Exception e) {
            return handleException(e);
        }
	}
    
    @Operation(summary = "Añade permisos al buzón de alumnado", description = "Este método añade permisos con vigencia para el buzón de alumnado",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/postSeleccionados")
	public ResponseEntity<Boolean> postSeleccionadosAlumnado(@RequestBody List<UsuariotBuzonDto> seleccionados, @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try{
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			Boolean completado = buzonAlumnado.postSeleccionadosAlumnado(seleccionados, datosUsuario);
			return new ResponseEntity<Boolean>(completado,HttpStatus.OK);
		}catch (Exception e) {
            return handleException(e);
        }
	}
    
    @Operation(summary = "Devuelve fechas vigencia", description = "Este método devuelve una lista que contiene las fecha de vigencia del acceso al buzón del centro de un usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/getFechasVigencia")
	public ResponseEntity<List<BuzonAlumnado>> getFechasVigenciaAlumnado(@RequestBody List<Long> oidPersonasAccesoBuzonAlumnado) {
    	List<BuzonAlumnado> fechasVigencia = new ArrayList<>();
		try{
			fechasVigencia = buzonAlumnado.getFechasVigenciaAlumnado(oidPersonasAccesoBuzonAlumnado);
			return new ResponseEntity<List<BuzonAlumnado>>(fechasVigencia,HttpStatus.OK);
		}catch (Exception e) {
            return handleException(e);
        }
	}
    
    @Operation(summary = "Devuelve lista personal centro", description = "Este método devuelve una lista del personal elegible para ser administrador del buzón del centro",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/deleteSeleccionados")
	public ResponseEntity<Boolean> deleteSeleccionadosCentro(@RequestBody List<UsuariotDto> seleccionados) {
		try{
			Boolean completado = buzonAlumnado.deleteSeleccionados(seleccionados);
			return new ResponseEntity<Boolean>(completado,HttpStatus.OK);
		}catch (Exception e) {
            return handleException(e);
        }
	}

	@Operation(summary = "Devuelve alumnos del centro y sus unidades", description = "Este método devuelve una lista con los alumnos del centro y a qué unidad pertenece cada uno.",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCursoEtapaAlumnosCentro")
	public ResponseEntity<List<CursoSolicitudDto>> getCursoEtapaAlumnosCentro(@RequestParam String oidCentro, @RequestParam String etapa, @RequestParam String curso) {
    	List<CursoSolicitudDto> cursos = new ArrayList<>();
		try{
			cursos = buzonAlumnado.getCursoEtapaAlumnosCentro(oidCentro);
			return new ResponseEntity<List<CursoSolicitudDto>>(cursos,HttpStatus.OK);
		}catch (Exception e) {
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

}

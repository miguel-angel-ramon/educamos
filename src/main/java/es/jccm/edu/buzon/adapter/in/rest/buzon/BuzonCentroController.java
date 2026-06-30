package es.jccm.edu.buzon.adapter.in.rest.buzon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import es.jccm.edu.buzon.adapter.in.rest.buzon.model.UnidadBuzonCentroDTO;
import es.jccm.edu.buzon.adapter.in.rest.buzon.model.UsuariotBuzonDto;
import es.jccm.edu.buzon.application.domain.buzonCentro.BuzonCentro;
import es.jccm.edu.buzon.application.domain.buzonCentro.UnidadBuzonCentro;
import es.jccm.edu.buzon.application.ports.in.buzonCentro.IBuzonCentro;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.services.datosUsuarioJwt.DatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import es.jccm.edu.simulacion.adapter.in.rest.centros.model.UsuariotDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;



@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/buzon")
//@CrossOrigin
public class BuzonCentroController {

    @Operation(summary = "Devuelve lista candidatos", description = "Este método devuelve una lista de candidatos",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCandidatosCentro")
	public ResponseEntity<List<UsuariotDto>> getCandidatosCentro(@RequestParam String oidCentro) {
		try{
			List<UsuariotDto> usuariostDto = buzonCentro.getCandidatosCentro(oidCentro);
			return new ResponseEntity<List<UsuariotDto>>(usuariostDto,HttpStatus.OK);
		}catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		/*catch (Unauthorized e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }catch (Forbidden e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ACCESOPROHIBIDO);
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	    }catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ERRORDESCONOCIDO);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }*/
	} 
    
    private final String ERROR = "ERROR";
    
    @Operation(summary = "Actualiza fecha vigencia", description = "Este método actualiza la fecha de vigencia del acceso al buzón del centro de un usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateFechaVigencia")
	public ResponseEntity<Boolean> editFechaVigencia(@RequestParam Long oidPersona, @RequestParam String fechaBaja, @RequestParam String fechaAlta) {
		try{
			Boolean completado = buzonCentro.updateFechaVigencia(oidPersona, fechaBaja, fechaAlta);
			return new ResponseEntity<Boolean>(completado,HttpStatus.OK);
		}catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }/*catch (Unauthorized e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
	    }catch (Forbidden e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ACCESOPROHIBIDO);
	        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
	    }catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ERRORDESCONOCIDO);
	        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	    }	*/	
	}
    
    private final String ACCESOPROHIBIDO = "ACCESO PROHIBIDO";
    
    @Operation(summary = "Este método añade la vigencia del personal seleccionado al buzon", description = "Este método añade la vigencia del personal seleccionado al buzon",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/postSeleccionadosCentro")
	public ResponseEntity<Boolean> postSeleccionadosCentro(@RequestBody List<UsuariotBuzonDto> seleccionados, @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try{
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			Boolean completado = buzonCentro.postSeleccionadosCentro(seleccionados, datosUsuario);
			return new ResponseEntity<Boolean>(completado,HttpStatus.OK);
		}catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }/*catch (Unauthorized e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
	    }catch (Forbidden e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ACCESOPROHIBIDO);
	        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
	    }catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ERRORDESCONOCIDO);
	        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	    }	*/	
	}
    
    private final String ERRORDESCONOCIDO = "ERROR DESCONOCIDO";
    
    @Operation(summary = "Este método borra la vigencia de un usuario al buzon", description = "Este método borra la vigencia de un usuario al buzon",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/deleteSeleccionadosCentro")
	public ResponseEntity<Boolean> deleteSeleccionadosCentro(@RequestBody List<UsuariotDto> seleccionados) {
		try{
			Boolean completado = buzonCentro.deleteSeleccionadosCentro(seleccionados);
			return new ResponseEntity<Boolean>(completado,HttpStatus.OK);
		}catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }/*catch (Unauthorized e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
	    }catch (Forbidden e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ACCESOPROHIBIDO);
	        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
	    }catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ERRORDESCONOCIDO);
	        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	    }	*/	
	}
    
    @Autowired
	DatosUsuarioJwtService datosUsuarioJwtService;

    @Operation(summary = "Devuelve fechas vigencia", description = "Este método devuelve una lista que contiene las fecha de vigencia del acceso al buzón del centro de un usuario",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/getFechasVigencia")
	public ResponseEntity<List<BuzonCentro>> getFechasVigencia(@RequestBody List<Long> oidPersonasAccesoBuzon) {
    	List<BuzonCentro> fechasVigencia = new ArrayList<>();
		try{
			fechasVigencia = buzonCentro.getFechasVigencia(oidPersonasAccesoBuzon);
			return new ResponseEntity<List<BuzonCentro>>(fechasVigencia,HttpStatus.OK);
		}catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }/*catch (Unauthorized e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<List<BuzonCentro>>(fechasVigencia, HttpStatus.UNAUTHORIZED);
	    }catch (Forbidden e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ACCESOPROHIBIDO);
	        return new ResponseEntity<List<BuzonCentro>>(fechasVigencia, HttpStatus.FORBIDDEN);
	    }catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ERRORDESCONOCIDO);
	        return new ResponseEntity<List<BuzonCentro>>(fechasVigencia, HttpStatus.INTERNAL_SERVER_ERROR);
	    }	*/	
	}
    
	@Autowired
	IBuzonCentro buzonCentro;
     
    @Operation(summary = "Devuelve lista personal centro", description = "Este método devuelve una lista de personal del centro",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getpersonalcentro")
	public ResponseEntity<List<UsuariotDto>> getPersonalCentro(@RequestParam String oidCentro) {
		try{
			List<UsuariotDto> usuariostDto = buzonCentro.getPersonalCentro(oidCentro);
			return new ResponseEntity<List<UsuariotDto>>(usuariostDto,HttpStatus.OK);
		}catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }/*catch (Unauthorized e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }catch (Forbidden e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ACCESOPROHIBIDO);
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	    }catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ERRORDESCONOCIDO);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }*/		
	}
    
	private final String UNAUTHORIZED = "No está autorizado para realizar esta operación";
    
    @Operation(summary = "Devuelve lista no docentes activos centro", description = "Este método devuelve una lista de los no docentes activos del centro",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getnodocentes")
	public ResponseEntity<List<UsuariotDto>> getNoDocentes(@RequestParam String oidCentro) {
		try{
			List<UsuariotDto> usuariostDto = buzonCentro.getNoDocentes(oidCentro);
			return new ResponseEntity<List<UsuariotDto>>(usuariostDto,HttpStatus.OK);
		}catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }/*catch (Unauthorized e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }catch (Forbidden e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ACCESOPROHIBIDO);
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	    }catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ERRORDESCONOCIDO);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }	*/	
	}
    
    //Devuelve director centro
    
    @Operation(summary = "Devuelve director centro", description = "Este método devuelve el director del centro",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/isDirector")
	public ResponseEntity<Boolean> isDirector(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try{
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			Boolean isDirector = buzonCentro.isDirector(datosUsuario);
			return new ResponseEntity<Boolean>(isDirector,HttpStatus.OK);
		}catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }/*catch (Unauthorized e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }catch (Forbidden e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ACCESOPROHIBIDO);
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	    }catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ERRORDESCONOCIDO);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }*/		
	}
    
    //Devuelve lista docentes activos centro
    
    @Operation(summary = "Devuelve lista docentes activos centro", description = "Este método devuelve una lista de los docentes con puesto activo del centro",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getdocentes")
	public ResponseEntity<List<UsuariotDto>> getDocentes(@RequestParam String oidCentro) {
		try{
			List<UsuariotDto> usuariostDto = buzonCentro.getDocentes(oidCentro);
			return new ResponseEntity<List<UsuariotDto>>(usuariostDto,HttpStatus.OK);
		}catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }/*catch (Unauthorized e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }catch (Forbidden e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ACCESOPROHIBIDO);
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	    }catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, "Error desconocido");
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }	*/	
	}
    
    //Devuelve lista administradores buzón centro
    
    @Operation(summary = "Devuelve lista administradores buzón centro", description = "Este método devuelve una lista de los administradores del buzón del centro",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
                @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
                @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
                @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getadministradoresbuzoncentro")
	public ResponseEntity<List<UsuariotDto>> getAdministradoresBuzonCentro(@RequestParam String oidCentro) {
		try{
			List<UsuariotDto> usuariostDto = buzonCentro.getAdministradoresBuzonCentro(oidCentro);
			return new ResponseEntity<List<UsuariotDto>>(usuariostDto,HttpStatus.OK);
		}catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }/*catch (Unauthorized e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, UNAUTHORIZED);
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }catch (Forbidden e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ACCESOPROHIBIDO);
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	    }catch (Exception e) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put(ERROR, ERRORDESCONOCIDO);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }		*/
	}
    
}

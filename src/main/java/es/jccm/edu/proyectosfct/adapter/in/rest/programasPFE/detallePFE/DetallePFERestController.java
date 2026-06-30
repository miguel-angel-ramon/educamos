package es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.detallePFE;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionFlujoSiguienteDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.AutorizacionesAnexosHistorialDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.desplazamiento.model.HistoricoFlujoAutorizacionDto;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionFlujoSiguiente;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.HistoricoFlujoAutorizacion;
import es.jccm.edu.proyectosfct.application.services.desplazamiento.DesplazamientoService;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.HistorialProgramaPFE;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model.DatosVigentePFEDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model.ProgramasPFEDto;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.DatosVigentePFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.ProgramasPFE;
import es.jccm.edu.proyectosfct.application.ports.in.programasPFE.DetallePFE.IProgamaPFE;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio detalle programas PFE", description = "Servicio con las operaciones sobre el detalle programas PFE")
public class DetallePFERestController {
	
    @Autowired
    private ModelMapper modelMapper;
	
    @Autowired
    private IProgamaPFE iProgamaPFE;
    
    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	DesplazamientoService desplazamientoService;
	
	@Operation(summary = "Lista de cursos", description = "Este metodo devuelve la lista de Modulos modalidad LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCursosCiclos/{idCentro}/{cAnno}")
	public ResponseEntity<List<ElementoSelectDto>> getCursosCiclos(@PathVariable("idCentro") Long idCentro,
			                                                       @PathVariable("cAnno") Integer cAnno) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<ElementoSelect> curos = iProgamaPFE.getCursosCiclos(idCentro,cAnno);

			List<ElementoSelectDto> cursoDto = curos.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());

			return new ResponseEntity<List<ElementoSelectDto>>(cursoDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Lista de cursos", description = "Este metodo devuelve la lista de Modulos modalidad LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCursosEspecializacion/{idCentro}/{cAnno}")
	public ResponseEntity<List<ElementoSelectDto>> getCursosEspecializacion(@PathVariable("idCentro") Long idCentro,
																            @PathVariable("cAnno") Integer cAnno
																		    ) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<ElementoSelect> cursos = iProgamaPFE.getCursosEspecializacion(idCentro, cAnno);

			List<ElementoSelectDto> cursosDto = cursos.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());

			return new ResponseEntity<List<ElementoSelectDto>>(cursosDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Lista de cursos", description = "Este metodo devuelve la lista de Modulos modalidad LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getProgramaFPE/{id}/{xPerfil}/{esJefe}")
	public ResponseEntity<ProgramasPFEDto> getPrograma(@PathVariable("id") Long id,
													   @PathVariable("xPerfil") Long xPerfil,
													   @PathVariable("esJefe") Integer esJefe,
			                                           @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try {			
			
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
			
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			ProgramasPFE programa = iProgamaPFE.getProgramaPFE(id, xPerfil, datosUsuario.getXUsuarioDelphos(), esJefe);

			ProgramasPFEDto programaDto = modelMapper.map(programa, ProgramasPFEDto.class);

			return new ResponseEntity<ProgramasPFEDto>(programaDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Operation(summary = "Lista de cursos", description = "Este metodo devuelve la lista de Modulos modalidad LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getExistePFE/{id}/{idCentro}/{idCurso}/{nuAnnoDesde}/{NuAnnoHasta}/{lgAlcance}/{lgModalidad}")
	public ResponseEntity<List<DatosVigentePFEDto>> getExisteFPE(@PathVariable("id") Long id,
															  @PathVariable("idCurso") Long idCurso,
														      @PathVariable("idCentro") Long idCentro,
														      @PathVariable("nuAnnoDesde") Integer nuAnnoDesde,
														      @PathVariable("NuAnnoHasta") Integer NuAnnoHasta,
														      @PathVariable("lgAlcance") Integer lgAlcance,
														      @PathVariable("lgModalidad") Integer lgModalidad) 
	{
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<DatosVigentePFE> vigente = iProgamaPFE.getExistePFE(id,idCentro,idCurso,nuAnnoDesde,NuAnnoHasta,lgAlcance,lgModalidad);			

			List<DatosVigentePFEDto> vigenteDto = vigente.stream().map(x -> modelMapper.map(x, DatosVigentePFEDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<DatosVigentePFEDto>>(vigenteDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Creación de PFE", description = "Este metodo crea PFE", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/savePFE")
	public ResponseEntity<Object> savePFE(@RequestBody ProgramasPFEDto pfe,										  
			                              @RequestHeader(Constants.AUTHORIZATION) String jwt) {
	    try {
	    	
	    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
	    	
	    	ProgramasPFE pfeRequest = modelMapper.map(pfe, ProgramasPFE.class);  	
	    	
	    	ProgramasPFE pfeResponse = iProgamaPFE.savePFE(pfeRequest,datosUsuario.getXUsuarioDelphos());
	    	
	    	ProgramasPFEDto pfeDtoResponse = modelMapper.map(pfeResponse, ProgramasPFEDto.class);  	
	        
	    	return new ResponseEntity<Object>(pfeDtoResponse, HttpStatus.OK);
	        
	    } catch (Exception e) {
	        return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(
	                Map.of(
	                    "message", "Error asignando módulos",
	                    "detail",  e.getMessage()
	                )
	            );
	    }
	}
	
	
	@Operation(summary = "Lista de años inicio", description = "Este metodo devuelve la lista de Modulos modalidad LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAnnosIni")
	public ResponseEntity<List<ElementoSelectDto>> getAnnosIni() {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<ElementoSelect> cursos = iProgamaPFE.getAnnosIni();

			List<ElementoSelectDto> cursosDto = cursos.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());

			return new ResponseEntity<List<ElementoSelectDto>>(cursosDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Lista de años fin", description = "Este metodo devuelve la lista de Modulos modalidad LOFP",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAnnosFin")
	public ResponseEntity<List<ElementoSelectDto>> getAnnosFin() {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			List<ElementoSelect> cursos = iProgamaPFE.getAnnosFin();

			List<ElementoSelectDto> cursosDto = cursos.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());

			return new ResponseEntity<List<ElementoSelectDto>>(cursosDto, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")

	/**
	 * Obtener el anexo V
	 * @param response
	 * @return
	 */
	@Operation(summary ="Anexo V de programas PFE.", description = "",responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAnexoV/{idProgPerFor}"})
	public ResponseEntity<Object> getAnexoV(HttpServletResponse response,
											@PathVariable("idProgPerFor") Long idProgPerFor){
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + "AnexoV" +"."+ "pdf");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setStatus(200);

		try {

			byte[] documento = iProgamaPFE.getAnexoV(idProgPerFor);

			InputStream is = new ByteArrayInputStream(documento);
			FileCopyUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Generar anexo", description = "Este metodo crea PFE", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/generarAnexoV/{idPrograma}/{modo}/{sigEstado}/{idPerfil}/{esJefe}/{esDirector}")
	public ResponseEntity<Object> generarAnexoV(@PathVariable("idPrograma") Long idPrograma,
			                                    @PathVariable("modo") Integer modo,
			                                    @PathVariable("sigEstado") String sigEstado,
			                                    @PathVariable("idPerfil") Long idPerfil,
			                                    @PathVariable("esJefe") Integer esJefe,
			                                    @PathVariable("esDirector") Integer esDirector,
			                                    @RequestHeader(Constants.AUTHORIZATION) String jwt) {
	    try {
	    	
	    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);    		
	    	
	    	Integer pfeResponse = iProgamaPFE.generarAnexoV(idPrograma,datosUsuario.getXUsuarioDelphos(), modo, sigEstado, idPerfil, esJefe, esDirector);	 	
	        
	    	return new ResponseEntity<Object>(pfeResponse, HttpStatus.OK);
	        
	    } catch (Exception e) {
	        return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(
	                Map.of(
	                    "message", "Error generando anexo",
	                    "detail",  e.getMessage()
	                )
	            );
	    }
	}

	/**
	 * Get siguiente estado flujo autorizacion.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get flujo autorizacion desplazamiento", description = "Este metodo devuelve el flujo autorizacion desplazamiento",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getSiguienteEstadoFlujoPFE/{idPerfil}/{idPrograma}/{esJefe}/{esDirector}")
	public ResponseEntity<List<AutorizacionFlujoSiguienteDto>> getSiguienteEstadoFlujoPFE(@PathVariable("idPerfil") Long idPerfil,
																										   @PathVariable("idPrograma") Long idPrograma,
																										   @PathVariable("esJefe") Integer esJefe,
																										   @PathVariable("esDirector") Integer esDirector,
																										   @RequestHeader(Constants.AUTHORIZATION) String jwt){

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		List<AutorizacionFlujoSiguiente> listadoIn =  iProgamaPFE.getSiguienteEstadoFlujoPFE(idPerfil, idPrograma, esJefe, datosUsuario.getXUsuarioDelphos(), esDirector, false);

		List<AutorizacionFlujoSiguienteDto> listadoOut = listadoIn.stream().map(x -> modelMapper.map(x, AutorizacionFlujoSiguienteDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(listadoOut, HttpStatus.OK);


	}
	/**
	 * Creacion siguiente estado flujo historial.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Creación siguiente estado flujo historial", description = "Este metodo crea el siguiente estado flujo historial", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/createSiguienteEstadoFlujoPfeAnexo/{idPerfil}")
	public ResponseEntity<Object> createSiguienteEstadoFlujoPfeAnexo(@RequestBody final AutorizacionesAnexosHistorialDto historialAnexoDto,
																		   @PathVariable("idPerfil") Long idPerfil,																		   
																		   @RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);

			HistorialProgramaPFE historialIn = iProgamaPFE.createSiguienteEstadoFlujoPfeAnexo(historialAnexoDto,
					idPerfil,			
					datosUsuario.getXUsuarioDelphos() );

			

			return new ResponseEntity<>(historialIn, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Historico gastos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista histórico gasto", description = "Este metodo devuelve una Lista de historico de un gasto tutor/alumnado y anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getHistoricoFlujoAutorizacionPFE/{id}")
	public ResponseEntity<List<HistoricoFlujoAutorizacionDto>> getHistoricoFlujoAutorizacionPFE(@PathVariable("id") Long id){		
		
		List<HistoricoFlujoAutorizacion> historicoIn =  iProgamaPFE.getHistoricoFlujoAutorizacionPFE(id);
		
		List<HistoricoFlujoAutorizacionDto> historicoOut = historicoIn.stream().map(x -> modelMapper.map(x, HistoricoFlujoAutorizacionDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(historicoOut, HttpStatus.OK);
	}

}

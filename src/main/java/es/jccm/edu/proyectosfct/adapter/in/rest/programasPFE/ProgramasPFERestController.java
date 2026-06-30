package es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE;

import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model.CombosProgramasPFEDTO;
import es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model.CursosProgramasPFEDTO;
import es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model.ListadoPFEDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.model.ProgramasPFEDto;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.HistorialProgramaPFE;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.ProgramasPFE;
import es.jccm.edu.proyectosfct.application.ports.in.programasPFE.IProgramasPFEService;
import es.jccm.edu.proyectosfct.application.ports.in.usuarios.IParamsFCTService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.tomcat.util.json.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio programas PFE", description = "Servicio con las operaciones sobre programas PFE")

public class ProgramasPFERestController {

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    IParamsFCTService iParamsFCTService;

    @Autowired
    IProgramasPFEService programasPFEService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     *  Listado de codigos para pantalla de anexo V
     * @param idCentro
     * @param cAnno
     * @param idPerfil
     * @param idUsuario
     * @param jwt
     * @return
     * @throws ParseException
     */
    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Listado de los combos para programasPFE", description = "Este metodo devuelve un listado de los combos para programasPFE",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getListadoCombosProgramasPFE/{cbNombre}/{idCentro}/{cAnno}/{idPerfil}/{idUsuario}/{idCentroCombo}/{idProvincia}/{idCodigo}")
    public ResponseEntity<List<CombosProgramasPFEDTO>> getListadoCombosProgramasPFE(
            @PathVariable("cbNombre") String cbNombre,
            @PathVariable("idCentro") Long idCentro,
            @PathVariable("cAnno") Integer cAnno,
            @PathVariable("idPerfil") Long idPerfil,
            @PathVariable("idUsuario") Long idUsuario,
            @PathVariable("idCentroCombo") Long idCentroCombo,
            @PathVariable("idProvincia") Integer idProvincia,
            @PathVariable("idCodigo") Long idCodigo,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {

        try {
        	
        	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

            List<CombosProgramasPFEDTO> listadoCombosOut = programasPFEService
                    .getCombosProgramasPFE(cbNombre,idCentro,cAnno, idPerfil,idUsuario,  idCentroCombo,idProvincia, datosUsuario.getXUsuarioDelphos(), idCodigo )
                    .stream()
                    .map(projection -> modelMapper.map(projection, CombosProgramasPFEDTO.class))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(listadoCombosOut, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Listado de cursos para pantalla de listado de anexo V
     *
     * @param idCentroCombo
     * @param idFamilia
     * @param idProvincia
     * @param idUsuario
     * @param idPerfil
     * @param idCentro
     * @param jwt
     * @return
     * @throws ParseException
     */
    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Listado cursos ProgramasPFE", description = "Este metodo devuelve un listado de cursos para ProgramasPFE",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getListadoCursosProgramasPFE/{idCentroCombo}/{idFamilia}/{idProvincia}/{idUsuario}/{idPerfil}/{idCentro}")
    public ResponseEntity<Object> getListadoCentrosProgramasPFE(
            @PathVariable("idCentroCombo") Long idCentroCombo,
            @PathVariable("idFamilia") Long idFamilia,
            @PathVariable("idProvincia") Integer idProvincia,
            @PathVariable("idUsuario") Long idUsuario,
            @PathVariable("idPerfil") Long idPerfil,
            @PathVariable("idCentro") Long idCentro,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {

        try {

        	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
        	
            List<CursosProgramasPFEDTO> listadoCursosOut = programasPFEService
                    .getCursosProgramasPFE(idCentroCombo, idFamilia, idProvincia, datosUsuario.getXUsuarioDelphos(), idPerfil, idCentro)
                    .stream()
                    .map(curso -> modelMapper.map(curso, CursosProgramasPFEDTO.class))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(listadoCursosOut, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    /**
     * Listado de cursos para pantalla de listado de anexo V
     *
     * @param idCentroCombo
     * @param idFamilia
     * @param idProvincia
     * @param idUsuario
     * @param idPerfil
     * @param idCentro
     * @param jwt
     * @return
     * @throws ParseException
     */
    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Listado creadores cursos ProgramasPFE", description = "Este metodo devuelve un listado de los creadore cursos para ProgramasPFE",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getListadoCreadoresProgramasPFE/{idCentroCombo}/{idFamilia}/{idProvincia}/{idUsuario}/{idPerfil}/{idCentro}/{idCurso}/{idModalidad}")
    public ResponseEntity<List<ElementoSelectDto>> getListadoCreadoresProgramasPFE(
            @PathVariable("idCentroCombo") Long idCentroCombo,
            @PathVariable("idFamilia") Long idFamilia,
            @PathVariable("idProvincia") Integer idProvincia,
            @PathVariable("idUsuario") Long idUsuario,
            @PathVariable("idPerfil") Long idPerfil,
            @PathVariable("idCentro") Long idCentro,
            @PathVariable("idCurso") Long idCurso,
            @PathVariable("idModalidad") Long idModalidad,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {

        try {            
            
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			
		      List<ElementoSelectDto> creadoresOut = programasPFEService.getListadoCreadoresProgramasPFE(idCentroCombo, 
																				                      idFamilia, 
																				                      idProvincia, 
																				                      idUsuario, 
																				                      idPerfil, 
																				                      idCentro, 
																				                      idCurso,
																				                      idModalidad,
																				                      datosUsuario.getXUsuarioDelphos())
	                    .stream()
	                    .map(curso -> modelMapper.map(curso, ElementoSelectDto.class))
	                    .collect(Collectors.toList());

	            return new ResponseEntity<>(creadoresOut, HttpStatus.OK);            
            

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    
    @Operation(summary = "Listado programas formativos de empresa", description = "Este metodo devuelve una listado de programas formativos de empresa",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("/getListadoPFE/{idCentro}/{cAnno}/{idCurso}/{idPerfil}/{idCentroCombo}/{idProvincia}/{idUsuCrea}/{idModalidad}/{idFamilia}/{idCodigo}/{lgVigente}/{esDirector}/{idEstado}/{reqAutorizacion}")
		public ResponseEntity<List<ListadoPFEDto>> getListadoPFE(@PathVariable("idCentro") Long idCentro, 
																		@PathVariable("cAnno") Integer cAnno,																					 
																		@PathVariable("idCurso") Long idCurso,																		
																		@PathVariable("idPerfil") Long idPerfil,
																	    @PathVariable("idCentroCombo") Long idCentroCombo,
																		@PathVariable("idProvincia") Long idProvincia,
																		@PathVariable("idUsuCrea") Long idUsuCrea,
																		@PathVariable("idModalidad") Long idModalidad,
																		@PathVariable("idFamilia") Long idFamilia,
																		@PathVariable("idCodigo") Long idCodigo,
																        @PathVariable("lgVigente") Integer lgVigente,
																		@PathVariable("esDirector") Integer esDirector,
																		@PathVariable("idEstado") Integer idEstado,
                                                                        @PathVariable("reqAutorizacion") Integer reqAutorizacion,
																		@RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException{
			
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);		
			
			List<ListadoPFEDto> listadoPFEDto =  programasPFEService.getListadoPFE(idCentro, 
																				   cAnno,																									   
																				   idCurso,																				 
																				   idPerfil,
																				   idCentroCombo,
																				   idProvincia,
																				   datosUsuario.getXUsuarioDelphos(),																				   
																				   idUsuCrea,
																				   idModalidad,
																				   idFamilia,
																				   idCodigo,
					                                                               lgVigente,
					                                                               esDirector,
					                                                               idEstado,
                                                                                   reqAutorizacion
																				   );

					
			return new ResponseEntity<List<ListadoPFEDto>>(listadoPFEDto, HttpStatus.OK);
		}
    
    
  //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
  	@Operation(summary = "Parsem delete mejora y observacion a nivel global", description = "Este metodo elimina una mejora y observación a nivel global",
  			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
  	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
  			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
  			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
  			@ApiResponse(responseCode = "404", description = "No encontrado")})
  	@DeleteMapping("/deleteAnexoV/{id}")
  	public ResponseEntity<HttpStatus> deletePrograma(@PathVariable("id") Long id) {
  		try {
  			programasPFEService.deletePrograma(id);

  		} catch (Exception e) {
  			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  		}
  		return new ResponseEntity<>(HttpStatus.OK);
  	}

	@Operation(summary = "", description = "",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PostMapping("/postVistoBuenoPFE/{cPerfil}")
	public ResponseEntity postVistoBuenoPFE(@RequestHeader(Constants.AUTHORIZATION)
											String jwt, @RequestBody List<Long>  lista,
											@PathVariable ("cPerfil") String cPerfil) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);


		try {


			programasPFEService.postVistoBuenoPFE(lista, datosUsuario.getXUsuarioDelphos(),cPerfil);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido realizar la consulta");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Get autorizacion extraordinario.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get anexo V", description = "Este metodo devuelve el anexo V",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getPfeById/{idProgPerFor}")
	public ResponseEntity<ProgramasPFEDto> getPfeById(@PathVariable("idProgPerFor") Long idProgPerFor){

		ProgramasPFE programasPFeIn =  programasPFEService.getPfeById(idProgPerFor);

		ProgramasPFEDto autorizacionExtraordinarioOut = modelMapper.map(programasPFeIn, ProgramasPFEDto.class);

		return new ResponseEntity<>(autorizacionExtraordinarioOut, HttpStatus.OK);
	}
	
	/**
	 * Upload fichero para la autorizacion del extraordinario.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Upload fichero anexo FCT extraordinario", description = "Upload fichero anexo FCT extraordinario", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/uploadAdjuntoPFE/{xCentro}/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> uploadAdjunto(@PathVariable("xCentro") Long xCentro,
			                                    @PathVariable("id") Long id,				                                    
												@RequestPart(value = "file", required = false) MultipartFile file) {		
		
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			programasPFEService.uploadAdjuntoPFE(xCentro,id,file);
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/** 
	 * Get autorizacion desplazamiento.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Get autorizacion anexo", description = "Este metodo devuelve la autorizacion anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAdjuntoAnexoHistorial/{idHistorial}")
	public ResponseEntity<HistorialProgramaPFE> getAutorizacionAnexoHistorial(@PathVariable("idHistorial") Long idHistorial){
		
		HistorialProgramaPFE anexoIn =  programasPFEService.getAdjuntoAnexoHistorial(idHistorial);		
				
		return new ResponseEntity<>(anexoIn, HttpStatus.OK);
	}
	
	
	

}
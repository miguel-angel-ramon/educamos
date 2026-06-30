package es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial;


import es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model.ListadoAltasSegSocialDto;

import es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model.ListadoHistoricoAltasDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model.NuevoPeriodoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model.RegisterTraineeStudentsGroupDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.datosgestora.model.DatosGestoraDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.application.domain.altassegsocial.entities.ListadoAltasSegSocial;
import es.jccm.edu.proyectosfct.application.domain.altassegsocial.projection.ListadoHistoricoAltasProjection;
import es.jccm.edu.proyectosfct.application.domain.datosgestora.entities.DatosGestora;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.ports.in.altassegsocial.IAltasSegSocialService;
import es.jccm.edu.proyectosfct.application.ports.in.datosgestora.IDatosGestoraService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.domain.segsocial.entities.RegisterTraineeStudent;
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
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Programas FCT", description = "Servicio para dar de alta a un alumno en la Seguridad Social FCT")
public class AltasSegSocialRestController {

    @Autowired
    private IAltasSegSocialService altasSegSocialService;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	private IDatosGestoraService datosGestoraService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Lista datos.
     *
     * @return the response entity
     * @throws ParseException
     */
    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Listado alumnos tutor", description = "Este metodo devuelve una listado de alumnos de un tutor",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getListadoAltasSegSocial/{idTutorfctdual}/{idCentro}/{cAnno}/{tipoEmpresa}/{idEmpresa}/{idOfertamatrig}/{idUnidad}/{idPerfil}/{idCentroCombo}/{idProvincia}/{idEstado}")
    public ResponseEntity<List<ListadoAltasSegSocial>> getListadoAltasSegSociProg(@PathVariable("idTutorfctdual") Long idTutorfctdual,
                                                                                     @PathVariable("idCentro") Long idCentro,
                                                                                     @PathVariable("cAnno") Integer cAnno,
                                                                                     @PathVariable("tipoEmpresa") Integer tipoEmpresa,
                                                                                     @PathVariable("idEmpresa") Long idEmpresa,
                                                                                     @PathVariable("idOfertamatrig") Long idOfertamatrig,
                                                                                     @PathVariable("idUnidad") Long idUnidad,
                                                                                     @PathVariable("idPerfil") Long idPerfil,
                                                                                     @PathVariable("idCentroCombo") Long idCentroCombo,
                                                                                     @PathVariable("idProvincia") Long idProvincia,
                                                                                     @PathVariable("idEstado") Integer idEstado,
                                                                                     @RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException{

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        List<ListadoAltasSegSocial> listadoAlumnadoTutor =  altasSegSocialService.getListadoAltasSegSocial(idTutorfctdual,
																						                   idCentro,
																						                   cAnno,
																						                   tipoEmpresa,
																						                   idEmpresa,
																						                   idOfertamatrig,
																						                   idUnidad,
																						                   idPerfil,
																						                   idCentroCombo,
																						                   idProvincia,
																						                   idEstado,
																						                   datosUsuario.getXUsuarioDelphos());

        //List<ListadoAltasSegSocialDto> listadoAltasSegSocial = listadoAlumnadoTutor.stream().map(x -> modelMapper.map(x, ListadoAltasSegSocialDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(listadoAlumnadoTutor, HttpStatus.OK);
    }

	/**
	 * Creación de los Datos de Alumno Convenio Programa.
	 *
	 * @param listadoAltasSegSocialDto Datos de Alumno Convenio Programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Datos Altas de la Seguridad Social de alumnos", description = "Este metodo crea o actualiza los datos de la seguridad social de un alumno de programa o proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/datosListadoAltasSegSocial")
	public ResponseEntity<Object> datosListadoAltasSegSocial(@RequestBody final List<ListadoAltasSegSocialDto> listadoAltasSegSocialDto,
															 @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			List<ListadoAltasSegSocial> listadoAltasSegSocialIn = listadoAltasSegSocialDto.stream().map(x -> modelMapper.map(x, ListadoAltasSegSocial.class)).collect(Collectors.toList());

			altasSegSocialService.modifyDataAltasSegSocial(listadoAltasSegSocialIn, datosUsuario.getXUsuarioDelphos());

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
	/**
	 * Combos cursos anexo anexo.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista cursos anexo", description = "Este metodo devuelve una lista de cursos anexo",
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEstadosSS/{idPerfil}")
	public ResponseEntity<List<ElementoSelectDto>> getEstadosSS(@PathVariable("idPerfil") Long idPerfil ){
		
	
		List<ElementoSelect> estados =  altasSegSocialService.getEstadosSS(idPerfil);
		
		List<ElementoSelectDto> estadosDto = estados.stream().map(x -> modelMapper.map(x, ElementoSelectDto.class)).collect(Collectors.toList());
				
		return new ResponseEntity<>(estadosDto, HttpStatus.OK);
	}



	/**
	 * Combos cursos anexo anexo.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista cursos anexo", description = "Este metodo devuelve una lista de cursos anexo",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/resetEstadoValidar")
	public ResponseEntity<Object> resetEstadoValidar(@RequestParam Long id,
													 @RequestParam Long idPerfil,
													 @RequestParam String tipoEmpresa){

		try {

			altasSegSocialService.resetEstadoValidar(id, idPerfil, tipoEmpresa);

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
    
    /**
     * Creación de los Datos de Alumno Convenio Programa.
     *
     * @param registerTraineeStudentDto Datos de Alumno Convenio Programa
     * @return the response entity
     */
    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Envio de altas seguridad social empresa", description = "Este metodo envia todo el alumnado recibido a la empresa tramitadora de las altas", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/envioAltasSSEmpresa")
    public ResponseEntity<Integer> envioAltasSSEmpresa(@RequestBody final RegisterTraineeStudentsGroupDto registerTraineeStudentsGroupDto,
                                                       @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
        	modelMapper.getConfiguration().setAmbiguityIgnored(true);
        	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        	List<RegisterTraineeStudent> registerTraineeStudents = null;
        	List<RegisterTraineeStudent> updateTraineeStudents = null;
        	List<RegisterTraineeStudent> cancelledTraineeStudents = null;
        	
        	if (registerTraineeStudentsGroupDto.getRegisterTraineeStudentDtos().size()>0) registerTraineeStudents = registerTraineeStudentsGroupDto.getRegisterTraineeStudentDtos().stream().map(entity -> modelMapper.map(entity, RegisterTraineeStudent.class)).collect(Collectors.toList());

        	if (registerTraineeStudentsGroupDto.getUpdateTraineeStudentDtos().size()>0) updateTraineeStudents = registerTraineeStudentsGroupDto.getUpdateTraineeStudentDtos().stream().map(entity -> modelMapper.map(entity, RegisterTraineeStudent.class)).collect(Collectors.toList());

        	if (registerTraineeStudentsGroupDto.getCancellTraineeStudentDtos().size()>0) cancelledTraineeStudents = registerTraineeStudentsGroupDto.getCancellTraineeStudentDtos().stream().map(entity -> modelMapper.map(entity, RegisterTraineeStudent.class)).collect(Collectors.toList());

            Integer enviados = altasSegSocialService.envioAltasSSEmpresa(registerTraineeStudents,
            															 updateTraineeStudents, 
            															 cancelledTraineeStudents,
            		                                                     datosUsuario.getXUsuarioDelphos());

            return new ResponseEntity<Integer>(enviados,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/**
	 * Combos cursos anexo anexo.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista cursos anexo", description = "Este metodo devuelve una lista de cursos anexo",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getListadoHistorico/{idAltass}/{tipoEmpresa}/{dniAlu}/{idInterno}")
	public ResponseEntity<List<ListadoHistoricoAltasDto>> getListadoHistorico(@PathVariable("idAltass") Long idAltass,
																		      @PathVariable("tipoEmpresa") String tipoEmpresa,
																			  @PathVariable("dniAlu") String nif,
																			  @PathVariable("idInterno") Long idInterno) throws Exception {

		List<ListadoHistoricoAltasProjection> listadoHistoricoAltas =  altasSegSocialService.getListadoHistoricoAltas(idAltass, tipoEmpresa);

		List<ListadoHistoricoAltasDto> listadoHistoricoAltasDtosOut = datosGestoraService.getListadoHistoricoAltas(listadoHistoricoAltas, nif, idAltass, tipoEmpresa, idInterno);

		return new ResponseEntity<>(listadoHistoricoAltasDtosOut, HttpStatus.OK);
	}

	/**
	 * Creación del histórico de los Datos de Alumno Convenio Programa.
	 *
	 * @param listadoHistoricoAltasDtoIn Datos de Alumno Convenio Programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Historico de los datos Aatas de la Seguridad Social de alumnos", description = "Este metodo crea o actualiza el historico de los datos de la seguridad social de un alumno de programa o proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/updateHistoricoAltas")
	public ResponseEntity<Object> updateHistoricoAltas(@RequestBody final ListadoHistoricoAltasDto listadoHistoricoAltasDtoIn,
															 @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			altasSegSocialService.modifyHistoricoAltas(listadoHistoricoAltasDtoIn, datosUsuario.getXUsuarioDelphos());

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Parsem delete mejora y observacion a nivel global", description = "Este metodo elimina una mejora y observación a nivel global",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@DeleteMapping("/deleteAlumAltaSegSocial")
	public ResponseEntity<Object> deleteAlumAltaSegSocial(@RequestParam("id") Long idAltasSS,
														  @RequestParam("tipo") String tipo) {
		try {
			altasSegSocialService.deleteAlumnoAltas(idAltasSS, tipo);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}



	/**
	 * Combos cursos anexo anexo.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Lista datos gestora por alumno", description = "Este metodo devuelve una lista de los datos de la seguridad social aportados por la gestora de un alumno en concreto",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getDatosGestoraByAlumno/{nif}/{id}/{tipo}/{idInterno}")
	public ResponseEntity<List<DatosGestoraDto>> getDatosGestoraByAlumno(@PathVariable("nif") String nif,
																		 @PathVariable("id") Long idAlu,
																		 @PathVariable("tipo") String tipo,
																		 @PathVariable("idInterno") Long idInterno){

		try {

			List<DatosGestora> listadoDatosGestoraByAlumno = datosGestoraService.getDatosGestoraByAlumno(nif, idAlu, tipo, idInterno);

			List<DatosGestoraDto> listadoDatosGestoraByAlumnoOut = listadoDatosGestoraByAlumno.stream().map(x -> modelMapper.map(x, DatosGestoraDto.class)).collect(Collectors.toList());

			return new ResponseEntity<>(listadoDatosGestoraByAlumnoOut, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

    
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "Revertir ultimo cambio", description = "Este metodo permite revertir el último cambio que se ha realizado sobre un alumno en la pantalla de Alta Seguridad Social",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/revertEstadoAnulado")
	public ResponseEntity<Object> revertEstadoAnulado(@RequestParam("idAlu") Long idAlu,
													  @RequestParam("nuPeticion") String nuPeticion){

		try {

			altasSegSocialService.revertEstadoAnulado(idAlu, nuPeticion);

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * Creación del histórico de los Datos de Alumno Convenio Programa.
	 *
	 * @param listadoHistoricoAltasDtoIn Datos de Alumno Convenio Programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
	@Operation(summary = "Historico de los datos Aatas de la Seguridad Social de alumnos", description = "Este metodo crea o actualiza el historico de los datos de la seguridad social de un alumno de programa o proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/postAddPeriodoSS")
	public ResponseEntity<Object> postAddPeriodoSS(@RequestBody final NuevoPeriodoDto nuevoPeriodoDtoIn,
															 @RequestHeader(Constants.AUTHORIZATION) String jwt) {
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

			altasSegSocialService.nuevoPeriodoAltas(nuevoPeriodoDtoIn, datosUsuario.getXUsuarioDelphos());

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Borrado lógico de alumnos.
	 *
	 * @return the response entity
	 */
	@Operation(summary = "Borrado lógico de alumnos", description = "Este método hace un borrado lógico de alumnos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/postExcludeStudent/{idConvProxAlu}/{tipoEmpresa}")
	public ResponseEntity<Object> postExcluirAlumno(@PathVariable("idConvProxAlu") Long idConvProxAlu,
													@PathVariable("tipoEmpresa") String tipoEmpresa) {
		try {
			altasSegSocialService.excluirAlumno(idConvProxAlu, tipoEmpresa);

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

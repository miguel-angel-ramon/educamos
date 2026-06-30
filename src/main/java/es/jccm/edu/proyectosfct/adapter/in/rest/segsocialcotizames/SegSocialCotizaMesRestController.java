package es.jccm.edu.proyectosfct.adapter.in.rest.segsocialcotizames;

import es.jccm.edu.proyectosfct.adapter.in.rest.datosgestorames.model.DatosGestoraMesDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.segsocialcotizames.model.ListadoHistoricoMesDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.segsocialcotizames.model.ListadoSegSocialCotizaMesDto;
import es.jccm.edu.proyectosfct.application.domain.datosgestorames.entities.DatosGestoraMes;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ElementoSelect;
import es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.entities.ListadoSegSocialCotizaMes;
import es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.projection.ListadoHistoricoCotizaMesProjection;
import es.jccm.edu.proyectosfct.application.domain.segsocialcotizames.projection.ListadoSegSocialCotizaMesProjection;
import es.jccm.edu.proyectosfct.application.ports.in.datosgestorames.IDatosGestoraMesService;
import es.jccm.edu.proyectosfct.application.ports.in.segsocicotizames.ISegSociCotizaMesService;
import es.jccm.edu.shared.adapter.in.rest.segsocial.model.RegisterDaysContributedDto;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.domain.segsocial.entities.RegisterDaysContributed;
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
@Tag(name = "Servicio Programas FCT", description = "Servicio para dar de alta a un alumno en un mes en concreto en la Seguridad Social FCT")
public class SegSocialCotizaMesRestController {

    @Autowired
    private ISegSociCotizaMesService segSocialCotizaMesService;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    private IDatosGestoraMesService datosGestoraMesService;

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
    @GetMapping("/getListadoCotizaMes/{idTutorfctdual}/{idCentro}/{cAnno}/{tipoEmpresa}/{idEmpresa}/{idOfertamatrig}/{idUnidad}/{idPerfil}/{idCentroCombo}/{idProvincia}/{idEstado}/{nuMes}/{idTipo}")
    public ResponseEntity<List<ListadoSegSocialCotizaMes>> getListadoCotizaMes(@PathVariable("idTutorfctdual") Long idTutorfctdual,
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
                                                                                     @PathVariable("nuMes") Integer nuMes,
                                                                                     @PathVariable("idTipo") Integer idTipo,
                                                                                     @RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException{

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

//        List<ListadoSegSocialCotizaMes> listadoAlumnadoTutor =  segSocialCotizaMesService.getListadoCotizaMes(idTutorfctdual,
//                idCentro,
//                cAnno,
//                tipoEmpresa,
//                idEmpresa,
//                idOfertamatrig,
//                idUnidad,
//                idPerfil,
//                idCentroCombo,
//                idProvincia,
//                idEstado,
//                nuMes,
//                datosUsuario.getXUsuarioDelphos());

        List<ListadoSegSocialCotizaMes> listadoAlumnadoTutor =  segSocialCotizaMesService.getListadoCotizaMes(idTutorfctdual,
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
                nuMes,
                datosUsuario.getXUsuarioDelphos(),
                idTipo);

        //List<ListadoSegSocialCotizaMesDto> listadoAltasSegSocial = listadoAlumnadoTutor.stream().map(x -> modelMapper.map(x, ListadoSegSocialCotizaMesDto.class)).collect(Collectors.toList());

        //return new ResponseEntity<>(listadoAltasSegSocial, HttpStatus.OK);
        
        return new ResponseEntity<>(listadoAlumnadoTutor, HttpStatus.OK);
    }

    /**
     * Creación de los Datos de Alumno Convenio Programa.
     *
     * @param listadoSegSocialCotizaMesDto Datos de Alumno Convenio Programa
     * @return the response entity
     */
    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Datos Altas de la Seguridad Social de alumnos por mes", description = "Este metodo crea o actualiza los datos de la seguridad social de un alumno de programa o proyecto de un mes", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/createUpdateListadoCotizaMes")
    public ResponseEntity<Object> createUpdateListadoCotizaMes(@RequestBody final List<ListadoSegSocialCotizaMesDto> listadoSegSocialCotizaMesDto,
                                                             @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

            List<ListadoSegSocialCotizaMes> listadoSegSocialCotizaMesIn = listadoSegSocialCotizaMesDto.stream().map(x -> modelMapper.map(x, ListadoSegSocialCotizaMes.class)).collect(Collectors.toList());

            segSocialCotizaMesService.createUpdateListadoCotizaMes(listadoSegSocialCotizaMesIn, datosUsuario.getXUsuarioDelphos());

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
    @GetMapping("/getEstadosMesSS/{idPerfil}")
    public ResponseEntity<List<ElementoSelectDto>> getEstadosMesSS(@PathVariable("idPerfil") Long idPerfil ){


        List<ElementoSelect> estados =  segSocialCotizaMesService.getEstadosSS(idPerfil);

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
	@PostMapping("/resetEstadoValidarMes")
	public ResponseEntity<Object> resetEstadoValidarMes(@RequestParam Long id,
													 @RequestParam Long idPerfil,
													 @RequestParam String tipoEmpresa){

		try {

			segSocialCotizaMesService.resetEstadoValidarMes(id, idPerfil, tipoEmpresa);

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
    /**
     * Creación de los Datos de Alumno Convenio Programa.
     *
     * @param registerDaysContributedDto Datos de Alumno Convenio Programa
     * @return the response entity
     */
    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Envio de altas seguridad social empresa", description = "Este metodo envia todo el alumnado recibido a la empresa tramitadora de las altas", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/envioCotizacionesMensuales")
    public ResponseEntity<Integer> envioCotizacionesMensuales(@RequestBody final List<RegisterDaysContributedDto> registerDaysContributedDto,
                                                              @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
        	modelMapper.getConfiguration().setAmbiguityIgnored(true);
        	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

            List<RegisterDaysContributed> registerDaysContributed = registerDaysContributedDto.stream().map(entity -> modelMapper.map(entity, RegisterDaysContributed.class)).collect(Collectors.toList());

            Integer enviados = segSocialCotizaMesService.envioCotizacionesMensuales(registerDaysContributed, datosUsuario.getXUsuarioDelphos());

            return new ResponseEntity<Integer>(enviados,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
    @Operation(summary = "Lista cursos anexo", description = "Este metodo devuelve una lista de cursos anexo",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getListadoHistoricoMes")
    public ResponseEntity<List<ListadoHistoricoMesDto>> getListadoHistoricoMes(@RequestParam() Long idCotizaMes,
                                                                                 @RequestParam() String tipo){

        List<ListadoHistoricoCotizaMesProjection> listadoHistoricoCotizaMes =  segSocialCotizaMesService.getListadoHistoricoMes(idCotizaMes, tipo);

        List<ListadoHistoricoMesDto> listadoHistoricoAltasDtosOut = listadoHistoricoCotizaMes.stream().map(x -> modelMapper.map(x, ListadoHistoricoMesDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(listadoHistoricoAltasDtosOut, HttpStatus.OK);
    }


    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Historico de los datos Aatas de la Seguridad Social de alumnos", description = "Este metodo crea o actualiza el historico de los datos de la seguridad social de un alumno de programa o proyecto", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/updateHistoricoMes")
    public ResponseEntity<Object> updateHistoricoMes(@RequestBody final ListadoHistoricoMesDto listadoHistoricoMesDtoIn,
                                                     @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

            segSocialCotizaMesService.modifyHistoricoMes(listadoHistoricoMesDtoIn, datosUsuario.getXUsuarioDelphos());

            return new ResponseEntity<>(HttpStatus.OK);

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
    @PostMapping("/revertUltimoEstado")
    public ResponseEntity<Object> revertUltimoEstado(@RequestParam("idCotizaMes") Long idCotizaMes,
                                                     @RequestParam("nuPeticion") String nuPeticion){

        try {

            segSocialCotizaMesService.revertUltimoEstado(idCotizaMes, nuPeticion);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    //@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
    @Operation(summary = "Listado de datos gestora mensual por alumno", description = "Servicio que devuelve un lista de los datos mensuales de la seguridad social aportados por la gestora de un alumno en concreto",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getDatosGestoraMesByAlumno")
    public ResponseEntity<List<DatosGestoraMesDto>> getDatosGestoraMesByAlumno(@RequestParam("nif") String nif,
                                                                               @RequestParam("nuMes") Integer nuMes,
                                                                               @RequestParam("anno") Integer anno,
                                                                               @RequestParam("id") Long id,
                                                                               @RequestParam("tipo") String tipo) {

        try {

            List<DatosGestoraMes> datosGestoraMes = datosGestoraMesService.getDatosGestoraMesByAlumno(nif, nuMes, anno, id, tipo);

            List<DatosGestoraMesDto> datosGestoraMesDtos = datosGestoraMes.stream().map( x -> modelMapper.map(x, DatosGestoraMesDto.class)).collect(Collectors.toList());

            return new ResponseEntity<>(datosGestoraMesDtos, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
    @Operation(summary = "Obtener el día límite para las liquidaciones de la SS", description = "Servicio que devuelve el día máximo permitido para las liquidaciones de la SS",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getMaxDiaLiquidacionMensual")
    public ResponseEntity<Integer> getMaxDiaLiquidacionMensual() {
        try {
            Integer limite = segSocialCotizaMesService.getMaxDiaLiquidacionMensual();

            return new ResponseEntity<>(limite, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

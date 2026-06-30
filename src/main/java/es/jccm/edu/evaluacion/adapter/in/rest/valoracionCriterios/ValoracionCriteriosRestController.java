package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios;

import es.jccm.edu.evaluacion.adapter.in.rest.BasePath;
import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.AlumnoEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.CompetenciaAlumnoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.*;
import es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model.*;
import es.jccm.edu.evaluacion.application.domain.evaluacion.*;
import es.jccm.edu.evaluacion.application.ports.in.valoracionCriterios.IValoracionCriteriosService;
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
import org.apache.commons.beanutils.ConvertUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(BasePath.EvaluacionBasePath + "/evaluacion")
@Tag(name = "Servicio evaluación", description = "")
@CrossOrigin
public class ValoracionCriteriosRestController {

    @Autowired
    IValoracionCriteriosService valoracionCriteriosService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;


    /**
     * Conectamos con la BBDD de Delphos y sacamos los alumnos de evaluacion.
     *
     * @param idUnidad
     * @param idUnidad
     * @param idPonderacion
     * @param idConvCentroOmc
     * @return List<AlumnoEvaluacionDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar alumnos de evaluación", description = "Este método devuelve los alumnos de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/alumnosEvaluacion")
    public ResponseEntity<List<AlumnoEvaluacionDto>> getMateriasUnidad(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                                       @RequestParam("idPonderacion") Long idPonderacion,
                                                                       @RequestParam("idConvCentroOmc") Long idConvCentroOmc,
                                                                       @RequestParam("fechaTomaPosesion") List<String> fechaTomaPosesion,
                                                                       @RequestParam("nivelCurricular") Long idNivelCurricular,
                                                                       @RequestParam("idUnidad") Long idUnidad,
                                                                       @RequestParam("idEmpleados") List<Long> idEmpleados,
                                                                       @RequestParam("pendiente") Optional<Boolean> pendiente,
                                                                       @RequestParam("tutor") boolean tutor,
                                                                       @RequestParam("direccion") boolean direccion) {
    	
    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        ArrayList<Long> idEmpleadosParaLlamada = new ArrayList<>(idEmpleados);

        idEmpleadosParaLlamada.add(datosUsuario.getIdEmpleadoDelphos());

        Long tutorLong =  tutor ? 1L:0L;
        Long direccionLong =  direccion ? 1L:0L;
    	
        List<AlumnoEvaluacion> alumnos = valoracionCriteriosService.getAlumnosEvaluacion(idPonderacion, idConvCentroOmc, idEmpleadosParaLlamada, fechaTomaPosesion, idNivelCurricular, idUnidad, pendiente.orElse(false), tutorLong, direccionLong);
        List<AlumnoEvaluacionDto> alumnosOut = alumnos.stream().map(alumno -> modelMapper.map(alumno, AlumnoEvaluacionDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(alumnosOut, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar alumnos de evaluación", description = "Este método devuelve los datos del alumno para la convocatoria de evaluación ",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/alumnoCompetenciasEvaluacion")
    public ResponseEntity<List<CompetenciaAlumnoDTO>> getAlumnoCompetenciaEvaluacion(@RequestParam("idConvCentroOmc") Long idConvCentroOmc,
                                                                                     @RequestParam("idMatMatri") Long idMatMatri ) {

        List<CompetenciaAlumno> alumnoCompetencias = valoracionCriteriosService.getAlumnoCompetenciasEvaluacion(idConvCentroOmc, idMatMatri);
        List<CompetenciaAlumnoDTO> alumnosCompetenciasOut = alumnoCompetencias.stream().map(alumnoComp -> modelMapper.map(alumnoComp, CompetenciaAlumnoDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(alumnosCompetenciasOut, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Guardar notas globales alumnos", description = "Este método guarda las notas globales de los alumnos en los criterios de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/trasladarResultadoEvaluacion")

    public HttpStatus trasladarResultadoEvaluacion(@RequestBody List<AlumnoEvaluacion> alumnos, @RequestParam("idCentroOmc") Long idCentroOmc) throws Exception {

        try {
            valoracionCriteriosService.trasladarResultadoEvaluacion(alumnos, idCentroOmc);
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new Exception("No se han podido trasladar las notas");
        }
    }

    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Bloquea una ponderación", description = "Bloquea una ponderación para que no se pueda editar",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/bloquearPonderacion")

    public HttpStatus bloquearPonderacion(@RequestParam("idPonderacion") Long idPonderacion) {

        try {
            valoracionCriteriosService.bloquearPonderacion(idPonderacion);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Conectamos con la BBDD de Delphos
     *
     * @return List<AnnosDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
    @Operation(summary = "Recuperar alumnos de evaluación", description = "Este método devuelve los alumnos de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/ultimosAnnos")
    public ResponseEntity<List<AnnosDto>> getUltimosAnnos() {

        List<Annos> annos = valoracionCriteriosService.getUltimosAnnos();
        List<AnnosDto> annosOut = annos.stream().map(anno -> modelMapper.map(anno, AnnosDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(annosOut, HttpStatus.OK);
    }

    /**
     * Conectamos con la BBDD de Delphos
     *
     * @return List<ConvocatoriasDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar alumnos de evaluación", description = "Este método devuelve los alumnos de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getConvocatorias")
    public ResponseEntity<List<ConvocatoriasDto>> getConvocatorias(@RequestParam("anno") Long anno,
                                                                   @RequestParam("idUnidad") Long idUnidad,
                                                                   @RequestParam("idOfertaMatrig") Long idOfertaMatrig) {

        List<Convocatorias> convocatorias = valoracionCriteriosService.getConvocatorias(anno, idUnidad, idOfertaMatrig);
        List<ConvocatoriasDto> convocatoriasOut = convocatorias.stream().map(convocatoria -> modelMapper.map(convocatoria, ConvocatoriasDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(convocatoriasOut, HttpStatus.OK);
    }

    /**
     * Conectamos con la BBDD de Delphos
     *
     * @return List<ConvocatoriasDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar alumnos de evaluación", description = "Este método devuelve los alumnos de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getConvocatoriasValoracionCriterios")
    public ResponseEntity<List<ConvocatoriasDto>> getConvocatoriasValoracionCriterios(@RequestParam("anno") Long anno,
                                                                   @RequestParam("idUnidad") Long idUnidad,
                                                                   @RequestParam("idOfertaMatrig") Long idOfertaMatrig) {

        List<Convocatorias> convocatorias = valoracionCriteriosService.getConvocatoriasValoracionCriterios(anno, idUnidad, idOfertaMatrig);
        List<ConvocatoriasDto> convocatoriasOut = convocatorias.stream().map(convocatoria -> modelMapper.map(convocatoria, ConvocatoriasDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(convocatoriasOut, HttpStatus.OK);
    }

    /**
     * Conectamos con la BBDD de Delphos
     *
     * @return List<AlumnoEvaluacionDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
    @Operation(summary = "Recuperar materias valoración", description = "Este método devuelve las materias de valoración",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/materiasValoracion")
    public ResponseEntity<List<MateriasValoracionDto>> getMateriasValoracion(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                                             @RequestParam("anno") Long anno,
                                                                             @RequestParam("idCentro") Long idCentro,
                                                                             @RequestParam(value = "idUnidades", required = false) List<Long> idUnidades,
                                                                             @RequestParam("idOfertamatrig") Optional<Long> idOfertamatrig
                                                                             ) {

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        List<MateriasValoracion> materiasValoracion = null;

        materiasValoracion = valoracionCriteriosService.getMateriasValoracion(datosUsuario.getIdEmpleadoDelphos(), anno, idCentro, idUnidades, idOfertamatrig.orElse(null));

        List<MateriasValoracionDto> materiasValoracionOut = materiasValoracion.stream().map(materia -> modelMapper.map(materia, MateriasValoracionDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(materiasValoracionOut, HttpStatus.OK);
    }

    /**
     * Conectamos con la BBDD de Delphos
     *
     * @return List<AlumnoEvaluacionDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
    @Operation(summary = "Recuperar unidades valoración", description = "Este método devuelve las unidades de valoración",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/unidadesValoracion")
    public ResponseEntity<List<UnidadesValoracionDto>> getUnidadesValoracion(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                                             @RequestParam("anno") Long anno,
                                                                             @RequestParam("idMateria") Long idMateria,
                                                                             @RequestParam("idCentro") Long idCentro,
                                                                             @RequestParam("idDocentes") List<Long> idDocentes,
                                                                             @RequestParam("idProgdidac") Optional<Long> idProgdidac,
                                                                             @RequestParam("tutor") boolean tutor,
                                                                             @RequestParam("direccion") boolean direccion) {


        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
        
        idDocentes.add(datosUsuario.getIdEmpleadoDelphos());


        List<UnidadesValoracion> unidadesValoracion = null;

        if(idProgdidac.isPresent()){
            unidadesValoracion = valoracionCriteriosService.getUnidadesValoracion(idDocentes, anno, idMateria, idCentro, idProgdidac.get(), tutor, direccion);
        } else {
            unidadesValoracion = valoracionCriteriosService.getUnidadesValoracion(idDocentes, anno, idMateria, idCentro, (long) -1, tutor, direccion);
        }

        List<UnidadesValoracionDto> unidadesValoracionOut = unidadesValoracion.stream().map(unidad -> modelMapper.map(unidad, UnidadesValoracionDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(unidadesValoracionOut, HttpStatus.OK);
    }

    /**
     * Conectamos con la BBDD de Delphos
     *
     * @return List<SistemaCalificacionCuaDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
    @Operation(summary = "Recuperar sistema de calificación", description = "Este método devuelve los sistemas de calificación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/sistemaCalificacion")
    public ResponseEntity<List<SistemaCalificacionCuaDto>> sistemaCalificacion(@RequestParam("idEtapa") Long idEtapa) {

        List<SistemaCalificacionCua> sistemaCalificaciones = valoracionCriteriosService.sistemaCalificacion(idEtapa);
        List<SistemaCalificacionCuaDto> sistemaCalificacionesOut = sistemaCalificaciones.stream().map(calificacion -> modelMapper.map(calificacion, SistemaCalificacionCuaDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(sistemaCalificacionesOut, HttpStatus.OK);
    }

    /**
     * Conectamos con la BBDD de Delphos
     *
     * @return List<SistemaCalificacionCuaDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
    @Operation(summary = "Recuperar sistema de calificación", description = "Este método devuelve los sistemas de calificación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/sistemaCalificacionGlobal")
    public ResponseEntity<List<SistemaCalificacionCuaDto>> sistemaCalificacionGlobal(@RequestParam("idOfertaMatrig") Long idOfertaMatrig) {

        List<SistemaCalificacionCua> sistemaCalificaciones = valoracionCriteriosService.sistemaCalificacionGlobal(idOfertaMatrig);
        List<SistemaCalificacionCuaDto> sistemaCalificacionesOut = sistemaCalificaciones.stream().map(calificacion -> modelMapper.map(calificacion, SistemaCalificacionCuaDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(sistemaCalificacionesOut, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
    @Operation(summary = "Guardar notas alumnos", description = "Este método guarda las notas de los alumnos en los criterios de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/saveNotasAlumno")

    public ResponseEntity<AlumnoEvaluacion> saveNotasAlumno(@RequestBody AlumnoEvaluacionDTO alumno,
                                                            @RequestParam("idPonderacion") Long idPonderacion,
                                                            @RequestParam("idConvCentroOmc") Long idConvCentroOmc,
                                                            @RequestParam("idCriterio") Long idCriterio,
                                                            @RequestParam(value = "idCalifica", required = false) Long idCalifica,
                                                            @RequestParam("idSistemaCalificacion") Long idSistemaCalificacion) throws Exception {

        try {
            AlumnoEvaluacion alumn = modelMapper.map(alumno, AlumnoEvaluacion.class);
            return new ResponseEntity<>(valoracionCriteriosService.calculoNotaCriterioParaAlumnoConvocatoria(alumn, idPonderacion, idConvCentroOmc, idCriterio, idCalifica, idSistemaCalificacion), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No se han podido guardar las notas");
        }
    }
    
    /**
     * Devuelve la lista de las competencias clave.
     *
     * @return List<CompetenciaClaveDTO>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar las competencias clave", description = "Este método devuelve una lista de las competencias clave",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getCompetenciasClave")
    public ResponseEntity<List<CompetenciaClaveDTO>> getCompetenciasClave() {

        List<CompetenciaClave> competenciasClave = valoracionCriteriosService.getCompetenciasClave();
        List<CompetenciaClaveDTO> competenciasOut = competenciasClave.stream().map(x -> modelMapper.map(x, CompetenciaClaveDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(competenciasOut, HttpStatus.OK);
    }
    
    /**
     * Devuelve la lista de las competencias clave y de descriptores operativos correspondientes a un alumno.
     *
     * @return List<CompetenciaClaveDTO>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar el árbol de competencias clave", description = "Este método devuelve una lista de las competencias clave y de descriptores operativos correspondientes a un alumno",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getArbolCompetenciasClave")
    public ResponseEntity<List<CompetenciaClaveDTO>> getArbolCompetenciasClave(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idEtapa") Long idEtapa) {

        List<CompetenciaClave> competenciasClave = valoracionCriteriosService.getCompetenciasClave();
        List<CompetenciaClaveDTO> competenciasOut = new ArrayList<CompetenciaClaveDTO>();
        for(CompetenciaClave comClave : competenciasClave) {
        	List<DescriptorOperativo> descriptoresOperativos = valoracionCriteriosService.getDescriptoresOperativosByComClaveAndEtapa(comClave, idEtapa);
        	List<DescriptorOperativoDTO> descriptoresOut = descriptoresOperativos.stream().map(x -> modelMapper.map(x, DescriptorOperativoDTO.class)).collect(Collectors.toList());
        	CompetenciaClaveDTO comClaveOut = modelMapper.map(comClave, CompetenciaClaveDTO.class);
        	comClaveOut.setDescriptoresOperativos(descriptoresOut);
        	competenciasOut.add(comClaveOut);
        }

        return new ResponseEntity<>(competenciasOut, HttpStatus.OK);
    }

    /**
     * Devuelve la lista de las unidades correspondientes a un curso
     *
     * @param anno
     * @return the response entity
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Devuelve un listado de unidades del curso", description = "Devuelve un listado de unidades del curso", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/unidadesEvaluacion")
    public ResponseEntity<List<UnidadEvaluacionDTO>> getUnidadesEvaluacion(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Long anno) {
        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        List<UnidadEvaluacion> unidadesEvaluacion = valoracionCriteriosService.getUnidadesEvaluacion(datosUsuario.getIdEmpleadoDelphos(), anno);
        List<UnidadEvaluacionDTO> unidadesEvaluacionOut = unidadesEvaluacion.stream().map(unidad -> modelMapper.map(unidad, UnidadEvaluacionDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(unidadesEvaluacionOut, HttpStatus.OK);
    }

    /**
     * Devuelve la lista de alumnos breve de una unidad
     *
     * @param idUnidad
     * @return the response entity
     */
    @Operation(summary = "Lista de alumnos breve de una unidad", description = "Este metodo devuelve lista de alumnos breve de una unidad", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getAlumnosUnidad")
    public ResponseEntity<List<AlumnoEvaluacionSelDTO>> getAlumnosUnidad(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idCurso") Long idCurso,
    		@RequestParam("idUnidad") Long idUnidad, @RequestParam("idOfertamatrig") Long idOfertamatrig, @RequestParam("idConvocatoria") Optional<Long> idConvocatoria) {
        List<AlumnoEvaluacionSel> alumnos = null;
        if (idConvocatoria.isPresent()) {
            alumnos = valoracionCriteriosService.getAlumnosUnidadConvExtra(idCurso, idUnidad, idConvocatoria.get(), idOfertamatrig);
        } else {
            alumnos = valoracionCriteriosService.getAlumnosUnidad(idCurso, idUnidad, idOfertamatrig);
        }
        List<AlumnoEvaluacionSelDTO> alumnosOut = alumnos.stream().map(x -> modelMapper.map(x, AlumnoEvaluacionSelDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(alumnosOut, HttpStatus.OK);
    }

	/**
	 * Devuelve la lista de alumnos breve a partir de sus matrículas
	 * 
	 *
	 * @param idsMatAlu
	 * @return the response entity
	 */
	@Operation(summary = "Lista de alumnos breve a partir de sus matrículas", description = "Este metodo devuelve lista de alumnos breve a partir de sus matrículas", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAlumnosByMatriculas")
	public ResponseEntity<List<AlumnoEvaluacionSelDTO>> getAlumnosByMatriculas(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idsMatAlu") String idsMatAlu, @RequestParam("idEtapa") Long idEtapa) {
		List<AlumnoEvaluacionSel> alumnos = valoracionCriteriosService.getAlumnosByMatriculas(idsMatAlu, idEtapa);
		List<AlumnoEvaluacionSelDTO> alumnosOut = alumnos.stream().map(x -> modelMapper.map(x, AlumnoEvaluacionSelDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(alumnosOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve la lista de competencias clave evaluadas a partir del ciclo, la matrícula y la convocatoria
	 * 
	 *
	 * @param idEtapa
         * @param idMatricula
         * @param idConvCentroOmc
	 * @return the response entity
	 */
	@Operation(summary = "Lista de competencias clave evaluadas a partir del ciclo, la matrícula y la convocatoria", description = "Este metodo devuelve lista de competencias clave evaluadas a partir del ciclo, la matrícula y la convocatoria", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCompetenciasClaveAlumno")
	public ResponseEntity<List<CompetenciaClaveAlumnoDTO>> getCompetenciasClaveAlumno(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idEtapa") Long idEtapa, @RequestParam("idMatricula") Long idMatricula, @RequestParam("idConvCentroOmc") Long idConvCentroOmc) {
		List<CompetenciaClaveAlumno> competenciasClave = valoracionCriteriosService.getCompetenciasClaveAlumno(idEtapa, idMatricula, idConvCentroOmc);
		List<CompetenciaClaveAlumnoDTO> competenciasClaveOut = competenciasClave.stream().map(comClave -> modelMapper.map(comClave, CompetenciaClaveAlumnoDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(competenciasClaveOut, HttpStatus.OK);
	}

    /**
     * Este método guarda competencias clave evaluadas
     *
     * @param idMatricula
     * @param idConvCentroOmc
     * @return the response entity
     * @body CompetenciaClaveAlumno
     */
    @Operation(summary = "Guardado de competencias clave", description = "Este método guarda competencias clave evaluadas", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/saveCompetenciasClaveAlumno")
    public HttpStatus saveCompetenciasClaveAlumno(@RequestParam("idMatricula") Long idMatricula,
                                                  @RequestParam("idConvCentroOmc") Long idConvCentroOmc,
                                                  @RequestBody CompetenciaClaveAlumnoDTO competenciasClaveIn) {
        try {
            valoracionCriteriosService.saveCompetenciasClaveAlumno(idMatricula, idConvCentroOmc, competenciasClaveIn);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return HttpStatus.OK;
    }

    /**
     * Este método guarda competencias clave evaluadas en la matriz
     *
     * @param idMatricula
     * @param idConvCentroOmc
     * @return the response entity
     * @body CompetenciaClaveAlumno
     */
    //TODO: refactorizar en un solo endpoint
    @Operation(summary = "Guardado de competencias clave", description = "Este método guarda competencias clave evaluadas", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/saveCompetenciaClaveMatriz")
    public HttpStatus saveCompetenciaClaveMatriz(@RequestParam("idMatricula") Long idMatricula,
                                                  @RequestParam("idConvCentroOmc") Long idConvCentroOmc,
                                                  @RequestBody ValoracionCompetenciaClaveAlumno competenciasClave) {
        try {
            valoracionCriteriosService.saveCompetenciaClaveMatriz(idMatricula, idConvCentroOmc, competenciasClave);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return HttpStatus.OK;
    }
	
    /**
     * Crea tantos registros en la tabla TLREGSELDOC como ids de alumnos reciba, y posteriormente devuelve el idEjecución
     *
     * @param idsAlumnos
     * @return the response entity
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Devuelve el idEjecucion de TLREGSELDOC de los registros creados",
            description = "Devuelve el idEjecucion de TLREGSELDOC de los registros creados", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/createRegSelDocsInformeValoracionCriterios")
    public ResponseEntity<Long> createRegSelDocsInformeValoracionCriterios(@RequestParam("idsAlumnos") String idsAlumnos) {

        Long idEjecucion = valoracionCriteriosService.createRegSelDocsInformeValoracionCriterios(idsAlumnos);

        return new ResponseEntity<>(idEjecucion, HttpStatus.OK);
    }
    
    /**
	 * Devuelve la lista de competencias especificas evaluadas a partir del ciclo, la matrícula y la convocatoria
	 * 
	 *
     * @param descripcionOperativa
	 * @param etapa
     * @param idMatricula
     * @param idConvCentroOmc
	 * @return the response entity
	 */
	@Operation(summary = "Lista de competencias especificas evaluadas a partir del ciclo, la matrícula y la convocatoria", description = "Este metodo devuelve lista de competencias especificas evaluadas a partir del ciclo, la matrícula y la convocatoria", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCompetenciasEspecificas")
	public ResponseEntity<List<CompetenciaEspecificaDTO>> getCompetenciasEspecificas(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("descripcionOperativa") Long descripcionOperativa, @RequestParam("etapa") Long etapa, @RequestParam("idMatricula") Long idMatricula, @RequestParam("idConvCentroOmc") Long idConvCentroOmc) {
		List<CompetenciaEspecifica> competencias = valoracionCriteriosService.getCompetenciaEspecifica(descripcionOperativa, etapa, idMatricula, idConvCentroOmc);
		List<CompetenciaEspecificaDTO> competenciasOut = competencias.stream().map(com -> modelMapper.map(com, CompetenciaEspecificaDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(competenciasOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve la valoracion del descriptor operativo a partir de él, de la matrícula y de la convocatoria
	 * 
	 *
     * @param idDescriptorOperativo
     * @param idMatricula
     * @param idConvCentroOmc
	 * @return the response entity
	 */
	@Operation(summary = "Valoración del descriptor operativo evaluadas a partir de él, de la matrícula y de la convocatoria", description = "Este metodo devuelve una valoracion del descriptor operativo evaluado a partir de él, de la matrícula y de la convocatoria", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getValoracionDescriptorOperativoAlumno")
	public ResponseEntity<ValoracionDescriptorOperativoAlumnoDTO> getValoracionDescriptorOperativoAlumno(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idDescriptorOperativo") Long idDescriptorOperativo, @RequestParam("idEtapa") Long idEtapa, @RequestParam("idMatricula") Long idMatricula, @RequestParam("idConvCentroOmc") Long idConvCentroOmc) {
		ValoracionDescriptorOperativoAlumno valoracion = valoracionCriteriosService.getValoracionDescriptorOperativoAlumno(idDescriptorOperativo, idEtapa, idMatricula, idConvCentroOmc);
		ValoracionDescriptorOperativoAlumnoDTO valoracionOut = modelMapper.map(valoracion, ValoracionDescriptorOperativoAlumnoDTO.class);
		
		return new ResponseEntity<>(valoracionOut, HttpStatus.OK);
	}
	
	/**
	 * Devuelve si el curso pertenece a Bachillerato
     * @param idOfertamatrig
     * @param idEtapa
	 * @return SI o NO
	 */
	@Operation(summary = "", description = "", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/Bachillerato")
	public ResponseEntity<String> getBachillerato(@RequestParam("idEtapa") Long idEtapa,  @RequestParam("idOfertamatrig") Long idOfertamatrig) {
		String tipo = valoracionCriteriosService.getBachillerato(idEtapa, idOfertamatrig);
		
		return new ResponseEntity<String>('"'+ tipo + '"', HttpStatus.OK);
	}
	
	/**
     * Conectamos con la BBDD de Delphos y sacamos los alumnos de evaluacion.
     *
     * @param idUnidad
     * @param idUnidad
     * @param idPonderacion
     * @return List<AlumnoEvaluacionCalculadaDTO>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
    @Operation(summary = "Recuperar alumnos de evaluación", description = "Este método devuelve los alumnos de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/alumnosEvaluacionCalculada")
    public ResponseEntity<List<AlumnoEvaluacionCalculadaDTO>> getAlumnosEvaluacionCalculada(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                                       @RequestParam("idPonderacion") Long idPonderacion,
                                                                       @RequestParam("fechaTomaPosesion") List<String> fechaTomaPosesion,
                                                                       @RequestParam("nivelCurricular") Long idNivelCurricular,
                                                                       @RequestParam("idUnidad") Long idUnidad,
                                                                       @RequestParam("idEmpleados") List<Long> idEmpleados,
                                                                       @RequestParam("tutor") boolean tutor,
                                                                       @RequestParam("direccion") boolean direccion) {



    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
    	
    	idEmpleados.add(datosUsuario.getIdEmpleadoDelphos());
    	
        List<AlumnoEvaluacionCalculadaDTO> alumnosOut = valoracionCriteriosService.getAlumnosEvaluacionCalculada(idPonderacion, idEmpleados, fechaTomaPosesion, idNivelCurricular, idUnidad, tutor, direccion);

        return new ResponseEntity<>(alumnosOut, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
    @Operation(summary = "Guardar notas alumnos", description = "Este método guarda las notas de los alumnos en los criterios de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/trasladarCalculoConvocatoria")
    public HttpStatus trasladarCalculoConvocatoria(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idPonderacion") Long idPonderacion,
    											@RequestParam("idConvCentroOmc") Long idConvCentroOmc,
    											@RequestParam("fechaTomaPosesion") List<String> fechaTomaPosesion,
                                                @RequestParam("nivelCurricular") Long idNivelCurricular,
                                                @RequestParam("idSistemaCalifica") Long idSistemaCalifica,
                                                @RequestParam("idUnidad") Long idUnidad,
                                                @RequestParam("idEmpleados") List<Long> idEmpleados,
                                                @RequestParam("tutor") boolean tutor,
                                                @RequestParam("direccion") boolean direccion) throws Exception {
    	try {
    		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
        	
        	idEmpleados.add(datosUsuario.getIdEmpleadoDelphos());
    		
    		valoracionCriteriosService.trasladarCalculoConvocatoria(idPonderacion, idConvCentroOmc, idEmpleados, fechaTomaPosesion, idNivelCurricular, idSistemaCalifica, idUnidad, tutor, direccion);
    		return HttpStatus.OK;
    	} catch (Exception e) {
            throw new Exception("No se han podido trasladar las notas a la convocatoria seleccionada");
        }
    	
    }
    
    /**
     * Devuelve la lista de las unidades correspondientes a un curso
     *
     * @param anno
     * @return the response entity
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Devuelve un listado de unidades del curso", description = "Devuelve un listado de unidades del curso", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/unidadesEvaluacionCompClave")
    public ResponseEntity<List<UnidadEvaluacionDTO>> getUnidadesEvaluacionCompClave(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                                                    @RequestParam("fechaTomaPosesion") List<String> fechaTomaPosesion,
                                                                                    @RequestParam("idCentro") Long idCentro,
                                                                                    @RequestParam("anno") Long anno,
                                                                                    @RequestParam("idEmpleados") List<Long> idEmpleados,
                                                                                    @RequestParam("direccion") Boolean direccion) {
        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        idEmpleados.add(datosUsuario.getIdEmpleadoDelphos());

        List<UnidadEvaluacion> unidadesEvaluacion = valoracionCriteriosService.getUnidadesEvaluacionCompClave(idEmpleados, fechaTomaPosesion, idCentro, anno, direccion);
        List<UnidadEvaluacionDTO> unidadesEvaluacionOut = valoracionCriteriosService.getUnidadesEvaluacionConConvocatorias(unidadesEvaluacion, anno);

        return new ResponseEntity<>(unidadesEvaluacionOut, HttpStatus.OK);
    }



    /**
     * Devuelve la lista de un histórico de currículos escolares de alumnos de una unidad
     *
     * @param idUnidad
     * @param idConvocatoria
     * @return the response entity
     */
    @Operation(summary = "Lista de alumnos de una unidad con sus currículos escolares", description = "Este metodo devuelve lista de alumnos de una unidad con sus valoraciones curriculares", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getHistoricoAlumnosValoracionCurricular")
    public ResponseEntity<List<AlumnoValoracionDTO>> getHistoricoAlumnosValoracionCurricular(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idCurso") Long idCurso, 
    		@RequestParam("idUnidad") Long idUnidad, @RequestParam("idConvocatoria") Long idConvocatoria, @RequestParam("idEtapa") Long idEtapa, @RequestParam("idOfertamatrig") Long idOfertamatrig) {
        List<AlumnoValoracion> alumnos = valoracionCriteriosService.getHistoricoAlumnosValoracionCurricular(idCurso, idUnidad, idConvocatoria, idEtapa, idOfertamatrig);
        List<AlumnoValoracionDTO> alumnosOut = alumnos.stream().map(x -> modelMapper.map(x, AlumnoValoracionDTO.class)).collect(Collectors.toList());
        valoracionCriteriosService.setMateriasNoEvaluadas(alumnosOut);
        return new ResponseEntity<>(alumnosOut, HttpStatus.OK);
    }
    
    /**
     * Devuelve la lista de currículos escolares de alumnos de una unidad
     *
     * @param idUnidad
     * @param idEtapa
     * @return the response entity
     */
    @Operation(summary = "Lista de alumnos de una unidad con sus currículos escolares", description = "Este metodo devuelve lista de alumnos de una unidad con sus valoraciones temporales curriculares", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getAlumnosValoracionTemporalCurricular")
    public ResponseEntity<List<AlumnoValoracionAdquisicionDTO>> getAlumnosValoracionTemporalCurricular(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idUnidad") Long idUnidad, @RequestParam("idEtapa") Long idEtapa, @RequestParam("idOfertamatrig") Long idOfertamatrig) {
        try {
	    	List<AlumnoValoracionAdquisicionDTO> alumnos = valoracionCriteriosService.getAlumnosValoracionTemporalCurricular(idUnidad, idEtapa, idOfertamatrig);
	        return new ResponseEntity<>(alumnos, HttpStatus.OK);
        } catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    /**
	 * Devuelve la lista de competencias clave temporales a partir de la etapa y la matrícula
	 * 
	 *
	 * @param idEtapa
     * @param idMatricula
	 * @return the response entity
	 */
	@Operation(summary = "Lista de competencias clave evaluadas a partir del ciclo, la matrícula y la convocatoria", description = "Este metodo devuelve lista de competencias clave evaluadas a partir del ciclo, la matrícula y la convocatoria", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCompetenciasClaveTemporalesAlumno")
	public ResponseEntity<List<CompetenciaClaveAlumnoDTO>> getCompetenciasClaveTemporalesAlumno(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idMatricula") Long idMatricula, @RequestParam("idEtapa") Long idEtapa) {
		List<CompetenciaClaveAlumno> competenciasClave = valoracionCriteriosService.getCompetenciasClaveTemporalesAlumno(idMatricula, idEtapa);
		List<CompetenciaClaveAlumnoDTO> competenciasClaveOut = competenciasClave.stream().map(comClave -> modelMapper.map(comClave, CompetenciaClaveAlumnoDTO.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(competenciasClaveOut, HttpStatus.OK);
	}

    /**
     * Devuelve la lista de actividades que contienen un criterio calificado
     *
     * @param idMatriAlu
     * @param idCriterio
     * @param idPonderacion
     * @return the response entity
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO', 'C')")
    @Operation(summary = "Lista de aactividades calificadas por criterio", description = "Este metodo devuelve lista de actividades con un criterio calificado", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/ActividadesbyCriterio")
    public ResponseEntity<List<ActividadCriterioDTO>> getActividadesbyCriterio(@RequestParam("idMatriAlu") Long idMatriAlu,
                                                                       @RequestParam("idCriterio") Long idCriterio,
                                                                       @RequestParam("idPonderacion") Long idPonderacion) {
        List<ActividadCriterioDTO> actividadesbyCriterio = valoracionCriteriosService.getActividadesbyCriterio(idMatriAlu, idCriterio, idPonderacion);

        return new ResponseEntity<>(actividadesbyCriterio, HttpStatus.OK);
    }

    /**
	 * Método que devuelve cursos de la valoración de criterios
	 *
	 * @param idCentro
	 * @param anno
	 * @return List<CursoValoracionDTO>
	 */
	@Operation(summary = "Lista de cursos", description = "Este metodo devuelve una lista con todos los cursos para un centro", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/cursosValoracion")
	public ResponseEntity<List<CursoValoracionDTO>> getCursosValoracion(
			@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idCentro") Long idCentro, @RequestParam("anno") Integer anno) {

		List<CursoValoracionDTO> cursosOut = valoracionCriteriosService.getCursosValoracionByCentroAndAnno(idCentro, anno);
		return new ResponseEntity<>(cursosOut, HttpStatus.OK);

	}

	/**
     * Calcula las valoraciones de los currículos escolares de alumnos de uno o varios cursos o unidades
     *
     * @param idCentro
     * @param idsUnidad
     * @param idsOfertamatrig
     * @return the response entity
     */
	@PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P', 'PRO', 'C')")
    @Operation(summary = "Lista de alumnos de una unidad con sus currículos escolares", description = "Este metodo devuelve lista de alumnos de una unidad con sus valoraciones curriculares", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/calcularValoracionCurricularAlumnos")
    public ResponseEntity<HttpStatus> calcularValoracionCurricularAlumnos(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("idCentro") Optional<Long> idCentro, @RequestParam("anno") Optional<Integer> anno,
    		@RequestParam("idsOfertamatrig") Optional<String> idsOfertamatrig, @RequestParam("idConvocatoria") Optional<Long> idConvocatoria, @RequestBody List<UnidadConvocatoriaCursoDTO> unidadesConvocatoriasCursos) {
		try {
			Boolean calculado = false;
            if (unidadesConvocatoriasCursos != null && !unidadesConvocatoriasCursos.isEmpty() && idsOfertamatrig.isEmpty()) {
                valoracionCriteriosService.calcularValoracionCurricularUnidadesConvocatoriasCursos(unidadesConvocatoriasCursos);
            } else if (idCentro.isPresent() && anno.isPresent() && idsOfertamatrig.isPresent()) {
				valoracionCriteriosService.calcularValoracionCurricularCursos(idCentro.get(), anno.get(), Arrays.stream(idsOfertamatrig.get().split(",")).map(Long::parseLong).collect(Collectors.toList()), idConvocatoria.orElse(null));
	        } else {
	        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	/**
     * Conectamos con la BBDD de Delphos y sacamos los alumnos ACNEE que no están asignados a una programación de aula.
     *
     * @param idUnidad
     * @param idUnidad
     * @param idPonderacion
     * @param idConvCentroOmc
     * @return List<AlumnoEvaluacionDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
    @Operation(summary = "Recuperar alumnos de evaluación", description = "Este método devuelve los alumnos de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getAlumnosACNEESinProgramacionAula")
    public ResponseEntity<List<AlumnoEvaluacionDto>> getAlumnosACNEESinProgramacionAula(@RequestHeader(Constants.AUTHORIZATION) String jwt,
    																   @RequestParam("fechaTomaPosesion") String fechaTomaPosesion,
                                                                       @RequestParam("idOfertamatrig") Long idOfertamatrig,
                                                                       @RequestParam("idMateriaOmg") Long idMateriaOmg,
                                                                       @RequestParam("idCentro") Long idCentro,
                                                                       @RequestParam("anno") Integer anno,
                                                                       @RequestParam("idsEmpleadosCompartidas") Optional<String> idsEmpleadosCompartidas,
                                                                       @RequestParam("fechasTomaPosesionCompartidas") Optional<String> fechasTomaPosesionCompartidas,
                                                                       @RequestParam("tutor") boolean tutor,
                                                                       @RequestParam("direccion") boolean direccion) {
    	try {
	    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

	    	List<Long> idsEmpleado = new ArrayList<>();
	    	List<String> fechasTomaPosesion = new ArrayList<>();

	    	idsEmpleado.add(datosUsuario.getIdEmpleadoDelphos());
	    	fechasTomaPosesion.add(fechaTomaPosesion);

	    	if (idsEmpleadosCompartidas.isPresent() && fechasTomaPosesionCompartidas.isPresent()) {
	    		idsEmpleado.addAll(Arrays.asList((Long[]) ConvertUtils.convert(idsEmpleadosCompartidas.get().split(","), Long[].class)));
	    		fechasTomaPosesion.addAll(Arrays.asList(fechasTomaPosesionCompartidas.get().split(",")));
	    	}

	        List<AlumnoEvaluacion> alumnos = valoracionCriteriosService.getAlumnosACNEESinProgramacionAula(idsEmpleado, fechasTomaPosesion, idOfertamatrig, idMateriaOmg, idCentro, anno, tutor, direccion);
	        List<AlumnoEvaluacionDto> alumnosOut = alumnos.stream().map(alumno -> modelMapper.map(alumno, AlumnoEvaluacionDto.class)).collect(Collectors.toList());

	        return new ResponseEntity<>(alumnosOut, HttpStatus.OK);
    	} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO','C')")
    @Operation(summary = "Recuperar alumnos de evaluación", description = "Este método devuelve los alumnos de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getMateriasNoEvaluadasCalculoCompetenciaFinal")
    public ResponseEntity<List<String>> getMateriasNoEvaluadasCalculoCompetenciaFinal(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                                                        @RequestParam("idUnidad") Long idUnidad,
                                                                                        @RequestParam("idConvCentroOmc") Long idConvCentroOmc) {
        try {
            List<String> materias = valoracionCriteriosService.getMateriasNoEvaluadasCalculoCompetenciaFinal(idUnidad, idConvCentroOmc);

            return new ResponseEntity<>(materias, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param jwt
     * @param anno
     * @param idMateria
     * @param idCentro
     * @return
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P', 'PRO', 'C')")
    @Operation(summary = "Recuperar unidades valoración", description = "Este método devuelve las unidades de valoración",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/unidadesValoracionPendiente")
    public ResponseEntity<List<UnidadesValoracionDto>> getUnidadesValoracion(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                                             @RequestParam("anno") Integer anno,
                                                                             @RequestParam("idOfertamatrig") Long idOfertamatrig,
                                                                             @RequestParam("idMateria") Long idMateria,
                                                                             @RequestParam("idCentro") Long idCentro,
                                                                             @RequestParam("isTutor") Boolean isTutor,
                                                                             @RequestParam("tutor") boolean tutor,
                                                                             @RequestParam("direccion") boolean direccion) {


        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        List<UnidadesValoracion> unidadesValoracion = valoracionCriteriosService.getUnidadesValoracionPendiente(anno, idOfertamatrig, idMateria, idCentro, isTutor ? datosUsuario.getIdEmpleadoDelphos() : -1L, tutor, direccion);

        List<UnidadesValoracionDto> unidadesValoracionOut = unidadesValoracion.stream().map(unidad -> modelMapper.map(unidad, UnidadesValoracionDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(unidadesValoracionOut, HttpStatus.OK);
    }
}

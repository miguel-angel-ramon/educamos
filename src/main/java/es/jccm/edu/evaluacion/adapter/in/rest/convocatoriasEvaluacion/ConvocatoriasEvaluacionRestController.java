package es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion;

import es.jccm.edu.evaluacion.adapter.in.rest.BasePath;
import es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model.AlumnoConvocatoriasDto;
import es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model.AlumnoEvalConvDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model.ConvocatoriaAlumnoEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model.DatosAlumnoConvDto;
import es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model.MateriaAlumnoEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model.ObservacionConvDto;
import es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model.PonderacionConvDto;
import es.jccm.edu.evaluacion.adapter.in.rest.convocatoriasEvaluacion.model.RelacionCalificacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.DocentePonderacionDto;
import es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.PonderacionDto;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.AlumnoConvocatorias;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.AlumnoEvalConv;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.ConvocatoriaAlumnoEvaluacion;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.DatosAlumnoConv;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.MateriaAlumnoEvaluacion;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.PonderacionConv;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.RelacionCalificacion;
import es.jccm.edu.evaluacion.application.domain.ponderacion.DocentePonderacion;
import es.jccm.edu.evaluacion.application.domain.ponderacion.Ponderacion;
import es.jccm.edu.evaluacion.application.ports.in.convocatoriasEvaluacion.IConvocatoriasEvaluacionService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController

@RequestMapping(BasePath.EvaluacionBasePath + "/convocatoriasEvaluacion")
@Tag(name = "Servicio Materias Escritorio", description = "Servicio para recuperar las materias del módulo de horarios del escritorio")
@CrossOrigin
public class ConvocatoriasEvaluacionRestController {

    @Autowired
    IConvocatoriasEvaluacionService servicio;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Conectamos con la BBDD de Delphos y sacamos los alumnos de la convocatoria.
     *
     * @param idMatmatrialu
     * @return List<AlumnoConvocatoriasDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar alumnos", description = "Este metodo devuelve un objeto List con los alumnos de una materia",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/alumnosConvocatorias")
    public ResponseEntity<List<AlumnoConvocatoriasDto>> alumnosConvocatorias(@RequestParam("idMatmatrialu") String idMatmatrialu) {

        List<AlumnoConvocatorias> alumnos = servicio.alumnosEvaluacion(idMatmatrialu);
        List<AlumnoConvocatoriasDto> alumnosOut = alumnos.stream().map(x -> modelMapper.map(x, AlumnoConvocatoriasDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(alumnosOut, HttpStatus.OK);
    }

    /**
     * Conectamos con la BBDD de Delphos y sacamos los alumnos de la convocatoria.
     *
     * @param idMatmatrialu
     * @param idConvCentroOmc
     * @return List<AlumnoConvocatoriasDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar datos alumno", description = "Este metodo devuelve los datos de un alumno en convocatorias de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/datosAlumnoConvocatoria")
    public ResponseEntity<DatosAlumnoConvDto> datosAlumnoConvocatoria(@RequestParam("idMatmatrialu") Long idMatmatrialu,
                                                                      @RequestParam("idConvCentroOmc") Long idConvCentroOmc) {

        try {
            DatosAlumnoConv alumno = servicio.datosAlumnoConvocatoria(idMatmatrialu, idConvCentroOmc);
            DatosAlumnoConvDto alumnoOut = modelMapper.map(alumno, DatosAlumnoConvDto.class);

            return new ResponseEntity(alumnoOut, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<DatosAlumnoConvDto>((DatosAlumnoConvDto) null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar ponderacion", description = "Este método devuelve la ponderación con las competencias especificas y los criterios de evaluacion de competencias",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/ponderacionConvocatoria")

    public ResponseEntity<PonderacionConvDto> ponderacionConvocatoria(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                         @RequestParam("idMatMatriAlu") Long idMatMatricula) {

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        PonderacionConv ponderacion = servicio.ponderacionConvocatoria(datosUsuario.getIdEmpleadoDelphos(), idMatMatricula);
        PonderacionConvDto ponderacionOut = modelMapper.map(ponderacion, PonderacionConvDto.class);

        return new ResponseEntity<>(ponderacionOut, HttpStatus.OK);
    }
    
    /**
     * Conectamos con la BBDD de Delphos y sacamos los alumnos de la agrupación.
     *
     * @param idUnidad
     * @return List<AlumnoEvalConvDTO>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar alumnos", description = "Este metodo devuelve un objeto List con los alumnos de una agrupación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getAlumnosConvocatorias")
    public ResponseEntity<List<AlumnoEvalConvDTO>> getAlumnosEvaluacion(@RequestParam("idCurso") Long idCurso, @RequestParam("idUnidad") Long idUnidad) {

        List<AlumnoEvalConv> alumnos = servicio.getAlumnosEvaluacion(idCurso, idUnidad);
        List<AlumnoEvalConvDTO> alumnosOut = alumnos.stream().map(x -> modelMapper.map(x, AlumnoEvalConvDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(alumnosOut, HttpStatus.OK);
    }
    
    /**
     * Conectamos con la BBDD de Delphos y sacamos las materias del alumno con sus evaluaciones.
     * 
     * @param idMatricula
     * @return List<MateriaAlumnoEvaluacionDTO>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar materias", description = "Este metodo devuelve un objeto List con las materias que se evalúa un alumno",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/materiasAlumno")
    public ResponseEntity<List<MateriaAlumnoEvaluacionDTO>> getMateriasEvaluacionAlumno(@RequestParam("idMatricula") Long idMatricula,@RequestHeader(Constants.AUTHORIZATION) String jwt) {
    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
    	List<MateriaAlumnoEvaluacion> materias = servicio.getMateriasEvaluacionAlumno(idMatricula, datosUsuario.getIdEmpleadoDelphos());
    	
    	List<MateriaAlumnoEvaluacionDTO> materiasOut = materias.stream().map(x -> modelMapper.map(x, MateriaAlumnoEvaluacionDTO.class)).collect(Collectors.toList());
    	
    	return new ResponseEntity<>(materiasOut, HttpStatus.OK);
    }
    
    /**
     * Conectamos con la BBDD de Delphos y sacamos las convocatorias del alumno.
     * 
     * @param idMatricula
     * @return List<ConvocatoriaAlumnoEvaluacionDTO>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar convocatorias", description = "Este metodo devuelve un objeto List con las convocatorias del curso de un alumno",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/convocatoriasAlumno")
    public ResponseEntity<List<ConvocatoriaAlumnoEvaluacionDTO>> getConvocatoriasEvaluacionAlumno(@RequestParam("idMatricula") Long idMatricula) {
    	List<ConvocatoriaAlumnoEvaluacion> materias = servicio.getConvocatoriasEvaluacionAlumno(idMatricula);
    	
    	List<ConvocatoriaAlumnoEvaluacionDTO> materiasOut = materias.stream().map(x -> modelMapper.map(x, ConvocatoriaAlumnoEvaluacionDTO.class)).collect(Collectors.toList());
    	
    	return new ResponseEntity<>(materiasOut, HttpStatus.OK);
    }
    
    /**
     * Conectamos con la BBDD de Delphos e insertamos o actualizamos una observación de la evaluación del alumno.
     * 
     * @param observaciones
     * @return
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Settea en BBDD las observaciones de la convocatoria para un alumno", description = "Este metodo inserta o actualiza las observaciones de una convocatoria para un alumno", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/setObservacionesAlumno")
    public HttpStatus setObservacionesEvaluacionAlumno(@RequestBody ObservacionConvDto observaciones) {

		try {
			servicio.setObservacionesEvaluacionAlumno(observaciones.getId().getIdMatricula(), observaciones.getId().getIdConvCentroOmc(), observaciones.getObservaciones());

            return HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
    }
    
    /**
     * Conectamos con la BBDD de Delphos y sacamos la tabla maestra del sistema de calificaciones.
     * 
     * @param idSistCal
     * @return List<RelacionCalificacionDTO>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar relación de calificaciones", description = "Este metodo devuelve un objeto List con las notas del sistema o criterio de calificación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/relacionCalificaciones")
    public ResponseEntity<List<RelacionCalificacionDTO>> getRelacionCalificaciones(@RequestParam("idSistCal") Long idSistCal) {
    	
    	List<RelacionCalificacion> relacion = servicio.getRelacionCalificaciones(idSistCal);
    	
    	List<RelacionCalificacionDTO> relacionOut = relacion.stream().map(x -> modelMapper.map(x, RelacionCalificacionDTO.class)).collect(Collectors.toList());
    	
    	return new ResponseEntity<>(relacionOut, HttpStatus.OK);
    	
    }

}

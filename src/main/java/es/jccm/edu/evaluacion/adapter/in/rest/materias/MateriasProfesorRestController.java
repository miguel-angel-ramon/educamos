package es.jccm.edu.evaluacion.adapter.in.rest.materias;

import java.util.List;
import java.util.stream.Collectors;

import es.jccm.edu.evaluacion.adapter.in.rest.BasePath;
import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.AlumnoMateriasUnidadDTO;
import es.jccm.edu.evaluacion.application.domain.alumnoMateriasUnidad.AlumnoMateriasUnidad;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.MateriasProfesorDTO;
import es.jccm.edu.evaluacion.application.domain.materiasProfesor.MateriasProfesor;
import es.jccm.edu.evaluacion.application.ports.in.materias.IMateriasProfesorService;
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
@RequestMapping(BasePath.EvaluacionBasePath + "/materias")
@Tag(name = "Servicio Materias Escritorio", description = "Servicio para recuperar las materias del módulo de horarios del escritorio")
@CrossOrigin
public class MateriasProfesorRestController {

    @Autowired
    IMateriasProfesorService servicio;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    /**
     * Conectamos con la BBDD de Delphos y sacamos las materias de un profesor.
     *
     * @param anno
     * @return List<MateriaListDto>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar materias de un profesor", description = "Este metodo devuelve un objeto List con las materias de un profesor",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/materiasprofesor")
    public ResponseEntity<List<MateriasProfesorDTO>> getMateriasProfesor(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                                         @RequestParam("anno") Integer anno) {

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        List<MateriasProfesor> materiasList = servicio.getMaterias(datosUsuario.getIdEmpleadoDelphos(), anno);
        List<MateriasProfesorDTO> materiasOut = materiasList.stream().map(x -> modelMapper.map(x, MateriasProfesorDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(materiasOut, HttpStatus.OK);
    }

    /**
     * Conectamos con la BBDD de Delphos y sacamos las materias de un profesor.
     *
     * @param idMateria
     * @param idUnidad
     * @param idGrupoActividad
     * @return List<AlumnoMateriasUnidadDTO>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar alumnos de materias", description = "Este método devuelve un objeto list con los alumnos y las materias",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/alumnomateriasunidad")
    public ResponseEntity<List<AlumnoMateriasUnidadDTO>> getMateriasUnidad(@RequestParam("idMateria") Long idMateria,
                                                                           @RequestParam("idUnidad") Long idUnidad,
                                                                           @RequestParam("idGrupoActividad") Long idGrupoActividad) {

        List<AlumnoMateriasUnidad> materiasList = servicio.getAlumnoMateriasUnidad(idMateria, idUnidad, idGrupoActividad);
        List<AlumnoMateriasUnidadDTO> materiasOut = materiasList.stream().map(x -> modelMapper.map(x, AlumnoMateriasUnidadDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(materiasOut, HttpStatus.OK);
    }

}

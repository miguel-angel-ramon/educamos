package es.jccm.edu.evaluacion.adapter.in.rest.ponderacion;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.evaluacion.adapter.in.rest.BasePath;
import es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.DocentePonderacionDto;
import es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.MateriasUnidadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.PonderacionDto;
import es.jccm.edu.evaluacion.application.domain.ponderacion.DocentePonderacion;
import es.jccm.edu.evaluacion.application.domain.ponderacion.MateriasUnidad;
import es.jccm.edu.evaluacion.application.domain.ponderacion.Ponderacion;
import es.jccm.edu.evaluacion.application.ports.in.ponderacion.IPonderacionService;
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
@RequestMapping(BasePath.EvaluacionBasePath + "/ponderacion")
@Tag(name = "Servicio Materias Escritorio", description = "Servicio para recuperar las materias y las unidades dónde se imparten")
@CrossOrigin
public class PonderacionRestController {

    @Autowired
    IPonderacionService ponderacionService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    /**
     * Conectamos con la BBDD de Delphos y sacamos las materias de un profesor.
     *
     * @param jwt  token
     * @param anno año académico
     * @return List<MateriasUnidadDTO>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar unidades de materias", description = "Este método devuelve un objeto list con las materias y sus unidades",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/materiasunidad")
    public ResponseEntity<List<MateriasUnidadDTO>> getMateriasUnidad(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("anno") Integer anno, @RequestParam("codigoCentro") Long codigoCentro) {

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        List<MateriasUnidad> materiasList = ponderacionService.getMateriasUnidad(datosUsuario.getIdEmpleadoDelphos(), anno, codigoCentro);
        List<MateriasUnidadDTO> materiasOut = materiasList.stream().map(x -> modelMapper.map(x, MateriasUnidadDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(materiasOut, HttpStatus.OK);
    }

    /**
     * Conectamos con la BBDD de Delphos y sacamos las competencias especificas de un profesor.
     *
     * @param idMateria identificador de la materia
     * @return List<CompetenciasEspecificasDto>
     */

    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Recuperar ponderacion", description = "Este método devuelve la ponderación con las competencias especificas y los criterios de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getPonderacion")

    public ResponseEntity<PonderacionDto> getPonderacion(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                         @RequestParam("idMateria") Long idMateria,
                                                         @RequestParam("idEmpleado") Long idEmpleado) {

        Ponderacion ponderacion = ponderacionService.getPonderaciones(idMateria, idEmpleado);
        PonderacionDto ponderacionOut = modelMapper.map(ponderacion, PonderacionDto.class);

        return new ResponseEntity<>(ponderacionOut, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Guardar ponderación", description = "Este método guarda la ponderación con las competencias específicas y los criterios de evaluación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @PostMapping("/savePonderacion")

    public ResponseEntity<PonderacionDto> savePonderacion(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                          @RequestParam("guardado") Integer guardado, @RequestBody PonderacionDto ponderacion) {

        try {
            ponderacionService.savePonderacion(ponderacion, guardado);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Obtiene los docentes", description = "Este método obtiene una lista de docentes para copiar su ponderación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/getDocentesPonderacion")

    public ResponseEntity<List<DocentePonderacionDto>> getDocentesPonderacion(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                                              @RequestParam("codCentro") Long codCentro, @RequestParam("idMateria") Long idMateria) {

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        try {
            List<DocentePonderacion> docentes = ponderacionService.getDocentesPonderacion(codCentro, idMateria, datosUsuario.getIdEmpleadoDelphos());
            List<DocentePonderacionDto> docentesOut = docentes.stream().map(docente -> modelMapper.map(docente, DocentePonderacionDto.class)).collect(Collectors.toList());

            return new ResponseEntity<List<DocentePonderacionDto>>( docentesOut, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<DocentePonderacionDto>>((List<DocentePonderacionDto>) null, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Comprobamos el número de valoraciones de criterios asociadas a una ponderación y si es cero, hacemos que esta sea editable.
     *
     * @param idPonderacion identificador de una ponderacion
     * @return ResponseEntity<HttpStatus>
     */
    @PreAuthorize("hasAnyRole('ICO', 'I', 'INZ', 'INC', 'P','PRO')")
    @Operation(summary = "Comprueba si la ponderación debe ser editable", description = "Este método revisa si existen valoraciónes de criterios asociados a una ponderación "
    		+ "y en caso de que no existan actualiza el campo que la hace editable",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/compruebaPonderacionEditable")
    public ResponseEntity<Boolean> getDocentesPonderacion(@RequestParam("idPonderacion") Long idPonderacion) {
        try {
            Boolean actualizado = ponderacionService.compruebaPonderacionEditable(idPonderacion);

            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

}

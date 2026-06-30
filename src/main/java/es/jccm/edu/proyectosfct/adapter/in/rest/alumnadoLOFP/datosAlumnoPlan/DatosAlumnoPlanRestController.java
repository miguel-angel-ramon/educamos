package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model.DatosAlumnoPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model.DatosSeguridadSocialAlumnoPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.datosAlumnoPlan.model.ListadoAlumnosPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.resultadosAsociadosPlan.model.ListadoResultadosAsociadosPlanDto;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ListadoResultadosAsociadosPlan;
import es.jccm.edu.proyectosfct.application.ports.in.datosalumnoplan.IDatosAlumnoPlanService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
public class DatosAlumnoPlanRestController {

    @Autowired
    private IDatosAlumnoPlanService datosAlumnoPlanService;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Obtener datos del alumno por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos del alumno obtenidos"),
            @ApiResponse(responseCode = "404", description = "No se encontraron datos para la matrícula especificada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/datosAlumnoPlan/{xMatricula}")
    public ResponseEntity<DatosAlumnoPlanDto> getDatosAlumnoPlan(@PathVariable("xMatricula") Long xMatricula) {
        Optional<DatosAlumnoPlanDto> datosAlumnoPlan = datosAlumnoPlanService.getDatosAlumnoPlanByMatricula(xMatricula);
        return datosAlumnoPlan.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Devuelve la lista de Alumnos tipo plan.
     *
     * @param idProyecto Id del proyecto
     * @return the response entity
     */
    //@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
    @Operation(summary = "Lista de alumnos plan", description = "Devuelve la lista de alumnos de un módulo tipo plan",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getListadoAlumnosPlan/{idProyecto}")
    public ResponseEntity<List<ListadoAlumnosPlanDto>> getListadoAlumnosPlan(@PathVariable("idProyecto") Long idProyecto) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);

            List<ListadoAlumnosPlanDto> modulo = datosAlumnoPlanService.getListadoAlumnosPlan(idProyecto);

            return new ResponseEntity<List<ListadoAlumnosPlanDto>>(modulo, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Crear o actualizar los datos del alumno por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos del alumno creados o actualizados exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/actualizarDatosAlumnoPlan")
    public ResponseEntity<DatosAlumnoPlanDto> actualizarDatosAlumnoPlan(
            @RequestBody DatosAlumnoPlanDto datosAlumnoPlanDto,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {


            datosAlumnoPlanService.updateDatosAlumnoPlan(datosAlumnoPlanDto);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Asignar el campo lgPrl a true en base a un listado de matrículas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos del alumno modificados correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/setPrlMasivo")
    public ResponseEntity<Long> setPrlMasivo(
            @RequestBody List<Long> matriculasAlu,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {

            datosAlumnoPlanService.setPrlMasivo(matriculasAlu);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener datos de Seguridad Social del alumno por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos de Seguridad Social obtenidos exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron datos de Seguridad Social para la matrícula especificada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/datosSeguridadSocialAlumno/{matricula}")
    public ResponseEntity<List<DatosSeguridadSocialAlumnoPlanDto>> getDatosSeguridadSocialAlumno(
            @PathVariable("matricula") Long matricula) {
        try {
            // Obtener los datos de Seguridad Social en forma de lista
            List<DatosSeguridadSocialAlumnoPlanDto> datosSS = datosAlumnoPlanService.getDatosSeguridadSocialAlumnoByMatricula(matricula);

            // Verificar si se obtuvieron resultados
            if (!datosSS.isEmpty()) {
                return new ResponseEntity<>(datosSS, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizarDatosSeguridadSocialAlumno")
    @Operation(summary = "Actualizar datos de Seguridad Social de los alumnos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos de Seguridad Social actualizados correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> actualizarDatosSeguridadSocialAlumno(
            @RequestBody List<DatosSeguridadSocialAlumnoPlanDto> datosSSAlumnoDtoList,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
            // Llamada al servicio para actualizar los datos
            datosAlumnoPlanService.updateDatosSeguridadSocialAlumno(datosSSAlumnoDtoList);

            // Respuesta exitosa
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Error de solicitud
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Error interno
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
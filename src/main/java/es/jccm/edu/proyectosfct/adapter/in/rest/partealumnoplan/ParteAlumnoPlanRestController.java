package es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan;

import es.jccm.edu.proyectosfct.adapter.in.rest.partealumnoplan.model.InfoActividadesParteDiaDto;
import es.jccm.edu.proyectosfct.application.domain.partealumnoplan.entities.projection.InfoActividadesParteDiaProjection;
import es.jccm.edu.proyectosfct.application.services.partealumnoplan.PardiaAluplanService;
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
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Parte Alumno Plan", description = "Servicio con las operaciones relacionadas con el Parte de Alumno")
public class ParteAlumnoPlanRestController {

    @Autowired
    private PardiaAluplanService pardiaAluplanService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Obtiene información de actividades relacionadas con un parte de día",
            description = "Devuelve el número de actividades, el total de horas y un indicador de si hay actividades relacionadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos obtenidos correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InfoActividadesParteDiaDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/getInfoActividadesPorConvProyYFecha/{idConvProyAlu}/{fecha}")
    public ResponseEntity<List<InfoActividadesParteDiaDto>> getInfoActividadesPorConvProyYFecha(
            @PathVariable("idConvProyAlu") Long idConvProyAlu,
            @PathVariable("fecha") String fecha) {
        try {
            // Obtener la lista de DTO desde el servicio
            List<InfoActividadesParteDiaDto> dtoList = pardiaAluplanService.getInfoActividadesPorConvProyYFecha(idConvProyAlu, fecha);

            // Devolver siempre 200, con lista vacía si no hay datos
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

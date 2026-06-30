package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.resultadosAsociadosPlan;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.resultadosAsociadosPlan.model.ListadoResultadosAsociadosPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.resultadosAsociadosPlan.model.ListadoResultadosAsociadosPlanRelacionadosDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.resultadosAsociadosPlan.model.ResultadosAsociadosPlanDTO;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ModulosCurso;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ListadoResultadosAsociadosPlan;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ListadoResultadosAsociadosPlanRelacionados;
import es.jccm.edu.proyectosfct.application.domain.resultadosAsociadosPlan.entities.ResultadosAsociadosPlan;
import es.jccm.edu.proyectosfct.application.ports.in.proyectos.IModulosCursosService;
import es.jccm.edu.proyectosfct.application.ports.in.resultadosAsociadosPlan.IResultadosAsociadosPlanService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.envers.AuditOverride;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Resultados Asociados al Plan", description = "Operaciones sobre Resultados Asociados al Plan")
public class ResultadosAsociadosPlanRestController {

    @Autowired
    private IResultadosAsociadosPlanService resultadosAsociadosPlanService;

    @Autowired
    private IModulosCursosService modulosCursosService;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    private ModelMapper modelMapper;

    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Guarda o actualiza Resultados Asociados al Plan",
            description = "Este método guarda un nuevo Resultado Asociado al Plan o lo actualiza si ya existe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, guardado o actualizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultadosAsociadosPlanDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("saveResultadosAsociadosPlan/{idModuloCurso}")
    public ResponseEntity<List<ResultadosAsociadosPlanDTO>> saveResultadosAsociadosPlan(@PathVariable("idModuloCurso") Long idModulo,
            @Parameter(description = "Lista de Resultados Asociados al Plan", required = true)
            @RequestBody final List<ResultadosAsociadosPlanDTO> resultadosAsociadosPlanDTOList,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {

            ModulosCurso modulo = modulosCursosService.getById(idModulo);
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);


            List<ResultadosAsociadosPlan> resultadosAsociadosPlanList = resultadosAsociadosPlanDTOList.stream()
                    .map(dto -> modelMapper.map(dto, ResultadosAsociadosPlan.class))
                    .peek(result -> result.setModulosCurso(modulo))
                    .map(result -> resultadosAsociadosPlanService.saveResultadosAsociadosPlan(result, datosUsuario.getXUsuarioDelphos())) // Pasa el valor adicional aquí
                    .collect(Collectors.toList());


            List<ResultadosAsociadosPlanDTO> resultadosAsociadosPlanOutList = resultadosAsociadosPlanList.stream()
                    .map(plan -> modelMapper.map(plan, ResultadosAsociadosPlanDTO.class))
                    .collect(Collectors.toList()); 

            return new ResponseEntity<>(resultadosAsociadosPlanOutList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Devuelve la lista de Resultados asociados a un modulo tipo plan.
     *
     * @param idModulo Id del modulo
     * @return the response entity
     */
    //@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
    @Operation(summary = "Lista de Resultados asociados plan", description = "Devuelve la lista de Resultados asociacdos a un modulo tipo plan",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getListadoResultadosAsociadosPlan/{idModulo}")
    public ResponseEntity<List<ListadoResultadosAsociadosPlanDto>> getListadoResultadosAsociadosPlan(@PathVariable("idModulo") Long idModulo) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);

            List<ListadoResultadosAsociadosPlan> modulo = resultadosAsociadosPlanService.getListadoResultadosAsociadosPlan(idModulo);

            List<ListadoResultadosAsociadosPlanDto> moduloDto = modulo.stream().map(x -> modelMapper.map(x, ListadoResultadosAsociadosPlanDto.class)).collect(Collectors.toList());

            return new ResponseEntity<List<ListadoResultadosAsociadosPlanDto>>(moduloDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Devuelve la lista de Resultados asociados a un módulo en relación.
     *
     * @param idModulo Id del módulo
     * @return the response entity
     */
//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
    @Operation(summary = "Lista de Resultados asociados en relación", description = "Devuelve la lista de Resultados asociados a un módulo en relación",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/getListadoResultadosAsociadosPlanRelacionados/{idModulo}/{idActividad}")
    public ResponseEntity<List<ListadoResultadosAsociadosPlanRelacionadosDto>> getListadoResultadosAsociadosPlanRelacionados(@PathVariable("idModulo") Long idModulo,
    		                                                                                                                 @PathVariable("idActividad") Long idActividad) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);

            List<ListadoResultadosAsociadosPlanRelacionados> resultadosRelacionados = resultadosAsociadosPlanService.getListadoResultadosAsociadosPlanRelacionados(idModulo,idActividad);

            List<ListadoResultadosAsociadosPlanRelacionadosDto> resultadosRelacionadosDto = resultadosRelacionados.stream()
                    .map(x -> modelMapper.map(x, ListadoResultadosAsociadosPlanRelacionadosDto.class))
                    .collect(Collectors.toList());

            return new ResponseEntity<List<ListadoResultadosAsociadosPlanRelacionadosDto>>(resultadosRelacionadosDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos.model.EsActividadRepetidaDTO;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos.model.InfoPardiaDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.resultadosAsociadosPlan.model.ListadoResultadosAsociadosPlanRelacionadosDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.actividadesModulos.model.ListadoActividadesModulosDto;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ActividadesModulos;
import es.jccm.edu.proyectosfct.application.domain.actividadesModulos.entities.ListadoActividadesModulos;
import es.jccm.edu.proyectosfct.application.ports.in.actividadesmodulos.IActividadesModulosService;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.InfrastructureProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Actividades Módulos Plan", description = "Operaciones sobre Actividades de Módulos de Plan")
public class ActividadesModulosPlanRestController {

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    private IActividadesModulosService actividadesModulosService;


    @Autowired
    private ModelMapper modelMapper;

    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Crea una nueva Actividad en el Módulo",
            description = "Este método permite crear una nueva actividad en el módulo especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, actividad creada correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActividadesModulos.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
@PostMapping("procesarActividad/{idActividad}/{idModuloCurso}/{nombreActividad}/{txAbrev}/{nuOrden}")
    public ResponseEntity<ActividadesModulos> procesarActividad(
            @PathVariable("idActividad") Long idActividad,
            @PathVariable("idModuloCurso") Long idModulo,
            @PathVariable("nombreActividad") String nombreActividad,
            @PathVariable("txAbrev") String txAbrev,
            @PathVariable("nuOrden") Integer nuOrden,
            @Parameter(description = "Lista de Resultados de Aprendizaje Asociados", required = true)
            @RequestBody final List<ListadoResultadosAsociadosPlanRelacionadosDto> resultadosAsociadosDtoList,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

            ActividadesModulos actividadProcesada;
            if (idActividad == -1) {
                actividadProcesada = actividadesModulosService.crearActividad(idModulo, nombreActividad, datosUsuario.getXUsuarioDelphos(), resultadosAsociadosDtoList,txAbrev,nuOrden);
            } else {
                actividadProcesada = actividadesModulosService.actualizarActividad(idActividad, nombreActividad, datosUsuario.getXUsuarioDelphos(), resultadosAsociadosDtoList,txAbrev,nuOrden);
            }

            return new ResponseEntity<>(actividadProcesada, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Borrar una Actividad en el Módulo",
            description = "Este método permite borrar una actividad en el módulo especificado junto con sus resultados de aprendizaje asociados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, actividad borrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("borrarActividad/{idActividad}")
    public ResponseEntity<Void> borrarActividad(@PathVariable("idActividad") Long idActividad) {
        try {
            boolean deleted = actividadesModulosService.borrarActividad(idActividad);

            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Obtener nombre de la Actividad",
            description = "Este método devuelve el nombre de la actividad dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, nombre de la actividad obtenido correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("obtenerNombreActividad/{idActividad}")
    public ResponseEntity<ListadoActividadesModulosDto> obtenerNombreActividad(@PathVariable("idActividad") Long idActividad) {
        try {
            Optional<ActividadesModulos> actividad = actividadesModulosService.buscarPorId(idActividad);

            if (actividad.isPresent()) {
                ListadoActividadesModulosDto listadoActividadesModulosDto = new ListadoActividadesModulosDto();
                listadoActividadesModulosDto.setNombre(actividad.get().getTxNombre());
                listadoActividadesModulosDto.setTxAbrev(actividad.get().getTxAbrev());
                listadoActividadesModulosDto.setNuOrden(actividad.get().getNuOrden());
                return new ResponseEntity<ListadoActividadesModulosDto>(listadoActividadesModulosDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Obtener listado de actividades",
            description = "Este método permite obtener una lista de actividades en base al módulo especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, listado devuelvo correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActividadesModulos.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @GetMapping("getListadoActividadesPlan/{idModuloCurso}")
    public ResponseEntity<List<ListadoActividadesModulosDto>> getListadoActividadesPlan(@PathVariable("idModuloCurso") Long idModulo) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);

            List<ListadoActividadesModulos> actividades = actividadesModulosService.getListadoActividadesPlan(idModulo);

            List<ListadoActividadesModulosDto> actividadesDto = actividades.stream().map(x -> modelMapper.map(x, ListadoActividadesModulosDto.class)).collect(Collectors.toList());

            return new ResponseEntity<List<ListadoActividadesModulosDto>>(actividadesDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Verifica si un nombre de actividad está repetido en un módulo",
            description = "Este endpoint verifica si ya existe una actividad con el mismo nombre en el módulo especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, el nombre es único o está repetido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/esActividadRepetida/{idModuloCurso}/{nombreActividad}/{idActividad}/{txAbrev}/{nuOrden}")
    public ResponseEntity<EsActividadRepetidaDTO> verificarNombreActividad(
            @PathVariable("idModuloCurso") Long idModuloCurso,
            @PathVariable("nombreActividad") String nombreActividad,
            @PathVariable("idActividad") Long idActividad,
            @PathVariable("txAbrev") String txAbrev,
            @PathVariable("nuOrden") Integer nuOrden)
    {


        try {
            EsActividadRepetidaDTO esActividadRepetidaDTO = actividadesModulosService.contarActividadesRepetidas(idModuloCurso,nombreActividad,idActividad,txAbrev,nuOrden);

            return new ResponseEntity<EsActividadRepetidaDTO>(esActividadRepetidaDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Devuelve el número de actividad siguiente",
            description = "Este endpoint devuelve el número de la actividad siguiente de un Módulo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, devuelve el número siguiente de actividad",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/getOrdenActividad/{idModuloCurso}")
    public ResponseEntity<Integer> ObtenerNumeroOrden (
            @PathVariable("idModuloCurso") Long idModuloCurso) {

        try {
            Integer numeroActividad = actividadesModulosService.getOrdenActividad(idModuloCurso);

            return new ResponseEntity<>(numeroActividad,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Obtener listado de actividades",
            description = "Este método permite obtener una lista de actividades en base al convproy aly y un día especificado")
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "OK, listado devuelvo correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActividadesModulos.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @GetMapping("getListadoActividadesPlanByConvProyAlu/{idConvProyAlu}/{fechaDia}")
    public ResponseEntity<List<ListadoActividadesModulosDto>> getListadoActividadesPlanByConvProyAlu(@PathVariable("idConvProyAlu") Long idConvProyAlu,
                                                                                                     @PathVariable("fechaDia") String fechaDia) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);

            List<ListadoActividadesModulos> actividades = actividadesModulosService.getListadoActividadesPlanByConvProyAlu(idConvProyAlu, fechaDia);

            List<ListadoActividadesModulosDto> actividadesDto = actividades.stream().map(x -> modelMapper.map(x, ListadoActividadesModulosDto.class)).collect(Collectors.toList());

            return new ResponseEntity<List<ListadoActividadesModulosDto>>(actividadesDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Guardar actividades para seguimiento plan",
            description = "Este método permite guardar actividades para el seguimiento de los planes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, actividad guardada correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActividadesModulos.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("postGuardarActividadSeguimientoPlan/{idConvProyAlu}/{fechaAct}/{fechaInicio}/{observaciones}/{horas}")
    public ResponseEntity<Object> postGuardarActividadSeguimientoPlan(
            @PathVariable("idConvProyAlu") Long idConvProyAlu,
            @PathVariable("fechaAct") String fechaAct,
            @PathVariable("fechaInicio") String fechaInicio,
            @PathVariable("observaciones") String observaciones,
            @PathVariable("horas") Integer horas,
            @Parameter(description = "Lista de Actividades", required = true)
            @RequestBody final List<Long> actividades,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

            actividadesModulosService.guardarActividadSeguimientoPlan(idConvProyAlu, fechaAct, fechaInicio, observaciones, horas, actividades);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Borrar datos del parte diario de un seguimiento")
    @PostMapping("postBorrarActividadSeguimientoPlan/{idConvProyAlu}/{fechaAct}/{fechaInicio}")
    public ResponseEntity<Object> postBorrarActividadSeguimientoPlan(
            @PathVariable("idConvProyAlu") Long idConvProyAlu,
            @PathVariable("fechaAct") String fechaAct,
            @PathVariable("fechaInicio") String fechaInicio,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

            actividadesModulosService.borrarActividadSeguimientoPlan(idConvProyAlu, fechaAct, fechaInicio);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PreAuthorize("hasAnyRole('P','PRO','ALU','FCT','CFT')")
    @Operation(summary = "Obtener listado de actividades",
            description = "Este método permite obtener una lista de actividades en base al convproy aly y un día especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, listado devuelvo correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActividadesModulos.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
    @GetMapping("getHorasAndObservacionesPardia/{idConvProyAlu}/{fechaDia}")
    public ResponseEntity<InfoPardiaDto> getHorasAndObservacionesPardia(@PathVariable("idConvProyAlu") Long idConvProyAlu,
                                                                        @PathVariable("fechaDia") String fechaDia) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);

            InfoPardiaDto parte = actividadesModulosService.getHorasAndObservacionesPardia(idConvProyAlu, fechaDia);
            
            return new ResponseEntity<InfoPardiaDto>(parte, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}

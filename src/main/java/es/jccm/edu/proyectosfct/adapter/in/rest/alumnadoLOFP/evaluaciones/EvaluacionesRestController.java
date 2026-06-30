package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.evaluaciones.model.*;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities.ModuloEvaluacion;
import es.jccm.edu.proyectosfct.application.ports.in.evaluaciones.IEvaluacionesService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Evaluaciones", description = "Servicio para la gestión de evaluaciones")
public class EvaluacionesRestController {

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    private IEvaluacionesService evaluacionesService;

    @Autowired
    private ModelMapper modelMapper;


    private static final String NO_CACHE = "no-cache";
    private static final String APPLICATION = "application/octet-stream";
    private static final String CONTENT = "Content-Disposition";
    private static final String ATTACHMENT = "attachment; filename=";
    private static final String PRAGMA = "Pragma";
    private static final String CACHE = "Cache-Control";

    @Operation(summary = "Listado de alumnos evaluados LOFP",
            description = "Este método devuelve un listado de alumnos evaluados en LOFP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, listado obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @GetMapping("/getListadoAlumnosEvaluacionLOFP/{idTutorfctdual}/{idCentro}/{cAnno}")
    public ResponseEntity<List<AlumnoEvaluacionDto>> getListadoAlumnosEvaluacionLOFP(
            @PathVariable("idTutorfctdual") Long idTutorfctdual,
            @PathVariable("idCentro") Long idCentro,
            @PathVariable("cAnno") Integer cAnno) {

        List<AlumnoEvaluacionDto> listadoAlumnosEvaluacion = evaluacionesService.getListadoAlumnosEvaluacionLOFP(
                idTutorfctdual, idCentro, cAnno);

        return new ResponseEntity<>(listadoAlumnosEvaluacion, HttpStatus.OK);
    }

    @Operation(summary = "Listado de módulos",
            description = "Este método devuelve un listado de módulos con sus respectivos RA e información sobre la evaluación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, listado obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @GetMapping("/getListadoModulosEvaluacionLOFP/{xMatricula}/{xEmpresa}")
    public ResponseEntity<List<ModuloEvaluacionDto>> getListadoModulosEvaluacionLOFP(
            @PathVariable("xMatricula") Long xMatricula,
            @PathVariable("xEmpresa") Long xEmpresa){

        List<ModuloEvaluacion> listadoModulosEvaluacion = evaluacionesService.getListadoModulosEvaluacionLOFP(
                xMatricula, xEmpresa);


        List<ModuloEvaluacionDto> listadoModulosEvaluacionDto = listadoModulosEvaluacion.stream()
                .map(x -> modelMapper.map(x, ModuloEvaluacionDto.class)).collect(Collectors.toList());

        // filtramos duplicados anteriores a las restriciones, eliminamos por idModulo distinto pero mismo código de módulo dejando solo uno
        List<ModuloEvaluacionDto> listadoFiltrado = listadoModulosEvaluacionDto.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                ModuloEvaluacionDto::getModuloNombre, // clave, código del módulo
                                dto -> dto,
                                (dto1, dto2) -> dto1
                        ),
                        map -> new ArrayList<>(map.values())
                ));

        return new ResponseEntity<>(listadoFiltrado, HttpStatus.OK);
    }

    @Operation(summary = "Listado de cabeceras de evaluación para una matrícula",
            description = "Devuelve un listado de cabeceras de evaluación para cada empresa vinculada a la matrícula")
    @GetMapping("/getListadoCabecerasEvaluacion/{xMatricula}")
    public ResponseEntity<List<DatosGeneralesCabeceraEvaluacionDto>> getListadoCabecerasEvaluacion(
            @PathVariable("xMatricula") Long xMatricula) {

        List<DatosGeneralesCabeceraEvaluacionDto> listadoCabeceras = evaluacionesService.getListadoCabecerasEvaluacion(xMatricula);

        return new ResponseEntity<>(listadoCabeceras, HttpStatus.OK);
    }

    @Operation(summary = "Guardar evaluación para resultados de aprendizaje",
            description = "Este método permite guardar la evaluación de los resultados de aprendizaje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, evaluación guardada correctamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "No autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/postGuardarEvaluacionLOFP/{idEvaluacion}/{xEmpresa}/{idResultado}/{idCalificacion}/{idModuloMatricula}/{xMatricula}")
    public ResponseEntity<ElementoSelectDto> postGuardarEvaluacionLOFP(
            @PathVariable("idEvaluacion") Long idEvaluacion,
            @PathVariable("xEmpresa") Long xEmpresa,
            @PathVariable("idResultado") Long idResultado,
            @PathVariable("idCalificacion") Long idCalificacion,
            @PathVariable("idModuloMatricula") Long idModuloMatricula,
            @PathVariable("xMatricula") Long xMatricula,
            @RequestParam(value = "motivacion", required = false, defaultValue = "") String motivacion) { 

        Long idEvaluacionRes = evaluacionesService.guardarEvaluacionLOFP(idEvaluacion, xEmpresa, idResultado, idCalificacion, idModuloMatricula, motivacion);

        Integer estadoAnexo = evaluacionesService.actualizarEstadoAnexo(xMatricula,xEmpresa,0);

        String estadoAnexoString = String.valueOf(estadoAnexo);

        ElementoSelectDto dto = new ElementoSelectDto();
        dto.setId(idEvaluacionRes);
        dto.setDescripcion(estadoAnexoString);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @Operation(summary = "Listado de calificaciones",
            description = "Este método devuelve el listado de calificaciones disponibles para la evaluación de RA en empresas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, listado obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })

    @GetMapping("/getListadoCalificacionesEvaluacionLOFP")
    public ResponseEntity<List<CalificacionEvaluacionDto>> getListadoCalificacionesEvaluacionLOFP(){

        List<CalificacionEvaluacionDto> listadoCalificacionesEvaluacionDto = evaluacionesService.getListadoCalificacionesEvaluacionLOFP();

        return new ResponseEntity<>(listadoCalificacionesEvaluacionDto, HttpStatus.OK);
    }

    @Operation(summary = "Procesar anexo firmado en Rodal",
            description = "Este método permite procesar un único anexo firmado en Rodal para una evaluación en LOFP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, anexo procesado correctamente"),
            @ApiResponse(responseCode = "401", description = "No está autorizado para realizar esta operación"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/procesarEvalProyAnexo/{xMatricula}/{xEmpresa}/{cAnno}/{xCentro}")
    public ResponseEntity<EvalProyAnexoDto> procesarEvalProyAnexo(
            @Parameter(description = "Archivo PDF del anexo firmado", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Identificador de la matrícula", required = true)
            @PathVariable("xMatricula") Long xMatricula,
            @Parameter(description = "Identificador de la empresa", required = true)
            @PathVariable("xEmpresa") Long xEmpresa,
            @Parameter(description = "Año académico", required = true)
            @PathVariable("cAnno") Integer cAnno,
            @Parameter(description = "Identificador del centro", required = true)
            @PathVariable("xCentro") Long xCentro,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

            EvalProyAnexoDto anexoDto = new EvalProyAnexoDto();
            anexoDto.setXMatricula(xMatricula);
            anexoDto.setXEmpresa(xEmpresa);

            EvalProyAnexoDto resultado = evaluacionesService.procesarEvalProyAnexo(anexoDto, file, cAnno, xCentro, datosUsuario);

            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.ok(new EvalProyAnexoDto());
        }
    }


    @Operation(summary = "Actualizar estado de evaluación firmada en Rodal",
            description = "Este endpoint permite actualizar el flag LG_ACTUALIZADO a 0 o 1 según la matrícula y la empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, actualización realizada correctamente."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor (valor inválido o error inesperado).")
    })
    @PostMapping("/actualizarEstadoAnexo/{xMatricula}/{xEmpresa}/{lgActualizado}")
    public ResponseEntity<Integer> actualizarEstadoAnexo(
            @Parameter(description = "Identificador de la matrícula", required = true) @PathVariable("xMatricula") Long xMatricula,
            @Parameter(description = "Identificador de la empresa", required = true) @PathVariable("xEmpresa") Long xEmpresa,
            @Parameter(description = "Nuevo estado del flag (0 o 1)", required = true) @PathVariable("lgActualizado") Integer lgActualizado) {
        try {
            if (lgActualizado != 0 && lgActualizado != 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(-1);
            }

            Integer estadoActualizado = evaluacionesService.actualizarEstadoAnexo(xMatricula, xEmpresa, lgActualizado);

            return ResponseEntity.ok(estadoActualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1);
        }
    }


    @Operation(summary = "Obtener anexo de evaluación por matrícula y empresa",
            description = "Este endpoint devuelve el anexo de evaluación firmado en Rodal asociado a una matrícula y empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anexo encontrado o DTO vacío si no existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/obtenerAnexoEvaluacion/{xMatricula}/{xEmpresa}")
    public ResponseEntity<EvalProyAnexoDto> obtenerAnexoEvaluacion(
            @Parameter(description = "Identificador de la matrícula", required = true) @PathVariable("xMatricula") Long xMatricula,
            @Parameter(description = "Identificador de la empresa", required = true) @PathVariable("xEmpresa") Long xEmpresa) {

        EvalProyAnexoDto anexoDto = evaluacionesService.getEvalProyAnexoByMatriculaAndEmpresa(xMatricula, xEmpresa);

        // Si no se encuentra el anexo, devolver un DTO vacío con id = -1
        if (anexoDto == null) {
            anexoDto = new EvalProyAnexoDto();
            anexoDto.setId(-1L);
        }

        return ResponseEntity.ok(anexoDto);
    }

    @GetMapping("/downloadAnexoIXEval/{xEmpresa}/{idMatricula}")
    public void downloadAnexoIXEval(HttpServletResponse response,
                                    @PathVariable("xEmpresa") Long xEmpresa,
                                    @PathVariable("idMatricula") Long idMatricula) throws JRException, IOException {
        response.setContentType(APPLICATION);
        response.setHeader(CONTENT, ATTACHMENT + "AnexoIX_Evaluacion."+ "pdf");
        response.setHeader(PRAGMA, NO_CACHE);
        response.setHeader(CACHE, NO_CACHE);

        byte [] documento = evaluacionesService.downloadAnexoIXEval(xEmpresa,idMatricula);

        InputStream is = new ByteArrayInputStream(documento);
        FileCopyUtils.copy(is, response.getOutputStream());
        response.flushBuffer();

    }

    @Operation(summary = "Borrar anexo de evaluación",
            description = "Este endpoint borra el anexo de evaluación tanto en la base de datos como en Rodal.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/borrarEvalProyAnexo/{idEvaFirRodal}")
    public ResponseEntity<Boolean> borrarEvalProyAnexo(
            @Parameter(description = "Identificador del documento en Rodal", required = true)
            @PathVariable("idEvaFirRodal") String idEvaFirRodal) {

        Boolean resultado = evaluacionesService.borrarDocumento(idEvaFirRodal);
        return ResponseEntity.ok(resultado);
    }
    
    @Operation(summary = "Actualizar horas",
            description = "Este endpoint actualiza los datos de horas para la empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/updateHoras/{xEmpresa}/{idMatricula}/{horas}")
    public ResponseEntity<Integer> updateHoras(@PathVariable("xEmpresa") Long xEmpresa,
    									       @PathVariable("idMatricula") Long idMatricula,
    									       @PathVariable("horas") Integer horas) {

    	try {
   
    		Integer estadoActualizado = evaluacionesService.updateHoras(xEmpresa, idMatricula,horas);

            return ResponseEntity.ok(estadoActualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1);
        }
    }

}

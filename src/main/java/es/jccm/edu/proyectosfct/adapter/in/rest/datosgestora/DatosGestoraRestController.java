package es.jccm.edu.proyectosfct.adapter.in.rest.datosgestora;

import es.jccm.edu.proyectosfct.adapter.in.rest.datosgestora.model.ListadoDatosGestoraDto;
import es.jccm.edu.proyectosfct.application.domain.datosgestora.entities.DatosGestora;
import es.jccm.edu.proyectosfct.application.domain.datosgestora.projection.ListadoDatosGestoraProjection;
import es.jccm.edu.proyectosfct.application.ports.in.datosgestora.IDatosGestoraService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Programas FCT", description = "Servicio para obtener los datos de la Seguridad Social de los alumnos agrupado por centro")
public class DatosGestoraRestController {

    @Autowired
    private IDatosGestoraService datosGestoraService;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    private ModelMapper modelMapper;

    private static final String NO_CACHE = "no-cache";

    /**
     * Obtener Datos de la Seguridad Social.
     *
     * @return the response entity
     */
    //@PreAuthorize("hasAnyRole('P','PRO','C','ALU','FCT','CFT')")
    @Operation(summary = "Envio de altas seguridad social empresa", description = "Este metodo envia todo el alumnado recibido a la empresa tramitadora de las altas", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/saveDatosSegSocial")
    public ResponseEntity<Integer> saveDatosSegSocial(@RequestParam("idCentro") Long idCentro,
                                                                    @RequestParam("anno") Integer anno,
                                                                    @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

            List<DatosGestora> listadoDatosGestora = datosGestoraService.saveDatosSegSocial(idCentro, anno, datosUsuario.getXUsuarioDelphos());            

            return new ResponseEntity<>(listadoDatosGestora.size(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    /**
     * Lista datos.
     *
     * @return the response entity
     * @throws ParseException
     */
    //@PreAuthorize("hasAnyRole('P','PRO','C','ALU','FCT','CFT')")
    @Operation(summary = "Listado Datos Gestora", description = "Este metodo devuelve una listado con los datos proporcionados por la gestora de la Seguridad Social de los alumnos de un centro en concreto",
            responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getListadoDatosGestora/{idTutorfctdual}/{idCentro}/{cAnno}/{idOfertamatrig}/{idUnidad}/{idPerfil}/{idCentroCombo}/{idProvincia}/{cEstado}/{idTipo}")
    public ResponseEntity<List<ListadoDatosGestoraDto>> getListadoDatosGestora(@PathVariable("idTutorfctdual") Long idTutorfctdual,
                                                                                     @PathVariable("idCentro") Long idCentro,
                                                                                     @PathVariable("cAnno") Integer cAnno,
                                                                                     @PathVariable("idOfertamatrig") Long idOfertamatrig,
                                                                                     @PathVariable("idUnidad") Long idUnidad,
                                                                                     @PathVariable("idPerfil") Long idPerfil,
                                                                                     @PathVariable("idCentroCombo") Long idCentroCombo,
                                                                                     @PathVariable("idProvincia") Long idProvincia,
                                                                                     @PathVariable("cEstado") String cEstado,
                                                                                     @PathVariable("idTipo") Long idTipo,
                                                                                     @RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException{

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        List<ListadoDatosGestoraProjection> listadoDatosGestoraProjection =  datosGestoraService.getListadoDatosGestora(idTutorfctdual,
                                                                                                                        idCentro,
                                                                                                                        cAnno,
                                                                                                                        idOfertamatrig,
                                                                                                                        idUnidad,
                                                                                                                        idPerfil,
                                                                                                                        idCentroCombo,
                                                                                                                        idProvincia,
                                                                                                                        datosUsuario.getXUsuarioDelphos(),
                                                                                                                        cEstado,
                                                                                                                        idTipo);

        List<ListadoDatosGestoraDto> listadoDatosGestoraDto = listadoDatosGestoraProjection.stream().map(x -> modelMapper.map(x, ListadoDatosGestoraDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(listadoDatosGestoraDto, HttpStatus.OK);
    }
    
    /**
     * Obtener Datos de la Seguridad Social.
     *
     * @return the response entity
     */
    //@PreAuthorize("hasAnyRole('P','PRO','C','ALU','FCT','CFT')")
    @Operation(summary = "Envio de altas seguridad social empresa", description = "Este metodo envia todo el alumnado recibido a la empresa tramitadora de las altas", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/saveDatosSegSocialBack")
    public ResponseEntity<Integer> saveDatosSegSocialBack(@RequestParam("anno") Integer anno,
                                                          @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

            Integer result = datosGestoraService.saveDatosSegSocialBack( anno, datosUsuario.getXUsuarioDelphos());            

            return new ResponseEntity<Integer>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Obtener PDF de la Seguridad Social.
     *
     * @return the response entity
     */
    //@PreAuthorize("hasAnyRole('P','PRO','C','ALU','FCT','CFT')")
    @Operation(summary = "Obtencion de archivo PDF", description = "Este metodo recupera el archivo PDF asociado a un alumno según su estado", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/getDatosGestoraPDF/{idGestora}/{estado}/{tipoDocumento}")
    public void getDatosGestoraPDF(HttpServletResponse response,
                                   @PathVariable("idGestora") Integer idGestora,
                                   @PathVariable("estado") String estado,
                                   @PathVariable("tipoDocumento") String tipoDocumento,
                                   @RequestHeader(Constants.AUTHORIZATION) String jwt) throws IOException {

            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
            byte[] documento = datosGestoraService.getDatosGestoraById(idGestora, estado, tipoDocumento, datosUsuario.getXUsuarioDelphos());

            // Asegúrate de que el contenido es un PDF válido
            if (documento == null || documento.length == 0) {
                response.sendError(HttpStatus.NO_CONTENT.value(), "No content");
                return;
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=" + "Datos gestora" + "." + "pdf");
            response.setHeader("Pragma", NO_CACHE);
            response.setHeader("Cache-Control", NO_CACHE);
            response.setStatus(200);

            // Enviar el contenido del PDF al output stream de la respuesta
            try (InputStream is = new ByteArrayInputStream(documento)) {
                FileCopyUtils.copy(is, response.getOutputStream());
            }
            response.flushBuffer();   

    }

}

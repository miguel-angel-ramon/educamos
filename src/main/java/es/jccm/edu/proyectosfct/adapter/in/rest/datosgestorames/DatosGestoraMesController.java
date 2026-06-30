package es.jccm.edu.proyectosfct.adapter.in.rest.datosgestorames;

import es.jccm.edu.proyectosfct.adapter.in.rest.datosgestorames.model.ListadoDatosGestoraMesDto;
import es.jccm.edu.proyectosfct.application.domain.datosgestorames.entities.DatosGestoraMes;
import es.jccm.edu.proyectosfct.application.domain.datosgestorames.projection.ListadoDatosGestoraMesProjection;
import es.jccm.edu.proyectosfct.application.ports.in.datosgestorames.IDatosGestoraMesService;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Programas FCT", description = "Servicio para obtener los datos de la Seguridad Social de los alumnos agrupado por mes")
public class DatosGestoraMesController {

    @Autowired
    private IDatosGestoraMesService datosGestoraMesService;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;

    @Autowired
    private ModelMapper modelMapper;

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
    @GetMapping("/getListadoDatosGestoraMes/{idTutorfctdual}/{idCentro}/{cAnno}/{idOfertamatrig}/{idUnidad}/{idPerfil}/{idCentroCombo}/{idProvincia}/{cEstado}/{mes}")
    public ResponseEntity<List<ListadoDatosGestoraMesDto>> getListadoDatosGestoraMes(@PathVariable("idTutorfctdual") Long idTutorfctdual,
                                                                                  @PathVariable("idCentro") Long idCentro,
                                                                                  @PathVariable("cAnno") Integer cAnno,
                                                                                  @PathVariable("idOfertamatrig") Long idOfertamatrig,
                                                                                  @PathVariable("idUnidad") Long idUnidad,
                                                                                  @PathVariable("idPerfil") Long idPerfil,
                                                                                  @PathVariable("idCentroCombo") Long idCentroCombo,
                                                                                  @PathVariable("idProvincia") Long idProvincia,
                                                                                  @PathVariable("cEstado") String cEstado,
                                                                                  @PathVariable("mes") Integer mes,
                                                                                  @RequestHeader(Constants.AUTHORIZATION) String jwt) throws ParseException{

        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        List<ListadoDatosGestoraMesProjection> listadoDatosGestoraMesProjection =  datosGestoraMesService.getListadoDatosGestoraMes(idTutorfctdual,
                idCentro,
                cAnno,
                idOfertamatrig,
                idUnidad,
                idPerfil,
                idCentroCombo,
                idProvincia,
                datosUsuario.getXUsuarioDelphos(),
                cEstado,
                mes);

        List<ListadoDatosGestoraMesDto> listadoDatosGestoraMesDto = listadoDatosGestoraMesProjection.stream().map(x -> modelMapper.map(x, ListadoDatosGestoraMesDto.class)).collect(Collectors.toList());

        return new ResponseEntity<>(listadoDatosGestoraMesDto, HttpStatus.OK);
    }


    //@PreAuthorize("hasAnyRole('P','PRO','C','ALU','FCT','CFT')")
    @Operation(summary = "Envio de altas seguridad social empresa", description = "Este metodo envia todo el alumnado recibido a la empresa tramitadora de las altas", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PostMapping("/saveDatosMesSegSocial")
    public ResponseEntity<Integer> saveDatosMesSegSocial(@RequestParam("idCentro") Long idCentro,
                                                         @RequestParam("anno") Integer anno,
                                                         @RequestParam("nuMes") Integer nuMes,
                                                         @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        try {
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

            //int annoProcesado = (nuMes >= 9) ? anno : anno + 1;

            List<DatosGestoraMes> listadoDatosGestora = datosGestoraMesService.saveDatosMesSegSocial(idCentro, anno, nuMes, datosUsuario.getXUsuarioDelphos());

            return new ResponseEntity<>(listadoDatosGestora.size(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

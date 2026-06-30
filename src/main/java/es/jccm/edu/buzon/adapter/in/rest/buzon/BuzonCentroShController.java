package es.jccm.edu.buzon.adapter.in.rest.buzon;

import java.util.List;
import java.util.stream.Collectors;

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

import es.jccm.edu.buzon.adapter.in.rest.buzon.model.UnidadBuzonCentroDTO;
import es.jccm.edu.buzon.application.domain.buzonCentro.UnidadBuzonCentro;
import es.jccm.edu.buzon.application.ports.in.buzonCentro.IBuzonCentroShService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/buzon")
@Tag(name = "Servicio buzon", description = "")
//@CrossOrigin
public class BuzonCentroShController {
	
	@Autowired
    IBuzonCentroShService buzonCentroService;
	
	@Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	/**
     * Devuelve la lista de las unidades correspondientes a un curso
     *
     * @param anno
     * @return the response entity
     */
    @PreAuthorize("hasAnyRole('P','PRO')")
    @Operation(summary = "Devuelve un listado de unidades del curso", description = "Devuelve un listado de unidades del curso", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "404", description = "No encontrado")})
    @GetMapping("/unidadesBuzonCentro")
    public ResponseEntity<List<UnidadBuzonCentroDTO>> getUnidadesBuzonCentroCompClave(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                                                    @RequestParam("fechaTomaPosesion") String fechaTomaPosesion,
                                                                                    @RequestParam("idCentro") Long idCentro,
                                                                                    @RequestParam("anno") Long anno,
                                                                                    @RequestParam("direccion") Boolean direccion) {
        DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

        List<UnidadBuzonCentro> unidadesBuzonCentro = buzonCentroService.getUnidadesCentroCompClave(datosUsuario.getIdEmpleadoDelphos(), fechaTomaPosesion, idCentro, anno, direccion);
        List<UnidadBuzonCentroDTO> unidadesBuzonCentroOut = unidadesBuzonCentro.stream().map(unidad -> modelMapper.map(unidad, UnidadBuzonCentroDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(unidadesBuzonCentroOut, HttpStatus.OK);
    }

}

package es.jccm.edu.comunicaciones.adapter.in.rest.avisos;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.comunicaciones.adapter.in.rest.avisos.model.NotificacionDto;
import es.jccm.edu.comunicaciones.application.domain.avisos.Notificacion;
import es.jccm.edu.comunicaciones.application.ports.in.avisos.INotificacionesService;
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
@RequestMapping("${spring.data.rest.base-path:/api}" + "/comunicaciones")
@Tag(name = "Servicio Avisos Escritorio", description = "Servicio para recuperar el módulo de avisos del escritorio")
//@CrossOrigin
public class NotificacionesRestController {
	
	@Autowired
	private INotificacionesService notificacionesService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * Conecta con el proyecto de comunica y trae todos los avisos recibidos de un usuario.
	 *
	 * @param String idUsuario
	 * @return List<AvisoDto>
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Recuperar avisos recibidos de comunica", description = "Este metodo devuelve un objeto List con todos los avisos recibidos por el usuario introducido", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/notificaciones")
    public ResponseEntity<Page<NotificacionDto>> getNotificaciones(@RequestHeader(Constants.AUTHORIZATION) String jwt, @RequestParam("codCentro") Long codCentro, 
    		@RequestParam("anno") Long Anno, @RequestParam("tipo") String tipo, @RequestParam("text") String text, @RequestParam(value = "perfil", required = false) String perfil,
    		@RequestParam("page") int page, @RequestParam("size") int size) {
    	
    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
    	
    	if(StringUtils.isEmpty(perfil)) {
			perfil = "-1";
		}
    	
    	Page<Notificacion> notPage= notificacionesService.getNotificaciones(datosUsuario.getXUsuarioComunica(), codCentro, datosUsuario.getIdEmpleadoDelphos(), Anno, tipo, text, perfil, page, size);
		
		List<NotificacionDto> notifOut = notPage.stream().map(aviso -> modelMapper.map(aviso, NotificacionDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<>(new PageImpl<>(notifOut, notPage.getPageable(), notPage.getTotalElements()), HttpStatus.OK);
    }
	
	
	/**
	 * Count no firmados
	 *
	 * @param String idUsuario
	 * @return List<AvisoDto>
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
    @Operation(summary = "Recuperar count no firmados", description = "Recuperar count no firmados", 
    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/noFirmadosCount")
    public ResponseEntity<Long> getNotificaciones(@RequestHeader(Constants.AUTHORIZATION) String jwt, 
    		@RequestParam("anno") Long Anno) {
    	
    	DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long count = notificacionesService.getNoFirmadosCount(datosUsuario.getXUsuarioComunica(), datosUsuario.getIdEmpleadoDelphos(), Anno);
		
		return new ResponseEntity<>(count, HttpStatus.OK);



	}
}
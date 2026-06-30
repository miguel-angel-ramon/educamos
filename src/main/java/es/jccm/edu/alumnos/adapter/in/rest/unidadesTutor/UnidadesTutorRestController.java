package es.jccm.edu.alumnos.adapter.in.rest.unidadesTutor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.alumnos.adapter.in.rest.unidadesTutor.model.UnidadesTutorDto;
import es.jccm.edu.alumnos.application.domain.unidadesTutor.UnidadesTutor;
import es.jccm.edu.alumnos.application.ports.in.unidadesTutor.IUnidadesTutorService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/alumnos")
@SecurityRequirement(name = "apieducamosclm")
@Tag(name = "Servicio unidades tutor", description = "Servicio para recuperar los datos de tutores de una unidad y tutores sustitutos")
//@CrossOrigin
public class UnidadesTutorRestController {

	@Autowired
	private IUnidadesTutorService unidadesTutorService;
	
	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;
	
	@Autowired
	private ModelMapper modelMapper;

	
	/**
	 * Devuelve un listado de unidades por tutor
	 *
	 * @param anno
	 * @return the response entity
	 */
	@Operation(summary = "Devuelve un listado de unidades de un tutor, o tutor sustituto", description = "Devuelve un listado de unidades a partir de un empleado", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getUnidadesTutor")
	public ResponseEntity<List<UnidadesTutorDto>> getUnidadesTutor(@RequestHeader(Constants.AUTHORIZATION) String jwt, 
			@RequestParam("anno") Long anno, @RequestParam("docentes") List<Long> docentes) {
		
		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);
		
		Long xEmpleado = datosUsuario.getIdEmpleadoDelphos() != null? datosUsuario.getIdEmpleadoDelphos() : datosUsuario.getIdEmpleadoComunica();
		
		docentes.add(xEmpleado);

		List<UnidadesTutor> uniTutor = unidadesTutorService.getTutoresByUnidades(docentes, anno);

		List<UnidadesTutorDto> uniTutorOut = uniTutor.stream()
				.map(x -> modelMapper.map(x, UnidadesTutorDto.class)).collect(Collectors.toList());

		return new ResponseEntity<>(uniTutorOut, HttpStatus.OK);

	}

}

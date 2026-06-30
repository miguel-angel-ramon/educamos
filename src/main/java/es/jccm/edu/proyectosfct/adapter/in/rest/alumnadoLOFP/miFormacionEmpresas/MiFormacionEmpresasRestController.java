package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas;


import java.util.List;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model.DatosFormacionDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.miFormacionEmpresas.model.DatosFormacionPlanDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.programas.model.ElementoSelectDto;
import es.jccm.edu.proyectosfct.application.ports.in.usuarios.IParamsFCTService;
import es.jccm.edu.shared.application.domain.datosUsuarioJwt.DatosUsuarioJwt;
import es.jccm.edu.shared.application.ports.in.datosUsuarioJwt.IDatosUsuarioJwtService;
import es.jccm.edu.shared.configuration.common.Constants;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import es.jccm.edu.proyectosfct.application.ports.in.miformacionempresas.IMiFormacionEmpresasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Mi formación empresas", description = "Servicio con las operaciones sobre formación de empresas del alumnado")
public class MiFormacionEmpresasRestController {

	@Autowired
	IMiFormacionEmpresasService miFormacionEmpresasService;

	@Autowired
	private IDatosUsuarioJwtService datosUsuarioJwtService;

	@Autowired
	IParamsFCTService iParamsFCTService;


	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Datos por matrícula", description = "Devuelve el listado de estados de validación asociados a una matrícula para planes",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosFormacionDto.class)))})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK, listado devuelto correctamente"),
			@ApiResponse(responseCode = "404", description = "Matrícula no encontrada"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	@GetMapping("/getDatosFormacionPlan/{idMatricula}")
	public ResponseEntity<List<DatosFormacionPlanDto>> getDatosFormacionPlan(@PathVariable("idMatricula") Long idMatricula) {
		try {
			List<DatosFormacionPlanDto> datos = miFormacionEmpresasService.getDatosFormacionPlan(idMatricula);

			return new ResponseEntity<>(datos, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Datos por matrícula", description = "Devuelve el listado de estados de validación asociados a una matrícula para proyectos dual",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosFormacionDto.class)))})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK, listado devuelto correctamente"),
			@ApiResponse(responseCode = "404", description = "Matrícula no encontrada"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	@GetMapping("/getDatosFormacionProyecto/{idMatricula}")
	public ResponseEntity<List<DatosFormacionDto>> getDatosFormacionProyecto(@PathVariable("idMatricula") Long idMatricula) {
		try {
			List<DatosFormacionDto> datos = miFormacionEmpresasService.getDatosFormacionProyecto(idMatricula);

			return new ResponseEntity<>(datos, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Datos por matrícula", description = "Devuelve el listado de estados de validación asociados a una matrícula para programas",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosFormacionDto.class)))})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK, listado devuelto correctamente"),
			@ApiResponse(responseCode = "404", description = "Matrícula no encontrada"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
		@GetMapping("/getDatosFormacionPrograma/{idMatricula}")
	public ResponseEntity<List<DatosFormacionDto>> getDatosFormacionPrograma(@PathVariable("idMatricula") Long idMatricula) {
		try {
			List<DatosFormacionDto> datos = miFormacionEmpresasService.getDatosFormacionPrograma(idMatricula);

			return new ResponseEntity<>(datos, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Listado tipos prácticas alumno", description = "Este devuelve los tipos de prácticas que tiene un alumno: Plan, Programa o Proyecto", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getTipoPracticasAlumno/{cAnno}" })
	public ResponseEntity<List<ElementoSelectDto>> getTipoPracticasAlumno(@PathVariable("cAnno") Integer cAnno, @RequestHeader(Constants.AUTHORIZATION) String jwt) {

		DatosUsuarioJwt datosUsuario = datosUsuarioJwtService.getDatosUsuarioByJwt(jwt);

		Long xEmpleadoComunica = iParamsFCTService.getXempleadoComunicaByOid(datosUsuario.getOid(), datosUsuario.getIdEmpleadoComunica());

		List<ElementoSelectDto> tiposAlumno = miFormacionEmpresasService.getTipoPracticasAlumno(cAnno, xEmpleadoComunica);

		return new ResponseEntity<>(tiposAlumno, HttpStatus.OK);
	}
	
}
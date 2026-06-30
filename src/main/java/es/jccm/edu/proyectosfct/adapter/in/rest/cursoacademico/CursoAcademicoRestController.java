package es.jccm.edu.proyectosfct.adapter.in.rest.cursoacademico;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.adapter.in.rest.cursoacademico.model.CursoAcademicoDto;
import es.jccm.edu.proyectosfct.application.domain.cursoacademico.CursoAcademico;
import es.jccm.edu.proyectosfct.application.ports.in.cursoacademico.ICursoAcademicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Programas FCT", description = "Servicio con las operaciones sobre Programas FCT")
public class CursoAcademicoRestController {

	@Autowired
	ICursoAcademicoService cursoAcademicoService;
	
	@Autowired
	private ModelMapper modelMapper;

	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','ALU','CFT')")
	@Operation(summary = "Lista de Cursos academicos", description = "Este metodo devuelve una lista con todos los cursos academicos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllCursosAcademicos"})
	public ResponseEntity<List<CursoAcademicoDto>> getAllCursosAcademicos() {
		
		List<CursoAcademico> cursos = cursoAcademicoService.getAllCursosAcademicos();
		
		List<CursoAcademicoDto> cursosOut = cursos.stream().map(entity -> modelMapper.map(entity, CursoAcademicoDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<CursoAcademicoDto>>(cursosOut, HttpStatus.OK);
	}
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Curso Actual", description = "Este metodo devuelve el curso actual", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getCursoActual"})
	public ResponseEntity<CursoAcademicoDto> getCursoActual() {

		CursoAcademico curso = cursoAcademicoService.getCursoActual();
		
		CursoAcademicoDto cursoOut = modelMapper.map(curso, CursoAcademicoDto.class);
		
		return new ResponseEntity<CursoAcademicoDto>(cursoOut, HttpStatus.OK);

	}
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Curso Actual", description = "Este metodo devuelve el curso actual pasando como parámetro el año", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getCursoActualAnno/{cAnno}"})
	public ResponseEntity<CursoAcademicoDto> getCursoActualAnno(@PathVariable("cAnno") Long cAnno) {

		CursoAcademico curso = cursoAcademicoService.getCursoActualAnno(cAnno);
		
		CursoAcademicoDto cursoOut = modelMapper.map(curso, CursoAcademicoDto.class);
		
		return new ResponseEntity<CursoAcademicoDto>(cursoOut, HttpStatus.OK);

	}

}

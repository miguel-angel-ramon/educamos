package es.jccm.edu.documentosGC.adapter.in.rest.cursoacademicodoc;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.cursoacademicodoc.model.CursoAcademicoDocDto;
import es.jccm.edu.documentosGC.application.domain.cursoacademicodoc.entities.CursoAcademicoDoc;
import es.jccm.edu.documentosGC.application.ports.in.cursoacademicodoc.ICursoAcademicoDocService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping(BasePath.DocumentosgcBasePath)
@Tag(name = "Servicio Curso Académico documentos", description = "Servicio con las operaciones de los cursos académicos Documentos GC")
public class CursoAcademicoDocRestController {

	@Autowired
	ICursoAcademicoDocService cursoAcademicoDocService;
	
	@Autowired
	private ModelMapper modelMapper;

	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Lista de Cursos academicos", description = "Este metodo devuelve una lista con todos los cursos academicos", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getAllCursosAcademicos"})
	public ResponseEntity<List<CursoAcademicoDocDto>> getAllCursosAcademicos() {
		
		List<CursoAcademicoDoc> cursos = cursoAcademicoDocService.getAllCursosAcademicos();
		
		List<CursoAcademicoDocDto> cursosOut = cursos.stream().map(entity -> modelMapper.map(entity, CursoAcademicoDocDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<CursoAcademicoDocDto>>(cursosOut, HttpStatus.OK);
	}
	
	
	/**
	 * Lista datos.
	 *
	 * @return the response entity
	 */
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','N')")
	@Operation(summary = "Curso Actual", description = "Este metodo devuelve el curso actual", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping({ "/getCursoActual"})
	public ResponseEntity<CursoAcademicoDocDto> getCursoActual() {

		CursoAcademicoDoc curso = cursoAcademicoDocService.getCursoActual();
		
		CursoAcademicoDocDto cursoOut = modelMapper.map(curso, CursoAcademicoDocDto.class);
		
		return new ResponseEntity<CursoAcademicoDocDto>(cursoOut, HttpStatus.OK);

	}	

}
package es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.ConvProgAluHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConvProgHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConveniosProgramasAnexosDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConveniosProgramasFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConveniosProgramasHorarioAlumnoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ParteSemanalAnexosProgramaDto;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.Alumno;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasAnexos;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasFct;
import es.jccm.edu.proyectosfct.application.domain.programas.entities.ParteSemanalAnexosPrograma;
import es.jccm.edu.proyectosfct.application.ports.in.alumnoprograma.IAlumnoProgramaService;
import es.jccm.edu.proyectosfct.application.ports.in.conveniosprogramas.IConveniosProgramasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio Programas de un convenio FCT", description = "Servicio con las operaciones sobre Programas de un convenio FCT")
public class ConveniosProgramasRestController {
	
	@Autowired
	private IConveniosProgramasService conveniosProgramasService;
	
	@Autowired
	IAlumnoProgramaService alumnoProgramaService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	/**
	 * Create convenios a programa.
	 *
	 * @param idConvenio Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de convenios a programa", description = "Este metodo crea convenios a programa", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createConvenioProgramas")
	public ResponseEntity<Object> createConvenioProgramas(
			@Parameter(description = "Convenio a programa", required = true) @RequestBody final List<ConveniosProgramasHorarioAlumnoFctDto> listConveniosProgramasHorAluFctDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);			
		
//			List<ConveniosProgramasFct> conveniosProgramasFctListIn = listConveniosProgramasHorAluFctDto.stream().map(entity -> modelMapper.map(entity, ConveniosProgramasFct.class)).collect(Collectors.toList());			
		
			List<ConveniosProgramasFct> conveniosProgramasFctListIn = conveniosProgramasService.createConvenioProgramasHorarioAlumno(listConveniosProgramasHorAluFctDto);
		
			List<ConveniosProgramasFctDto> conveniosProgramasFctListOut = conveniosProgramasFctListIn.stream().map(entity -> modelMapper.map(entity, ConveniosProgramasFctDto.class)).collect(Collectors.toList());
		
			response = new ResponseEntity<>(conveniosProgramasFctListOut, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}
	
	/**
	 * Create convenios programa horario alumnado.
	 *
	 * @param idConvenio Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Creación de convenios programa horario alumnado", description = "Este metodo crea convenio programa horario alumnado", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/createConvenioProgramaPeriodosHorarios")
	public ResponseEntity<Object> createConvenioProgramaPeriodosHorarios(
			@Parameter(description = "Convenio programa horario alumnado", required = true) @RequestBody final List<ConvProgHorPeriodoFctDto> listConvProgHorPeriodoFctDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);						
		
			List<ConvProgHorPeriodoFctDto> listConvProgHorPeriodoFctOut = conveniosProgramasService.createConvenioProgramaPeriodosHorarios(listConvProgHorPeriodoFctDto);
		
			response = new ResponseEntity<>(listConvProgHorPeriodoFctOut, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}
	
	
	/**
	 * Delete convenio programas.
	 *
	 * @param ConveniosProgramasFctDto
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Delete convenio programas", description = "Este metodo delete convenio programas", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
		@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
		@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
		@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/deleteConvenioProgramas")
	public ResponseEntity<Object> deleteConvenioProgramas(
			@Parameter(description = "Convenio a programa", required = true) @RequestBody final List<ConveniosProgramasHorarioAlumnoFctDto> listConveniosProgramasHorAluFctDto) {
		
		ResponseEntity<Object> response = null;
		try {
		
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
//			List<ConveniosProgramasFct> convenioProgramasFctListIn = ListConveniosProgramasFctDto.stream().map(entity -> modelMapper.map(entity, ConveniosProgramasFct.class)).collect(Collectors.toList());
		
			conveniosProgramasService.deleteConvenioProgramasHorarioAlumno(listConveniosProgramasHorAluFctDto);
		
			response = new ResponseEntity<>(null, HttpStatus.OK);
			
		}catch (Exception e) {
			response = new ResponseEntity<>("Se ha producido un error.", HttpStatus.BAD_REQUEST);
		}
	
		return response;
	}
	
	
	
	
	/**
	 * Asocia Convenio a programas.
	 *
	 * @param idConvenio Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@Operation(summary = "Asocia Convenio a programas", description = "Este metodo asocia un convenio a un conjunto de Programas",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping("/convenioAprogramas/{idConvenio}")
	public ResponseEntity<List<ConveniosProgramasFctDto>> convenioAprogramas(
			@Parameter(description = "Asocia Convenio a programas", required = true) @RequestBody final List<ConveniosProgramasFctDto> convprogFctDto,
			@PathVariable("idConvenio") Long idConvenio) {
		try {
			
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<ConveniosProgramasFct> convprogFct = convprogFctDto.stream().map(x -> modelMapper.map(x, ConveniosProgramasFct.class)).collect(Collectors.toList());
					
			convprogFct = conveniosProgramasService.convenioAprogramas(convprogFct, idConvenio);
			
			List<ConveniosProgramasFctDto> convprogFctDtoUpdate = convprogFct.stream().map(x -> modelMapper.map(x, ConveniosProgramasFctDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<ConveniosProgramasFctDto>>(convprogFctDtoUpdate, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Devuelve la lista de programas asociadas a un convenio.
	 *
	 * @param idConvenio Id del convenio
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Lista de programas de un convenio", description = "Este metodo devuelve la lista de programas de un convenio",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "OK, borrado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getProgramasConvenio/{idConvenio}/{cAnno}")
	public ResponseEntity<List<ConveniosProgramasFctDto>> getProgramasConvenio(
			@Parameter(description = "Identificador del convenio-programa", required = true) @PathVariable("idConvenio") Long idConvenio,
																							 @PathVariable("cAnno") Integer cAnno) {
		try {

			List<ConveniosProgramasFct> convprogFct = conveniosProgramasService.getProgramasConvenio(idConvenio, cAnno);

			List<ConveniosProgramasFctDto> convprogFctDto = convprogFct.stream().map(x -> modelMapper.map(x, ConveniosProgramasFctDto.class)).collect(Collectors.toList());
			
			for (int i = 0; i < convprogFctDto.size(); i++) {
				
				//Integer anno = conveniosProgramasService.getAnnoConvenioPrograma(convprogFctDto.get(i).getId());
				
				List<Alumno> alumnos =  alumnoProgramaService.getAlumnosSeleccionados(convprogFctDto.get(i).getConvenio().getCentro().getId(), 
																					  convprogFctDto.get(i).getPrograma().getOfertaMatriculaGenerico().getId(), 
																					  cAnno,
																					  convprogFctDto.get(i).getId());
				
				List<AlumnoDto> alumnosDto = alumnos.stream().map(x -> modelMapper.map(x, AlumnoDto.class)).collect(Collectors.toList());
				
				convprogFctDto.get(i).setAlumnos(alumnosDto);
				
			}
			
			return new ResponseEntity<List<ConveniosProgramasFctDto>>(convprogFctDto, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Datos de un Convenio-Programa.
	 *
	 * @param id Id del Convenio-Programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener datos de un Convenio-Programa", description = "Este metodo devuelve los datos de un Convenio-Programa", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvenioProgramaById/{idConvProg}")
	public ResponseEntity<ConveniosProgramasFctDto> getEmpresaById(
			@Parameter(description = "Identificador del convenio-programa", required = true) @PathVariable("idConvProg") Long idConvProg) {
		
		ConveniosProgramasFctDto convProg = modelMapper.map(conveniosProgramasService.getConvenioProgramaFct(idConvProg), ConveniosProgramasFctDto.class);
		
		return new ResponseEntity<ConveniosProgramasFctDto>(convProg, HttpStatus.OK);
	}
	
	/**
	 * Devuelve el número de convenios-programas por id convenio
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Contador de convenios-programas por id convenio", description = "Este metodo devuelve el numero de convenios-programas por id convenio",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCountConveniosByidConvenio/{idConvenio}")
	public ResponseEntity<Integer> getCountEmpresas(@PathVariable("idConvenio") Long idConvenio){
		return new ResponseEntity<Integer>(conveniosProgramasService.countByConvenioId(idConvenio), HttpStatus.OK);
		
	}
	
	/**
	 * Devuelve los horarios generales de un convenio programa
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Horarios generales de un convenio programa", description = "Este metodo devuelve los horarios generales de un convenio programa",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvenioProgramaPeriodosHorarios/{idConvProg}")
	public ResponseEntity<List<ConvProgHorPeriodoFctDto>> getConvenioProgramaPeriodosHorarios(@PathVariable("idConvProg") Long idConvProg){
		
		List<ConvProgHorPeriodoFctDto> periodosDto = conveniosProgramasService.getConvenioProgramaPeriodosHorarios(idConvProg);
		
		return new ResponseEntity<List<ConvProgHorPeriodoFctDto>>(periodosDto, HttpStatus.OK);		
	}
	
	/**
	 * Devuelve el horario personal de un convenio programa alumno
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT')")
	@Operation(summary = "Horario personal de un convenio programa alumno", description = "Este metodo devuelve el horario personales de un convenio programa alumno",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getConvenioProgramaPeriodosHorariosAlumno/{idConvProg}/{idMatricula}")
	public ResponseEntity<List<ConvProgAluHorPeriodoFctDto>> getConvenioProgramaPeriodosHorariosAlumno(@PathVariable("idConvProg") Long idConvProg, 
																								       @PathVariable("idMatricula") Long idMatricula){
		
		List<ConvProgAluHorPeriodoFctDto> periodosDto = conveniosProgramasService.getConvenioProgramaPeriodosHorariosAlumno(idConvProg, idMatricula);
		
		return new ResponseEntity<List<ConvProgAluHorPeriodoFctDto>>(periodosDto, HttpStatus.OK);		
	}
	
	/**
	 * Datos de un Convenio-Programa.
	 *
	 * @param id Id del Convenio-Programa
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener el anno de un Convenio-Programa", description = "Este metodo devuelve el anno de un Convenio-Programa", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAnnoConvenioPrograma/{idConvProg}")
	public ResponseEntity<Integer> getAnnoConvenioPrograma(
			@Parameter(description = "Identificador del convenio-programa", required = true) @PathVariable("idConvProg") Long idConvProg) {
		
		Integer anno = conveniosProgramasService.getAnnoConvenioPrograma(idConvProg);
		
		return new ResponseEntity<Integer>(anno, HttpStatus.OK);
	}
	
	
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Obtener el anno de un Convenio-Programa", description = "Este metodo devuelve el anno de un Convenio-Programa", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getCalcularHorasAlumnos/{id}/{idAlumno}")
	public ResponseEntity<Double> getCalcularHorasAlumnos(
			@Parameter(description = "Identificador del convenio-programa", required = true) @PathVariable("id") Long id,
																							 @PathVariable("idAlumno") Long idAlumno) {
		
		Double totalHoras = conveniosProgramasService.getHorasAlumnos(id, idAlumno);
		
		return new ResponseEntity<Double>(totalHoras, HttpStatus.OK);
	}
	
	/**
	 * Upload fichero para la autorizacion del desplazamiento.
	 *
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU', 'FCT')")
	@Operation(summary = "Upload fichero autorizacion desplazamiento", description = "Upload fichero autorizacion desplazamiento", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/uploadFicherosAnexoProg/{idConvProg}/{tipo}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> uploadFicherosAnexo(@PathVariable("idConvProg") Long idConvProg,
																		  @PathVariable("tipo") String tipo,
																		  @RequestPart List<MultipartFile> ficheros) {
		
		
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			conveniosProgramasService.uploadFicherosAnexo(idConvProg, tipo, ficheros);
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//@PreAuthorize("hasAnyRole('P','PRO','C','FCT','CFT')")
	@Operation(summary = "Anexos II", description = "Este metodo devuelve los identificadores de los anexos convenios programas", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAnexoII/{idConvProg}")
	public ResponseEntity<Object> getAnexoII(
			@Parameter(description = "Identificador del convenio-programa", required = true) @PathVariable("idConvProg") Long idConvProg) {
						
		
		try {
		
			List<ConveniosProgramasAnexos> anexosIn = conveniosProgramasService.getAnexoII(idConvProg);
			
			List<ConveniosProgramasAnexosDto> anexosInDto = anexosIn.stream().map(entity -> modelMapper.map(entity, ConveniosProgramasAnexosDto.class)).collect(Collectors.toList());
		
			return new ResponseEntity<>(anexosInDto, HttpStatus.OK);	
			
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//@PreAuthorize("hasAnyRole('P','PRO')")
	@Operation(summary = "Upload partes semanales", description = "Upload partes semanales FCT", responses = {
			@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, creado correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@PostMapping(value="/uploadFicherosParteSemanalAnexosPrograma/{idConvProgAlu}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<ParteSemanalAnexosProgramaDto>> uploadFicherosParteSemanalAnexosPrograma(@PathVariable("idConvProgAlu") Long idConvProgAlu,
																		  @RequestPart List<MultipartFile> files) {
		
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			
			List<ParteSemanalAnexosPrograma> parsemAluProg	= conveniosProgramasService.uploadFicherosParteSemanalAnexosProyecto(idConvProgAlu, files);
			
			List<ParteSemanalAnexosProgramaDto> parsemAneProg = parsemAluProg.stream().map(x -> modelMapper.map(x, ParteSemanalAnexosProgramaDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<>(parsemAneProg,HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	/**
	 * Servicio que devuelve los datos de los partes semanales del alumno
	 * @param idConvProgAlu
	 * @return the response entity
	 */
	//@PreAuthorize("hasAnyRole('P','PRO','ALU','C','FCT','CFT')")
	@Operation(summary = "Descarga de partes semanales", description = "Este metodo devuelve los partes semanas del alumno", 
		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getFicherosParteSemanalAnexosPrograma/{idConvProgAlu}")
	public ResponseEntity<List<ParteSemanalAnexosProgramaDto>> getFicherosParteSemanalAnexosPrograma(@Parameter(description = "Identificador del convenio-programa de alumno", required = true) 
																		@PathVariable("idConvProgAlu") Long idConvProgAlu) {			
		
		try {
		
			List<ParteSemanalAnexosPrograma> anexosIn = conveniosProgramasService.getParteSemanalAnexosPrograma(idConvProgAlu);
			
			List<ParteSemanalAnexosProgramaDto> anexosInDto = anexosIn.stream().map(entity -> modelMapper.map(entity, ParteSemanalAnexosProgramaDto.class)).collect(Collectors.toList());
		
			
			return new ResponseEntity<>(anexosInDto, HttpStatus.OK);	
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "", description = "",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/checkOverlappingDatesProg")
	public ResponseEntity<Integer> checkOverlappingDatesProg(@RequestParam("idPrograma") Long idPrograma,
														@RequestParam("idConvenio") Long idConvenio,
														@RequestParam("fechaInicio") String fechaInicio,
														@RequestParam("fechaFin") String fechaFin){

		try {

			return new ResponseEntity<Integer>(conveniosProgramasService.checkOverlappingDates(idPrograma, idConvenio, fechaInicio, fechaFin), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	//@PreAuthorize("hasAnyRole('P','PRO','C', 'ALU','FCT','CFT')")
	@Operation(summary = "", description = "",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado")})
	@PutMapping("/updateProgramDates")
	public ResponseEntity<Object> updateProgramDates(@RequestParam("idConvProg") Long idConvProg,
													 @RequestParam("fechaInicio") String fechaInicio,
													 @RequestParam("fechaFin") String fechaFin,
													 @RequestParam("newHora") Integer newHora) {

		try {

			conveniosProgramasService.updateProgramDates(idConvProg, fechaInicio, fechaFin, newHora);

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
}

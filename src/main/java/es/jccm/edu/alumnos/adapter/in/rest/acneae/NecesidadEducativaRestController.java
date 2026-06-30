package es.jccm.edu.alumnos.adapter.in.rest.acneae;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.alumnos.adapter.in.rest.acneae.model.CompensacionDesigualdadDTO;
import es.jccm.edu.alumnos.adapter.in.rest.acneae.model.NecesidadEducativaDTO;
import es.jccm.edu.alumnos.application.domain.acneae.CompensacionDesigualdad;
import es.jccm.edu.alumnos.application.domain.acneae.NecesidadEducativa;
import es.jccm.edu.alumnos.application.ports.in.acneae.ICompensacionDesigualdadService;
import es.jccm.edu.alumnos.application.ports.in.acneae.INecesidadEducativaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/alumnos")
@Tag(name="Servicio Necesidades Educativas", description="Servicio para recuperar datos sobre necesidades educativas especiales")
//@CrossOrigin
public class NecesidadEducativaRestController {
	
	@Autowired
	private INecesidadEducativaService neeService;
	@Autowired
	private ICompensacionDesigualdadService compensacionService;
	@Autowired 
	private ModelMapper modelMapper;
	
	
	@Operation(summary="Recuperar Listado de Necesides Educativas ",
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))) })
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
					@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
					@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
					@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping ("/acneae/nee")
	public ResponseEntity <CollectionModel<NecesidadEducativaDTO>> getNecesidadesEducativas(@RequestParam Optional< String> adaptacion) {
	
		List <NecesidadEducativa> nee=new ArrayList<NecesidadEducativa>();
		if(adaptacion.isPresent()) {
			nee=neeService.getAllNEEByAdaptacion(adaptacion.get());
		
		}else {
			nee=neeService.getAllNEE(); 
		}
	
		List<NecesidadEducativaDTO> neeDto=nee.stream().map(x->modelMapper.map(x, NecesidadEducativaDTO.class)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(CollectionModel.of(neeDto));
		
	}
	
		@Operation(summary="Recuperar Listado de Necesidades Educativas de una matricula",
			responses= {@ApiResponse(content=@Content(mediaType="application/json",schema=@Schema(implementation=ResponseEntity.class))) })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
			@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
			@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
			@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("acneae/nee/{idMatricula}")
	public ResponseEntity<CollectionModel<NecesidadEducativaDTO>> getNecesidadesEducativasPorMatricula(@PathVariable Long idMatricula,
			@RequestParam Optional <String> adaptacion ){
		
		List<NecesidadEducativa>nee=new ArrayList<NecesidadEducativa>();
		if(adaptacion.isPresent()){
			nee=neeService.getNEEByAdaptacionAndMatricula(adaptacion.get(), idMatricula);
		}else {
			nee=neeService.getNEEByMatricula(idMatricula);
		}
				
		List<NecesidadEducativaDTO>neeDto=nee.stream().map(x->modelMapper.map(x, NecesidadEducativaDTO.class)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(CollectionModel.of(neeDto));
	}
	
		@Operation(summary="Recuperar Listado de Compensación Desigualdades",
				responses= {@ApiResponse(content=@Content(mediaType="application/json",schema=@Schema(implementation=ResponseEntity.class))) })
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("acneae/ance")
		public ResponseEntity <CollectionModel<CompensacionDesigualdadDTO>>getCompensacionesDesigualdad(){
			
			List <CompensacionDesigualdad> comp=compensacionService.getAllCompensaciones();
			List <CompensacionDesigualdadDTO> compDto=comp.stream().map(x->modelMapper.map(x, CompensacionDesigualdadDTO.class))
					.collect(Collectors.toList());
			return ResponseEntity.ok().body(CollectionModel.of(compDto));
			
		}
		
		@Operation(summary="Recuperar Listado de Compensación Desigualdades por matricula ",
				responses= {@ApiResponse(content=@Content(mediaType="application/json",schema=@Schema(implementation=ResponseEntity.class))) })
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
		@GetMapping("acneae/ance/{idMatricula}")
		public ResponseEntity <CollectionModel<CompensacionDesigualdadDTO>> getCompensacionesByMatricula(@PathVariable Long idMatricula){
			
			List<CompensacionDesigualdad> comp=compensacionService.getCompensacionesByMatricula(idMatricula);
			List <CompensacionDesigualdadDTO> compDto=comp.stream().map(x->modelMapper.map(x, CompensacionDesigualdadDTO.class))
					.collect(Collectors.toList());
			return ResponseEntity.ok().body(CollectionModel.of(compDto));
		}

}

package es.jccm.edu.documentosGC.adapter.in.rest.bandejafirmas;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.adapter.in.rest.bandejafirmas.model.BandejaFirmasListDto;
import es.jccm.edu.documentosGC.adapter.in.rest.bandejafirmas.model.EstadosBandejaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.bandejafirmas.model.TipoAdjuntoBandejaDto;
import es.jccm.edu.documentosGC.adapter.in.rest.bandejafirmas.model.TipoDocumentoBandejaDto;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.BandejaFirmasList;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.EstadosBandeja;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.TipoAdjuntoBandeja;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.TipoDocumentoBandeja;
import es.jccm.edu.documentosGC.application.ports.in.bandejafirmas.IBandejaFirmasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping(BasePath.DocumentosgcBasePath)
@Tag(name = "BandejaFirmas", description = "Servicio con las operaciones sobre las firmas de actas documentosgc")
public class BandejaFirmasRestController {
	
	@Autowired
	private IBandejaFirmasService bandejaFirmasService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','P','PRO')")
	@Operation(summary = "Tipo documento", description = "Este metodo devuelve la informacion completa los tipo de documentos bandeja", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTiposDocumentoBandeja/{cAnno}")
	public ResponseEntity<List<TipoDocumentoBandejaDto>> getTiposDocumentoBandeja(@PathVariable("cAnno") Long cAnno) {
		
		List<TipoDocumentoBandeja> tipo =  bandejaFirmasService.getTiposDocumentoBandeja(cAnno);
		
		List<TipoDocumentoBandejaDto> tipoDto = tipo.stream().map(entity -> modelMapper.map(entity, TipoDocumentoBandejaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<TipoDocumentoBandejaDto>>(tipoDto, HttpStatus.OK);		
	}
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','P','PRO')")
	@Operation(summary = "Tipos adjunto documento", description = "Este metodo devuelve la informacion completa los tipos de adjuntos documentos bandeja", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getTiposAdjuntosBandeja/{cAnno}/{idTipoDocumento}")
	public ResponseEntity<List<TipoAdjuntoBandejaDto>> getTiposAdjuntosBandeja(@PathVariable("cAnno") Long cAnno,
																			   @PathVariable("idTipoDocumento") Long idTipoDocumento) {
		
		List<TipoAdjuntoBandeja> tipoAdjunto =  bandejaFirmasService.getTiposAdjuntosBandeja(cAnno,idTipoDocumento);
		
		List<TipoAdjuntoBandejaDto> tipoAdjuntoDto = tipoAdjunto.stream().map(entity -> modelMapper.map(entity, TipoAdjuntoBandejaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<TipoAdjuntoBandejaDto>>(tipoAdjuntoDto, HttpStatus.OK);		
	}
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','P','PRO')")
	@Operation(summary = "Estados adjunto documento", description = "Este metodo devuelve la informacion completa los estados documentos bandeja", 
			responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getEstadosDocumentoBandeja/{cAnno}/{idTipoDocumento}")
	public ResponseEntity<List<EstadosBandejaDto>> getEstadosDocumentoBandeja(@PathVariable("cAnno") Long cAnno,
																			  @PathVariable("idTipoDocumento") Long idTipoDocumento) {
		
		List<EstadosBandeja> estados =  bandejaFirmasService.getEstadosDocumentoBandeja(cAnno,idTipoDocumento);
		
		List<EstadosBandejaDto> estadosDto = estados.stream().map(entity -> modelMapper.map(entity, EstadosBandejaDto.class)).collect(Collectors.toList());
		
		return new ResponseEntity<List<EstadosBandejaDto>>(estadosDto, HttpStatus.OK);		
	}	
	
	@PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','P','PRO')")
	@Operation(summary = "Listado bandeja firmas", description = "Este metodo devuelve un listado con los documentos de las actas de evaluacion para firmar", 
	           responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
						   @ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
						   @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
						   @ApiResponse(responseCode = "404", description = "No encontrado") })
	@GetMapping("/getAllDocumentosBandeja/{cAnno}/{idTipoDocumento}/{idTipoAdjunto}/{idEstado}/{miFirma}/{idEmpleado}/{fechaGeneracion}/{fechaFirma}")
	public ResponseEntity<List<BandejaFirmasListDto>> getAllDocumentosBandeja(@PathVariable("cAnno") Long cAnno,
																			  @PathVariable("idTipoDocumento") Long idTipoDocumento,
																			  @PathVariable("idTipoAdjunto") Long idTipoAdjunto,
																			  @PathVariable("idEstado") Long idEstado,
																			  @PathVariable("miFirma") String miFirma,
																			  @PathVariable("idEmpleado") Long idEmpleado,
																			  @PathVariable("fechaGeneracion") String fechaGeneracion,
																			  @PathVariable("fechaFirma") String fechaFirma,
																			  @RequestParam(value = "perfil", required = false) String perfil) {
			
			
		if(StringUtils.isEmpty(perfil)) {
			perfil = "-1";
		}
		
		List<BandejaFirmasList> bandeja =  bandejaFirmasService.getAllDocumentosBandeja(cAnno,
															  					        idTipoDocumento,
															  					        idTipoAdjunto,
																					    idEstado,
																					    miFirma,
																					    idEmpleado,
																					    fechaGeneracion,
																					    fechaFirma,
																					    perfil);
			
		List<BandejaFirmasListDto> bandejaDto = bandeja.stream()
							.map(entity -> modelMapper.map(entity, BandejaFirmasListDto.class)).collect(Collectors.toList());
			
			return new ResponseEntity<List<BandejaFirmasListDto>>(bandejaDto, HttpStatus.OK);		
	}
	
	
	
	
	
	
		
}
	
	
	
	
	
	
	
	
	
	
	



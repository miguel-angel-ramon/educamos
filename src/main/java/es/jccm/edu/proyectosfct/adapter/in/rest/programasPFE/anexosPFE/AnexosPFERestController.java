package es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.anexosPFE;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.jccm.edu.proyectosfct.adapter.in.rest.programasPFE.anexosPFE.model.InfoAnexosDto;
import es.jccm.edu.proyectosfct.application.domain.programasPFE.entities.InfoAnexos;
import es.jccm.edu.proyectosfct.application.ports.in.programasPFE.anexosPFE.IAnexosFPE;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
@Tag(name = "Servicio anexos programas PFE", description = "Servicio con las operaciones sobre anexos programas PFE")
public class AnexosPFERestController {
	
	private static final String ASIGNACION_MODULOS = "Error asignando módulos";
	private static final String MESSAGE = "message";
	private static final String DETAIL = "detail";
	
    @Autowired
    private ModelMapper modelMapper;
	
    @Autowired
    private IAnexosFPE anexosFPE;
    
    @PostMapping("/createAnexosPFE/{idProgramaFPE}/{cAnno}/{xCentro}")
	public ResponseEntity<Object> saveAnexosPFE(
			@PathVariable("idProgramaFPE") Long idProgramaFPE,
			@PathVariable("cAnno") Integer cAnno,
			@PathVariable("xCentro") Long xCentro,
			@RequestParam("tipos") List<Integer> tipos,			
			@RequestParam("files") List<MultipartFile> files) {
	
		try {		
			
			anexosFPE.createModulosFPE(idProgramaFPE, tipos, files, cAnno, xCentro );
	        
	        return ResponseEntity.ok().build();
	        
	    } catch (Exception e) {
	        return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(
	                Map.of(
	                	MESSAGE, ASIGNACION_MODULOS,
	                	DETAIL,  e.getMessage()
	                )
	            );
	    }
	}
    
    
    @GetMapping("/getInfoAnexosFPE/{idProgramaFPE}")
    public ResponseEntity<List<InfoAnexosDto>> getInfoAnexosAut(
            @PathVariable("idProgramaFPE") Long idProgramaFPE) {
        try {
            // Obtener la lista de DTO desde el servicio
            List<InfoAnexos> anexos = anexosFPE.getInfoAnexosAut(idProgramaFPE);

            List<InfoAnexosDto> anexosDto = anexos.stream().map(x->modelMapper.map(x, InfoAnexosDto.class)).collect(Collectors.toList());
            
            
            // Devolver siempre 200, con lista vacía si no hay datos
            return new ResponseEntity<>(anexosDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/deleteAnexo/{id}")
	public ResponseEntity<Object> deleteAnexo(@PathVariable("id") Long id) {
	
		try {		
			
			anexosFPE.deleteAnexo(id);
	        
	        return ResponseEntity.ok().build();
	        
	    } catch (Exception e) {
	        return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(
	                Map.of(
	                	MESSAGE, ASIGNACION_MODULOS,
	                	DETAIL,  e.getMessage()
	                )
	            );
	    }
	}
    
    @PostMapping("/actualizaAnexoFPE/{id}")
   	public ResponseEntity<Object> actualizaAnexo(@PathVariable("id") Long id,
   			                                     @RequestParam("file") MultipartFile file) {
   	
   		try {		
   			
   			anexosFPE.actualizaAnexo(id,file);
   	        
   	        return ResponseEntity.ok().build();
   	        
   	    } catch (Exception e) {
   	        return ResponseEntity
   	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
   	            .body(
   	                Map.of(
   	                	MESSAGE, ASIGNACION_MODULOS,
   	                	DETAIL,  e.getMessage()
   	                )
   	            );
   	    }
   	}
    
    @PostMapping("/insertaAnexoFPE/{idProgramaFPE}/{cAnno}/{xCentro}/{tipo}")
   	public ResponseEntity<Object> insertaAnexo(@PathVariable("idProgramaFPE") Long idProgramaFPE,
			                                   @PathVariable("cAnno") Integer cAnno,
			                                   @PathVariable("xCentro") Long xCentro,
			                                   @PathVariable("tipo") Integer tipo,			
			                                   @RequestParam("file") MultipartFile file) {
   	
   		try {		
   			
   			anexosFPE.insertaAnexo(idProgramaFPE,cAnno,xCentro,tipo,file);
   	        
   	        return ResponseEntity.ok().build();
   	        
   	    } catch (Exception e) {
   	        return ResponseEntity
   	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
   	            .body(
   	                Map.of(
   	                	MESSAGE, ASIGNACION_MODULOS,
   	                	DETAIL,  e.getMessage()
   	                )
   	            );
   	    }
   	}

    
}

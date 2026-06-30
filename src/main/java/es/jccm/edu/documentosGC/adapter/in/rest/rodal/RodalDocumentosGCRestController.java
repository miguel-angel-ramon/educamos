package es.jccm.edu.documentosGC.adapter.in.rest.rodal;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;
import com.jccm.cstic.clientesws.clienterodal.RecuperarDocMetadatosFault;

import es.jccm.edu.documentosGC.adapter.in.rest.BasePath;
import es.jccm.edu.documentosGC.application.ports.in.rodal.RodalClientDgc;
import es.jccm.edu.documentosGC.application.ports.in.rodal.RodalExceptionDgc;
//import es.jccm.edu.shared.application.services.rodal.RodalExceptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping(BasePath.DocumentosgcBasePath)
@Tag(name = "Servicios Rodal Documentos GC", description = "Servicios con las operaciones ficheros GC")

public class RodalDocumentosGCRestController {
	
	    @Autowired
	    private RodalClientDgc rodalClient;
	
	   /**
		 * Descarga el documento de rodal que corresponde con el idDoc recibido.
		 *
		 * @param idDoc
		 * @return ResponseEntity<RodalDocDto>
		 */
	    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','P','PRO')")
	    @Operation(summary = "Descargar documento", description = "Este metodo descarga el documento de rodal que corresponde con el idDoc recibido", 
	    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	    @GetMapping("/descargarDocumento/{idRodal}/{txNombreFichero}")
	    public void descargarDoc(@Parameter(description = "I de la entidad FCT que sube el fichero a Rodal", required = true) @PathVariable("idRodal") String idRodal,
	    				         @Parameter(description = "Nombre del fichero guardado en la entidad", required = true) @PathVariable("txNombreFichero") String txNombreFichero,
	    		                 HttpServletResponse response) throws RodalExceptionDgc, RecuperarDocMetadatosFault, IOException {
			
			// Recuperamos el documento de rodal
			ByteArrayOutputStream bytes = null ;
			try {
				bytes = rodalClient.recuperaDoc(idRodal, "DGC");
			} catch (RodalExceptionDgc e) {
				e.printStackTrace();
			}
			
			// Descargamos el documento recuperado
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=" + txNombreFichero);
			OutputStream out = response.getOutputStream();
			if(bytes!=null) out.write(bytes.toByteArray());		
			
	    }
	    
	    
	    /**
		 * Actualiza un fichero Rodal a partir de su ID.
		 *
		 * @param MultipartFile
		 * @return ResponseEntity<RodalDocDto>
		 */
	    @PreAuthorize("hasAnyRole('C','I','INZ','INC','ICO','P','PRO')")
	    @Operation(summary = "Actualizar documento", description = "Este metodo actualiza un documento recibido en rodal", 
	    		responses = {@ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)))})
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, obtenido correctamente"),
				@ApiResponse(responseCode = "401", description = "No esta autorizado para realizar esta operacion"),
				@ApiResponse(responseCode = "403", description = "Acceso prohibido"),
				@ApiResponse(responseCode = "404", description = "No encontrado") })
	    @PostMapping("/actualizarDocumentoByIdRodal/{idRodal}")
	    public ResponseEntity<Boolean> actualizaDoc(@Parameter(description = "Documento para subir a Rodal", required = true) @RequestParam("file") MultipartFile file, 
	    		                        	        @Parameter(description = "Identificador de Rodal", required = true) @PathVariable("idRodal") String idRodal,
	    		                                    HttpServletResponse response) throws RodalExceptionDgc, InsertarDocFault, IOException {
			
			try {
				return new ResponseEntity<Boolean>(rodalClient.actualizaDoc(file, idRodal), HttpStatus.OK);
				
		        } catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
	    }

}

package es.jccm.edu.proyectosfct.adapter.in.rest.descarga;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConveniosFct;
import es.jccm.edu.proyectosfct.application.ports.in.convenios.IConveniosFctService;
import es.jccm.edu.proyectosfct.application.ports.in.descarga.IFormFielsFillerService;
import es.jccm.edu.proyectosfct.application.services.descarga.FormFieldsFillerService;

@RestController
@RequestMapping("${spring.data.rest.base-path:/api}" + "/proyectosfct")
public class DescargaDocumentoPruebaRestController {
	
	//@Autowired
	//private ConveniosFctRepository convenioRepository;
	
	@Autowired 
	private IConveniosFctService convenioService;
	
	@Autowired
	private FormFieldsFillerService fillerConvenio;

	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@GetMapping("/descarga")
	public ResponseEntity<FileSystemResource> descargaDocumentoRellenado(HttpServletResponse response) throws IOException {
		File pdfFile = new File("pruebaReal.pdf");

	    // HashMap Test
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    map.put("dniFirmante", "00000000T");
	    
	    // Load the existing pdf file
	    PDDocument pdd = PDDocument.load(pdfFile);
	    PDAcroForm acroForm = pdd.getDocumentCatalog().getAcroForm();
	    
	    // For each item, sets a value to the corresponding field
	    for (String item : map.keySet()){
	        acroForm.getField(item).setValue((String) map.get(item));
	    }
	    
	    // Save
	    pdd.save(pdfFile);
	    pdd.close();
	    
	    HttpHeaders respHeaders = new HttpHeaders();
	    respHeaders.setContentType(MediaType.APPLICATION_PDF);
	    respHeaders.setContentLength(pdfFile.length());
	    respHeaders.setContentDispositionFormData("attachment", "prueba2.pdf");

	    return new ResponseEntity<FileSystemResource>(new FileSystemResource(pdfFile), respHeaders, HttpStatus.OK);
	    
	}

	//@PreAuthorize("hasAnyRole('P','PRO','C')")
	@GetMapping("/descargaReal")
	public ResponseEntity<FileSystemResource> descargaDocumentoRealRellenado(Long idConvenio, HttpServletResponse response) throws IOException {
		File pdfFile = new File("pruebaReal.pdf");
		
		String printFormName = "Prueba";
		
		ConveniosFct convenio = convenioService.getConvenioById(idConvenio);

	    // HashMap Test
	    Map<String, Object> map = obtenerParametros(fillerConvenio, convenio, printFormName);
	    
	    // Load the existing pdf file
	    PDDocument pdd = PDDocument.load(pdfFile);
	    PDAcroForm acroForm = pdd.getDocumentCatalog().getAcroForm();
	    
	    // For each item, sets a value to the corresponding field
	    for (String item : map.keySet()){
	        acroForm.getField(item).setValue((String) map.get(item));
	    }
	    
	    // Save
	    pdd.save(pdfFile);
	    pdd.close();
	    
	    HttpHeaders respHeaders = new HttpHeaders();
	    respHeaders.setContentType(MediaType.APPLICATION_PDF);
	    respHeaders.setContentLength(pdfFile.length());
	    respHeaders.setContentDispositionFormData("attachment", "prueba2.pdf");

	    return new ResponseEntity<FileSystemResource>(new FileSystemResource(pdfFile), respHeaders, HttpStatus.OK);
	    
	}
	
	public <T> Map<String, Object> obtenerParametros(IFormFielsFillerService formFieldsFiller, T objeto, String printFormName) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			map.putAll(formFieldsFiller.execute(objeto, printFormName));
		} catch (Exception e) {
			System.out.println("FALLO");
		}
		
		return map;
	}

}

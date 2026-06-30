package es.jccm.edu.reportsDelphos.application.services;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import es.jccm.edu.reportsDelphos.application.ports.in.IReportsDelphosService;


@Service
public class ReportsDelphosService implements IReportsDelphosService {

	@Autowired
	private ResourceLoader resourceLoader;
	
    @Value("${reports.delphos.urlBase}")
	private String urlBase;
    
    @Value("${reports.delphos.cadenaConexion}")
	private String cadenaConexion;
	
	
	@Override
	public byte[] descargaReportsDelphos(String tParam) throws IOException  {
		
		String urlReport = urlBase + '?' + cadenaConexion + '&' + tParam;
		
		 InputStream dbAsStream = null; 
	     
	     Resource resource = resourceLoader.getResource(urlReport);
         dbAsStream = resource.getInputStream();   
         
         return dbAsStream.readAllBytes();
		
	}

}

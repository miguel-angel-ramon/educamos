package es.jccm.edu.proyectosfct.application.services.descarga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.descarga.PrintFormRepository;
import es.jccm.edu.proyectosfct.application.domain.descarga.PrintForm;
import es.jccm.edu.proyectosfct.application.ports.in.descarga.IPrintFormService;

@Service
public class PrintFormService implements IPrintFormService {
	
	@Autowired
	private PrintFormRepository printFormRepository;
	
	// Read
	
	public PrintForm findPrintFormByAlias(String alias) {
		return printFormRepository.findByPrintFormAlias(alias);
		
	}

}

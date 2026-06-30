package es.jccm.edu.proyectosfct.application.services.descarga;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.descarga.PrintFieldRepository;
import es.jccm.edu.proyectosfct.application.domain.descarga.PrintField;
import es.jccm.edu.proyectosfct.application.ports.in.descarga.IPrintFieldService;

@Service
public class PrintFieldService implements IPrintFieldService {
	
	@Autowired
	private PrintFieldRepository printFieldRepository;

	@Override
	public List<PrintField> findByPrintFormId(Long idPrintForm) {
		return printFieldRepository.findByPrintFormId(idPrintForm);
	}

}

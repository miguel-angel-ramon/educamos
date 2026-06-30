package es.jccm.edu.proyectosfct.application.ports.in.descarga;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.descarga.PrintField;

public interface IPrintFieldService {
	
	List<PrintField> findByPrintFormId(Long idPrintForm);

}

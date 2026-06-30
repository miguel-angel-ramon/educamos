package es.jccm.edu.proyectosfct.application.ports.in.descarga;

import es.jccm.edu.proyectosfct.application.domain.descarga.PrintForm;

public interface IPrintFormService {
	
	PrintForm findPrintFormByAlias(String alias);

}

package es.jccm.edu.proyectosfct.application.ports.in.sedeempresa;

import java.util.List;
import es.jccm.edu.proyectosfct.application.domain.empresas.SedeEmpresa;



public interface ISedeEmpresaService {
	
	List<SedeEmpresa> getAllSedesEmpresaById(Long idEmpresa);

	SedeEmpresa getSedeByIdConvenio(Long idConvenio);

	SedeEmpresa getSedeById(Long idSede);
}

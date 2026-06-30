package es.jccm.edu.proyectosfct.application.ports.in.convenios;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.convenios.entities.DatosEmpresaTrabajador;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.EmpresaTrabajador;

public interface IEmpresaTrabajadorService {
	
	// Read
	
	List<EmpresaTrabajador> getTrabajadoresByEmpresa(Long idEmpresa);
	
	EmpresaTrabajador getTrabajadorById(Long idTrabajador);
	List<EmpresaTrabajador> getRepresentanteEmpresabySede(Long idSede);

	List<EmpresaTrabajador> getResponsablesByEmpresa(Long idEmpresa);

	List<EmpresaTrabajador> getResponsablesBySede(Long idSede);


}

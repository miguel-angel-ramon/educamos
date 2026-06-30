package es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Empleado;

public interface IEmpleadoService {
	
	// Read
	
	Empleado getEmpleadoById(Long idEmpleado);

}

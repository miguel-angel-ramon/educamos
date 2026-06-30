package es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual;

import java.util.Date;
import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.PuestoTrabajoEmpleado;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.DocenteProjection;

public interface IPuestoTrabajoEmpleadoService {
	
	// Read
	
	PuestoTrabajoEmpleado getPuestoTrabajoEmpleadoByIdAndTomaPos(Long idEmpleado, Date tomaPosicion);
	
	List<DocenteProjection> getDocentesByCentro(Long centroId);
	
	List<PuestoTrabajoEmpleado> getEmpleadosByCentro(Long centroId);

}

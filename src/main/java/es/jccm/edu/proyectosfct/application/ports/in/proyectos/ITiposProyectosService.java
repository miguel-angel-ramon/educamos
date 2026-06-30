package es.jccm.edu.proyectosfct.application.ports.in.proyectos;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ListadoProyectos;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.Proyectos;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.TipoProyecto;

public interface ITiposProyectosService {

	List<TipoProyecto> getAllTiposProyectos();
	
	

}

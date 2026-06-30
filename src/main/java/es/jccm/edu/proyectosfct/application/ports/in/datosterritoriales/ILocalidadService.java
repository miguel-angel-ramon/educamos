package es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Localidad;

public interface ILocalidadService {
	
	Localidad findLocalidadById(Long id);

}

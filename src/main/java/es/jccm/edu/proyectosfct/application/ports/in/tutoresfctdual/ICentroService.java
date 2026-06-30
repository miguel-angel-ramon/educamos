package es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;

public interface ICentroService {
	
	// Read
	
	Centro getCentroById(Long idCentro);

}

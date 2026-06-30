package es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual;

import java.util.List;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.OfertaMatriculaCentro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.OfertaMatriculaProjection;

public interface IOfertaMatriculaCentroService {
	
	// Read
	
	OfertaMatriculaCentro getOfertaMatriculaCentroById(Long idOferta);
	
	List<OfertaMatriculaProjection> getOfertasByCentro(Long idCentro);

}

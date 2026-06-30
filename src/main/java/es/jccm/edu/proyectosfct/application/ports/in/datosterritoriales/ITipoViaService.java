package es.jccm.edu.proyectosfct.application.ports.in.datosterritoriales;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.TipoVia;

public interface ITipoViaService {
	
	TipoVia findTipoViaById(Long id);

}

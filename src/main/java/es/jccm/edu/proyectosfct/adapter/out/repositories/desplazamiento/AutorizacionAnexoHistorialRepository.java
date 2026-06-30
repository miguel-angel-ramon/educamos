package es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento;

import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.AutorizacionesAnexosHistorial;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.QAutorizacionesAnexosHistorial;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface AutorizacionAnexoHistorialRepository extends AbstractRepository<AutorizacionesAnexosHistorial, Long, QAutorizacionesAnexosHistorial> {

	AutorizacionesAnexosHistorial findByAnexoId(Long id);

}

package es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento;

import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.EstadoAutorizacion;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.QEstadoAutorizacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EstadoAutorizacionRepository extends AbstractRepository<EstadoAutorizacion, Long, QEstadoAutorizacion> {
 
 
}

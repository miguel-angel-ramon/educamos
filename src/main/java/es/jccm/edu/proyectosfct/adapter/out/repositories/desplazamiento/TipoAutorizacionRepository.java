package es.jccm.edu.proyectosfct.adapter.out.repositories.desplazamiento;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.QTipoAutorizacion;
import es.jccm.edu.proyectosfct.application.domain.desplazamiento.entities.TipoAutorizacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface TipoAutorizacionRepository extends AbstractRepository<TipoAutorizacion, Long, QTipoAutorizacion> {

	TipoAutorizacion findByAbreviatura(String abrev);
 
}

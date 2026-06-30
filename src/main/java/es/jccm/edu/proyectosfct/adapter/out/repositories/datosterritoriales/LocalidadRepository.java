package es.jccm.edu.proyectosfct.adapter.out.repositories.datosterritoriales;

import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.Localidad;
import es.jccm.edu.proyectosfct.application.domain.datosterritoriales.QLocalidad;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface LocalidadRepository extends AbstractRepository<Localidad, Long, QLocalidad> {

}

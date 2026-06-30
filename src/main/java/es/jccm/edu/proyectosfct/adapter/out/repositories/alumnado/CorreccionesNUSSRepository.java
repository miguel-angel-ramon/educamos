package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.CorreccionesAlumnado;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.QCorreccionesAlumnado;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;


@Repository
public interface CorreccionesNUSSRepository extends AbstractRepository<CorreccionesAlumnado, Long, QCorreccionesAlumnado> {

}

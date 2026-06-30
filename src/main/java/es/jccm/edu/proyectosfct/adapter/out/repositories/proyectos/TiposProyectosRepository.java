package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QTipoProyecto;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.TipoProyecto;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.Optional;

@Repository
public interface TiposProyectosRepository extends AbstractRepository<TipoProyecto, Long, QTipoProyecto> {
}

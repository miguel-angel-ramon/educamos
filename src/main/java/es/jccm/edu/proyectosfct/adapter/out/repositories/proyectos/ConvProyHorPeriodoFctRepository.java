package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.List;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConvProyHorPeriodoFct;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QConvProyHorPeriodoFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ConvProyHorPeriodoFctRepository extends AbstractRepository<ConvProyHorPeriodoFct, Long, QConvProyHorPeriodoFct> {

	List<ConvProyHorPeriodoFct> findAllByConvenioProyectoId(Long idConvProy);
}

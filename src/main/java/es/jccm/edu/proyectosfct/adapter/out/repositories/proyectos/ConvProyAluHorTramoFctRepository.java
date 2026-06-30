package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.List;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConvProyAluHorTramoFct;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QConvProyAluHorTramoFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ConvProyAluHorTramoFctRepository extends AbstractRepository<ConvProyAluHorTramoFct, Long, QConvProyAluHorTramoFct> {

	List<ConvProyAluHorTramoFct> findAllByPeriodoAlumnoHorarioId(Long id);

}

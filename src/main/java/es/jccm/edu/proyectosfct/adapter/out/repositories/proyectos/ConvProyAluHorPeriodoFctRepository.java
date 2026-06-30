package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.List;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConvProyAluHorPeriodoFct;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QConvProyAluHorPeriodoFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;


@Repository
public interface ConvProyAluHorPeriodoFctRepository extends AbstractRepository<ConvProyAluHorPeriodoFct, Long, QConvProyAluHorPeriodoFct> {

	List<ConvProyAluHorPeriodoFct> findAllByConvenioProyectoIdAndIdMatricula(Long idConvProy, Long idMatricula);
}

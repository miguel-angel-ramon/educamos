package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnoprograma;

import java.util.List;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.ConvProgAluHorPeriodoFct;
import es.jccm.edu.proyectosfct.application.domain.alumnoprograma.entities.QConvProgAluHorPeriodoFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;


@Repository
public interface ConvProgAluHorPeriodoFctRepository extends AbstractRepository<ConvProgAluHorPeriodoFct, Long, QConvProgAluHorPeriodoFct> {

	List<ConvProgAluHorPeriodoFct> findAllByConvenioProgramaIdAndIdMatricula(Long idConvProg, Long idMatricula);
	
	List<ConvProgAluHorPeriodoFct> findAllByIdMatricula(Long idMatricula);
}

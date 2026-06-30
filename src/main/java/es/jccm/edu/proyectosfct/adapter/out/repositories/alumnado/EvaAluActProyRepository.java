package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado;

import java.util.List;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.EvaAluActProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.QEvaAluActProy;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaAluActProyRepository extends AbstractRepository<EvaAluActProy, Long, QEvaAluActProy> {

	List<EvaAluActProy> findAllByAluConvProyId(Long idConvProyAlu);
	
	List<EvaAluActProy> findAllByAluConvProyIdOrderByDatosProyectoOrden(Long idConvProyAlu);
	

	
}

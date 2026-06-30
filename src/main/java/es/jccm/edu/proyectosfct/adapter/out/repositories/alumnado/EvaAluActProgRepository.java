package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado;

import java.util.List;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.EvaAluActProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.QEvaAluActProg;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaAluActProgRepository extends AbstractRepository<EvaAluActProg, Long, QEvaAluActProg> {

	List<EvaAluActProg> findAllByAluConvProgIdOrderByDatosProgramaOrden(Long idConvProyAlu);
	
	List<EvaAluActProg> findAllByAluConvProgId(Long idConvProyAlu);


	
}

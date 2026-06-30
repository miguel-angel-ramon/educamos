package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado;

import java.util.List;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemActProy;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.QParsemActProy;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ParsemActProyRepository extends AbstractRepository<ParsemActProy, Long, QParsemActProy> {

	List<ParsemActProy> findAllByParsemAluProyId(Long idParsemAluProy);

	void deleteAllByParsemAluProyId(Long idParsemAluProy);
	
}

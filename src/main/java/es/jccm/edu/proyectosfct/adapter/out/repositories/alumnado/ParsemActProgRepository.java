package es.jccm.edu.proyectosfct.adapter.out.repositories.alumnado;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.ParsemActProg;
import es.jccm.edu.proyectosfct.application.domain.alumnado.entities.QParsemActProg;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ParsemActProgRepository extends AbstractRepository<ParsemActProg, Long, QParsemActProg> {

	List<ParsemActProg> findAllByParsemAluProgId(Long idParsemAluProg);

	void deleteAllByParsemAluProgId(Long idParsemAluProg);
	
}

package es.jccm.edu.proyectosfct.adapter.out.repositories.convenios;

import java.util.List;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.ConveniosProgramasAnexos;
import es.jccm.edu.proyectosfct.application.domain.conveniosprogramas.entities.QConveniosProgramasAnexos;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ConveniosProgramasAnexoFctRepository extends AbstractRepository<ConveniosProgramasAnexos, Long, QConveniosProgramasAnexos>{

	List<ConveniosProgramasAnexos> findAllByIdConvProg(Long idConvProg);

	//List<ConveniosProgramasAnexos> findByProyectoId(Long idPrograma);
	
	
}

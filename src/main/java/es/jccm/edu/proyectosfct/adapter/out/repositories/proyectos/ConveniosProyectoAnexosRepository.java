package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.ConveniosProyectoAnexos;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QConveniosProyectoAnexos;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ConveniosProyectoAnexosRepository extends AbstractRepository<ConveniosProyectoAnexos, Long, QConveniosProyectoAnexos> {
	
	List<ConveniosProyectoAnexos> findAllByIdConvProy(Long idConvProy);

	//List<ConveniosProyectoAnexos> findByProyectoId(Long idProyecto);
	
}

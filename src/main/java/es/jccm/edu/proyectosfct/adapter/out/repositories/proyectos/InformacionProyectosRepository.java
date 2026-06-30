package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.InformacionProyectos;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QInformacionProyectos;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface InformacionProyectosRepository extends AbstractRepository<InformacionProyectos, Long, QInformacionProyectos> {
	
//	@Query(value = "SELECT * FROM FCT_INFORMACION_PROYECTOS info WHERE info.ID_PROYECTO = ?1", nativeQuery = true)
//	InformacionProyectos getInfoProyectoId(Long idProyecto);

	Optional<InformacionProyectos> findByProyectoId(Long id);

	Integer countByProyectoId(Long idProyecto);
	

}

package es.jccm.edu.proyectosfct.adapter.out.repositories.proyectos;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.QSecuenciacionProyectos;
import es.jccm.edu.proyectosfct.application.domain.proyectos.entities.SecuenciacionProyectos;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface SecuenciacionProyectosRepository extends AbstractRepository<SecuenciacionProyectos, Long, QSecuenciacionProyectos> {
	
	Optional<SecuenciacionProyectos> findByProyectoId(Long id);
	
	Optional<SecuenciacionProyectos> findByIdSecficRodal(String idRodal);

}

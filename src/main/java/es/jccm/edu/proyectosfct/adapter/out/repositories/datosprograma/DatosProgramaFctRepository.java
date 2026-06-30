package es.jccm.edu.proyectosfct.adapter.out.repositories.datosprograma;

import java.util.List;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.datosprograma.DatosProgramaFct;
import es.jccm.edu.proyectosfct.application.domain.datosprograma.QDatosProgramaFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface DatosProgramaFctRepository extends AbstractRepository<DatosProgramaFct, Long, QDatosProgramaFct> {

	List<DatosProgramaFct> findAllByProgramaIdOrderByOrden(Long idPrograma);

	Integer countByProgramaId(Long idPrograma);

}

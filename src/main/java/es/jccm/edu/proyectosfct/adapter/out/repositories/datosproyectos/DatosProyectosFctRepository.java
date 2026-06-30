package es.jccm.edu.proyectosfct.adapter.out.repositories.datosproyectos;

import java.util.List;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.datosproyecto.DatosProyectosFct;
import es.jccm.edu.proyectosfct.application.domain.datosproyecto.QDatosProyectosFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface DatosProyectosFctRepository extends AbstractRepository<DatosProyectosFct, Long, QDatosProyectosFct> {

	List<DatosProyectosFct> findAllByProyectoIdOrderByOrden(Long idProyecto);

	Integer countByProyectoId(Long idProyecto);

}

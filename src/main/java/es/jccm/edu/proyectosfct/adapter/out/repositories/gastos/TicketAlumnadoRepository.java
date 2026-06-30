package es.jccm.edu.proyectosfct.adapter.out.repositories.gastos;

import java.util.List;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.QTicketAlumnado;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TicketAlumnado;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface TicketAlumnadoRepository extends AbstractRepository<TicketAlumnado, Long, QTicketAlumnado> {

	List<TicketAlumnado> findAllByGastoAlumnadoId(Long idGastoAlumnado);

	
 
}

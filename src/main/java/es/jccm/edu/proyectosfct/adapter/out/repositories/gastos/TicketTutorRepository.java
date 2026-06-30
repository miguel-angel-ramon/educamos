package es.jccm.edu.proyectosfct.adapter.out.repositories.gastos;

import java.util.List;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.QTicketTutor;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TicketTutor;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface TicketTutorRepository extends AbstractRepository<TicketTutor, Long, QTicketTutor> {

	List<TicketTutor> findAllByGastoTutorId(Long idGastoTutor);

	
 
}

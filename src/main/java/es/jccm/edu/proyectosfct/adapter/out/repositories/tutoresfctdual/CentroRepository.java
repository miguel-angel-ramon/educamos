package es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual;

import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Centro;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.QCentro;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface CentroRepository extends AbstractRepository<Centro, Long, QCentro>{

}

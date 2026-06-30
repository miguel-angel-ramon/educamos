package es.jccm.edu.proyectosfct.adapter.out.repositories.gastos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.PeriodoGasto;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.QPeriodoGasto;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface PeriodoGastoRepository extends AbstractRepository<PeriodoGasto, Long, QPeriodoGasto> {

	List<PeriodoGasto> findByAnnoPeriodo(Integer annoPeriodo);
 
}

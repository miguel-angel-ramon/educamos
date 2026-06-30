package es.jccm.edu.proyectosfct.adapter.out.repositories.gastos;

import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.gastos.entities.EstadoGasto;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.QEstadoGasto;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EstadoGastoRepository extends AbstractRepository<EstadoGasto, Long, QEstadoGasto> {
 
 
}

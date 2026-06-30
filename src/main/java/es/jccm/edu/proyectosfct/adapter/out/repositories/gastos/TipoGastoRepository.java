package es.jccm.edu.proyectosfct.adapter.out.repositories.gastos;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.QTipoGasto;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.TipoGasto;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface TipoGastoRepository extends AbstractRepository<TipoGasto, Long, QTipoGasto> {

	TipoGasto findByAbrev(String string);
 
 
}

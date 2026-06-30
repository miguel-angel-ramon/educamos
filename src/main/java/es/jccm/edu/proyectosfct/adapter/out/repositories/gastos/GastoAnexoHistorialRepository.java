package es.jccm.edu.proyectosfct.adapter.out.repositories.gastos;

import java.util.List;

import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAnexoHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.QGastoAnexoHistorial;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface GastoAnexoHistorialRepository extends AbstractRepository<GastoAnexoHistorial, Long, QGastoAnexoHistorial> {

	GastoAnexoHistorial findByGastoAnexoId(Long id); 
	
	List<GastoAnexoHistorial> findByGastoAnexoIdAndIdAneHisRodalNotNull(Long id); 
 
}

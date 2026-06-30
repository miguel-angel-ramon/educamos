package es.jccm.edu.documentosGC.application.ports.out;

import org.springframework.stereotype.Repository;

import es.jccm.edu.documentosGC.application.domain.frompfc.DgcEmpleado;
import es.jccm.edu.documentosGC.application.domain.frompfc.QDgcEmpleado;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface DgcEmpleadoRepository extends AbstractRepository<DgcEmpleado, Long, QDgcEmpleado> {

}

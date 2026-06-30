package es.jccm.edu.proyectosfct.adapter.out.repositories.gastos;

import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAlumnado;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.GastoAlumnadoHistorial;
import es.jccm.edu.proyectosfct.application.domain.gastos.entities.QGastoAlumnadoHistorial;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface GastoAlumnadoHistorialRepository extends AbstractRepository<GastoAlumnadoHistorial, Long, QGastoAlumnadoHistorial> {


    void deleteByGastoAlumno(GastoAlumnado gastoAlumnado);
}

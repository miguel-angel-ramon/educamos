package es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual;

import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Empleado;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.QEmpleado;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EmpleadoRepository extends AbstractRepository<Empleado, Long, QEmpleado> {

}

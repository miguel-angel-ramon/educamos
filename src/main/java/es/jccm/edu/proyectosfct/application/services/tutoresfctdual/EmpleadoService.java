package es.jccm.edu.proyectosfct.application.services.tutoresfctdual;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual.EmpleadoRepository;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.Empleado;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.IEmpleadoService;


@Service
public class EmpleadoService implements IEmpleadoService {
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	// Read

	@Override
	public Empleado getEmpleadoById(Long idEmpleado) {
		Optional<Empleado> res = empleadoRepository.findById(idEmpleado);
		
		return res != null ? res.get() : null;
	}
	
}

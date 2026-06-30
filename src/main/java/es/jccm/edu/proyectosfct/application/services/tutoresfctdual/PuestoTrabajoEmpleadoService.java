package es.jccm.edu.proyectosfct.application.services.tutoresfctdual;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.proyectosfct.adapter.out.repositories.tutoresfctdual.PuestoTrabajoEmpleadoRepository;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.entities.PuestoTrabajoEmpleado;
import es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection.DocenteProjection;
import es.jccm.edu.proyectosfct.application.ports.in.tutoresfctdual.IPuestoTrabajoEmpleadoService;

@Service
public class PuestoTrabajoEmpleadoService implements IPuestoTrabajoEmpleadoService {
	
	@Autowired
	private PuestoTrabajoEmpleadoRepository puestoTrabajoEmpleadoRepository;

	// Read
	
	public PuestoTrabajoEmpleado getPuestoTrabajoEmpleadoByIdAndTomaPos(Long idEmpleado, Date tomaPosicion) {
		return puestoTrabajoEmpleadoRepository.findByIdEmpleadoAndIdFechaTomaPosesion(idEmpleado, tomaPosicion);
	}
	
	public List<DocenteProjection> getDocentesByCentro(Long centroId) {
		return puestoTrabajoEmpleadoRepository.findDocentesBycentro(centroId);
	}

	@Override
	public List<PuestoTrabajoEmpleado> getEmpleadosByCentro(Long centroId) {
		// TODO Auto-generated method stub
		return puestoTrabajoEmpleadoRepository.findAllByCentroId(centroId);
	}

}

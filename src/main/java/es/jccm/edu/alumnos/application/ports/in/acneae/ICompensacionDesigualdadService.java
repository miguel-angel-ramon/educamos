package es.jccm.edu.alumnos.application.ports.in.acneae;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.acneae.CompensacionDesigualdad;

public interface ICompensacionDesigualdadService {
	
	List <CompensacionDesigualdad> getAllCompensaciones();
	
	List<CompensacionDesigualdad> getCompensacionesByMatricula(Long idMatricula);

}

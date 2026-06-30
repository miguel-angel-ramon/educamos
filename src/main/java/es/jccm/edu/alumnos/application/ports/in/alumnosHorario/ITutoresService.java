package es.jccm.edu.alumnos.application.ports.in.alumnosHorario;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.alumnosHorario.TlefDetalle;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.Tutor;

public interface ITutoresService {
	
	List<Tutor> getTutoresByAlumnos(Long idAlumno);
	
	List<TlefDetalle> getTelefonosByTutor(String idTutor);
}

package es.jccm.edu.alumnos.application.ports.in.familiasAlumnado;

import java.util.List;
import java.util.Optional;

import es.jccm.edu.alumnos.application.domain.familiasAlumnado.FAlumno;

public interface IAlumnosTutorService {
	
	List <FAlumno>getAlumnosDeUnTutor(Long idTutor,Optional<Long>idCentro);
	

}

package es.jccm.edu.alumnos.application.ports.in.familiasAlumnado;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.familiasAlumnado.TutorFamilia;

public interface ITutoresCentroService {
	
	List <TutorFamilia>getTutoresAlumnadoCentro(Long idCentro, int annio);
	

}

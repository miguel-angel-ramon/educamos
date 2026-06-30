package es.jccm.edu.alumnos.application.ports.in.familiasAlumnado;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.familiasAlumnado.FAlumno;

public interface IAlumnosFamiliaService {
	
	List<FAlumno>getAlumnosDeUnaFamilia(Long idFamilia);

}

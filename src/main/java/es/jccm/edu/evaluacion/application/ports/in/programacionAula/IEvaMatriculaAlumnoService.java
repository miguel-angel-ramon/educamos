package es.jccm.edu.evaluacion.application.ports.in.programacionAula;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaMatriculaAlumno;

public interface IEvaMatriculaAlumnoService {

	EvaMatriculaAlumno findById(Long idMatricula);

}

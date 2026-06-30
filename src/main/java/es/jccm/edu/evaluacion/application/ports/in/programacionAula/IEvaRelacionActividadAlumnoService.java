package es.jccm.edu.evaluacion.application.ports.in.programacionAula;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadAlumno;

public interface IEvaRelacionActividadAlumnoService {

	void guardar(EvaRelacionActividadAlumno relacionActividadAlumno);

	EvaRelacionActividadAlumno findByActividadIdAndMatriculaId(Long idActividad, Long idMatricula);

}

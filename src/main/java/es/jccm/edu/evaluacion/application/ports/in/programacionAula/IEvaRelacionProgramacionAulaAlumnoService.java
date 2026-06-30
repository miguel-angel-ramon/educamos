package es.jccm.edu.evaluacion.application.ports.in.programacionAula;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionProgramacionAulaAlumno;

public interface IEvaRelacionProgramacionAulaAlumnoService {

	void guardar(EvaRelacionProgramacionAulaAlumno relacionProgramacionAulaAlumno);

	EvaRelacionProgramacionAulaAlumno findById(Long idRelacion);

}

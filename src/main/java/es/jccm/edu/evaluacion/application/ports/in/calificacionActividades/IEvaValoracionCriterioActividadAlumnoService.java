package es.jccm.edu.evaluacion.application.ports.in.calificacionActividades;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaValoracionCriterioActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.evaluacion.CriterioAlumno;

public interface IEvaValoracionCriterioActividadAlumnoService {

	void guardar(EvaValoracionCriterioActividadAlumno valoracionCriterioActividadAlumno);

	EvaValoracionCriterioActividadAlumno findByRelacionActividadCriterioEvaluacionIdAndRelacionActividadAlumnoId(Long relacionActividadCriterioEvaluacionId, Long relacionActividadAlumnoId);

	void eliminar(EvaValoracionCriterioActividadAlumno valoracionCriterioActividadAlumno);

	List<CriterioAlumno> getCriteriosAlumno(Long idMatMatriAlu, Long idConvCentroOmc, Long idUnidadProgramacion);

	List<CriterioAlumno> getCriteriosAlumno(Long idMatMatriAlu, Long idActividadMoodle, Long idConvCentroOmc, Long idUnidadProgramacion);

	List<CriterioAlumno> getCriteriosAlumnoActividad(Long idMatricula, Long idActividad);
}

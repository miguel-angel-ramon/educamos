package es.jccm.edu.evaluacion.application.ports.in.convocatoriasEvaluacion;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.AlumnoConvocatorias;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.AlumnoEvalConv;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.ConvocatoriaAlumnoEvaluacion;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.DatosAlumnoConv;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.MateriaAlumnoEvaluacion;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.PonderacionConv;
import es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.RelacionCalificacion;

public interface IConvocatoriasEvaluacionService {
	
	List<AlumnoConvocatorias> alumnosEvaluacion(String idMatmatrialu);

	DatosAlumnoConv datosAlumnoConvocatoria(Long idMatmatrialu, Long idConvCentroOmc);

	PonderacionConv ponderacionConvocatoria(Long idEmpleado, Long idMatMatriAlu);
	
	List<AlumnoEvalConv> getAlumnosEvaluacion(Long idCurso, Long idUnidad);
	
	List<MateriaAlumnoEvaluacion> getMateriasEvaluacionAlumno(Long idMatricula, Long idEmpleado);
	
	List<ConvocatoriaAlumnoEvaluacion> getConvocatoriasEvaluacionAlumno(Long idMatricula);
	
	void setObservacionesEvaluacionAlumno(Long idMatricula, Long idConvCentroOmc, String observaciones);
	
	List<RelacionCalificacion> getRelacionCalificaciones(Long idSistCal);
	
}

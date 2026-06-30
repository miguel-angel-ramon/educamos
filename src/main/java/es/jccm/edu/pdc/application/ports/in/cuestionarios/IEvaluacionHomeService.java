package es.jccm.edu.pdc.application.ports.in.cuestionarios;

import java.util.Date;
import java.util.List;

import es.jccm.edu.pdc.application.domain.evaluacion.entities.AmbitoAsociado;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionCompleto;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionHome;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.EvaluacionVisionGlobal;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.ObjetivoEspecificoEva;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.CentrosParaInspectoresProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.EvaluacionCompletoProjection;
import es.jccm.edu.pdc.application.domain.evaluacion.projection.EvaluacionVisionGlobalProjection;
import es.jccm.edu.pdc.application.domain.planActuacion.entities.LineaSeguimiento;
import es.jccm.edu.pdc.application.domain.evaluacion.entities.ObjetivoEspecificoEva;

public interface IEvaluacionHomeService {
	
	List<EvaluacionHome> getEvaluacionHomeAll();
	
	List<EvaluacionHome> getPorcentajes();
	List<EvaluacionHome> getEstadoPorcentajes();
	Double getMediaPorcentajes();
	
	List<EvaluacionHome> getFechasActualizacion();
	
	List<AmbitoAsociado> getAmbitoAsociado();
	
	List<ObjetivoEspecificoEva> getObjetivoEspecificoEva();
	
	List<EvaluacionCompletoProjection> getEvaluacionCompleto(Long codCentro, Integer anno);
	
	void setLineasDeSeguimiento(Date fechaInicioEjecucion,Date fechaFinEjecucion,Integer porcentaje,String tareas,String valoracion,String dificultades_acciones,String comentarios,Long idLinAct,Long idUsuComunica);
	
	void editLineasDeSeguimiento(Long idSeguiLinAct, List<LineaSeguimiento> lineasSeguimientoIn, Long idUsuComunica);

	List<EvaluacionVisionGlobalProjection> getEvaluacionVisionGlobal(Long codCentro, Integer anno);
	
	List<CentrosParaInspectoresProjection> getCentrosInspector(Long idEmpleado, Long tipoInforme, Long tipoEmpleado);
}

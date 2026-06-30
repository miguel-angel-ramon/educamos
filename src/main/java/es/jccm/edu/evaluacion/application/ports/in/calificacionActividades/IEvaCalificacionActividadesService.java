package es.jccm.edu.evaluacion.application.ports.in.calificacionActividades;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.AlumnoValoracionActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.ConvocatoriasDto;
import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.UnidadesValoracionDto;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.ProgramacionAulaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CriterioEvaluacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.PonderacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.UnidadProgramacionDTO;
import es.jccm.edu.evaluacion.application.domain.evaluacion.MateriasValoracion;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;

public interface IEvaCalificacionActividadesService {
	
	List<ConvocatoriasDto> getConvocatorias(Long idDidac);
	
	List<UnidadProgramacionDTO> getUnidadesProgramacionProgAula(Long convOmc, Long idProgramacionAula, Long idUnidadProgramacion);
	
	List<ProgramacionAulaDTO> getProgramacionesAulaByDidactica(Long idOfermatrig, Long idMateriaOmg, Long idCentro, Integer anno, List<Long> idsEmpleadosCompartidas, Boolean director);

	PonderacionDTO getPonderacionByProgramacionDidactica(Long idProgramacionDidactica) throws EvaluacionException;
	
	List<AlumnoValoracionActividadDTO> getAlumnosCalificacionesUnidad(Long idProgramacionAula, Long idConvCentroOmc, Long idUnidadProgramacion, Long idMateriaOmg, Long idUnidadCentro) throws EvaluacionException;
	
	List<AlumnoValoracionActividadDTO> getAlumnosCalificacionesActividad(Long idActividad, Long idMateriaOmg, Long idUnidadCentro) throws EvaluacionException;

	List<AlumnoValoracionActividadDTO> getAlumnosActividadReport(Long idActividad, Long idMateriaOmg, Long idUnidadCentro);

	void guardarCalificacionCriteriosActividadAlumno(AlumnoValoracionActividadDTO alumno, Long idCriterio, Long idActividad, Long idPonderacion, Long idProgramacionAula, Long anno) throws EvaluacionException;

	void bloquearPonderacion(Long idPonderacion) throws EvaluacionException;
	
	List<CriterioEvaluacionDTO> getCriteriosByActividad(Long idActividad) throws EvaluacionException;

	List<MateriasValoracion> getMateriasProgAula(Long idEmpleado, Long anno, Long codigoCentro, Long idOfertaMatrig);
	
	List<UnidadesValoracionDto> getUnidadesCentro(Long idProgramacionAula, Long idActividad, Long idUnidadProg);
}
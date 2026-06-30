package es.jccm.edu.evaluacion.application.ports.in.programacionAula;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.AlumnoValoracionActividadDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.AlumnoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.AlumnosPorMateriaDTO;
import es.jccm.edu.evaluacion.application.domain.programacionAula.AlumnosPorMateria;

public interface IEvaAlumnoService {
	
	List<AlumnosPorMateria> findAlumnosByMateria(Long idEmpleado, String fechaTomaPosesion, Long idDidac, Long nivelCurricular);

	List<AlumnosPorMateria> getGestionarAlumnadoProgramacionAula(Long idEmpleado, Long idProgramacionAula, Long nivelCurricular, Long idCentro);

	List<AlumnosPorMateriaDTO> getAlumnosProgramacionAula(Long idProgramacionAula, Long idActividad);
	
	List<AlumnoValoracionActividadDTO> getAlumnosProgramacionAulaConvocatoria(Long idProgramacionAula, Long idConvCentroOmc, Long idUnidadProgramacion, Long idMateriaOmg, Long idUnidadCentro);
	
	List<AlumnoValoracionActividadDTO> getAlumnosActividad(Long idActividad, Long idMateriaOmg, Long idUnidadCentro);
	
	List<AlumnoDTO> getAlumnosAulaVirtual(Long idAulaVirtual);

	String getNumEscolar(Long idAlumno);
	
	List<AlumnosPorMateria> findAllAlumnosACNEESinProgramacionAula(List<Long> idsEmpleado, List<String> fechasTomaPosesion, Long idCentro, Integer anno, Long direccion);

}

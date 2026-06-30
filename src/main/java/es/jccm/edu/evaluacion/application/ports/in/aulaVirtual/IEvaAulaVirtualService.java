package es.jccm.edu.evaluacion.application.ports.in.aulaVirtual;

import java.util.Date;
import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.AulaVirtualListDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.ProgramacionAulaVirtualDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.*;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;


public interface IEvaAulaVirtualService {
	
	AulaVirtualDTO getAulaVirtualById(Long idAula);

	List<AulaVirtualListDTO> getAulasVirtuales(List<Long> idEmpleados, Integer anno) throws EvaluacionException;
	
	List<AlumnosPorMateriaDTO>getAlumnosAula(Long idProgramacionAula, Long idAula);

	Boolean vincularAulaVirtual(Long idProgramacionAula, Long idAula) throws EvaluacionException;

	List<AlumnoDTO> comprobarAlumnado(Long idProgramacionAula, Long idAula);
	
	ProgramacionAulaDTO actualizaFechaComprobarAlumnado(Long idProgramacionAula);

	Boolean desvincularAulaVirtual(Long idProgramacionAula, Boolean mantenerDatos) throws EvaluacionException;

	List<ActividadDTO> getCriteriosActividades(AulaVirtualListDTO aulaVirtual, List<ActividadDTO> actividadesdto, Long idUnidadProgramacion) throws EvaluacionException;

	List<ActividadDTO> getActividadesAula(ProgramacionAulaDTO programacionAula, Long idAula) throws EvaluacionException;

	List<AlumnoDTO> getAlumnosActividad(Long idActividad, AulaVirtualListDTO aulaVirtual, ProgramacionAulaDTO programacionAula) throws EvaluacionException;

	void getCalificacionesActividad(Long idPonderacion, Long idAula, List<AlumnosPorMateriaDTO> alumnos, Long anno, List<ActividadDTO> Actividades, List<ActualizarActividadDTO> actualizarActividades, Long idProgramacionAula) throws Exception;

	List<ProgramacionAulaVirtualDTO> listDetalleProgramacionAula(Long idPlataforma, Long idCurso);

	List<ActualizarActividadDTO> actualizarActividadesMoodle(Long idPonderacion, Long idAula, Long idUnidadProgramacion, Long anno, BodyActualizarActividadDTO body, Long idProgramacionAula) throws Exception;
}

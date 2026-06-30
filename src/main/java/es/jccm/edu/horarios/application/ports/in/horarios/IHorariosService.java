package es.jccm.edu.horarios.application.ports.in.horarios;

import java.util.List;

import es.jccm.edu.horarios.application.domain.horarios.AtributosPlanificacionSemanal;
import es.jccm.edu.horarios.application.domain.horarios.GrupoActividad;
import es.jccm.edu.horarios.application.domain.horarios.HorarioDireccion;
import es.jccm.edu.horarios.application.domain.horarios.HorarioGrupoActividadList;
import es.jccm.edu.horarios.application.domain.horarios.HorarioList;
import es.jccm.edu.horarios.application.domain.horarios.HorarioPersonalList;
import es.jccm.edu.horarios.application.domain.horarios.ReunionOrganoCentro;
import es.jccm.edu.horarios.application.domain.horarios.TotalHorasList;
import es.jccm.edu.horarios.application.domain.horarios.VisitasProgramadas;


public interface IHorariosService {
	
	List<HorarioGrupoActividadList> getHorarios(Long idUsuario, Integer anno, Long codCentro);

	List<HorarioList> getHorariosByDia(String idUsuario, Integer anno, Integer diaSemana);
	
	List<TotalHorasList> getTotalHoras(String idUsuario, Integer anno);
	
	List<AtributosPlanificacionSemanal> getGrupoActividades(Long xEmpleadp, String tomaPos);

	List<HorarioPersonalList> getHorarioPersonal(Long idUsuario, String fechaInicio, String fechaFin);
	
	List<HorarioDireccion> getHorarioDireccion(Long xEmpleado, String tomaPos);
	
	List<ReunionOrganoCentro> getReunionesOrganosCentro(Long codCentro, String anno);
	
	List<GrupoActividad> getGrupoActividadByEmpleadoAnno(Long idUsuario, Integer anno, Long idCentro);
	
	List<GrupoActividad> gruposActividadByEmpleadoAlumnoAnno(Long idUsuario, Long idMatricula, Integer anno);
	
	List<VisitasProgramadas> getVisitasProgramadas(Long idMatricula);
	
}

package es.jccm.edu.alumnos.application.ports.in.alumnosHorario;

import java.util.List;

import org.springframework.data.domain.Page;

import es.jccm.edu.alumnos.application.domain.alumnosHorario.AlumnoAndFaltasList;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.AlumnoDetalle;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.AlumnoHorario;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.Alumnos;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.AsignaturaAlumno;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.HorarioSemanalAlumno;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.ListaAlumnosGrupoActividad;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.TutorAlumnoProjection;

public interface IAlumnoHorarioService {

    Long countAlumnoHorarioByAsignatura(Long idUnidad, Long idMateria);

    List<AlumnoHorario> getAlumnosHorarioByFaltasRecurrentes(Long idUnidad, Long idMateria);

    List<AlumnoHorario> getAlumnosHorarioByRetrasosRecurrentes(Long idUnidad, Long idMateria);

    List<AlumnoAndFaltasList> getAlumnosAndFaltas(Long idTramo, Long idGrupo, String fecha);

    AlumnoHorario getAlumnosHorariosDetalle(Long idMatricula, Long idMatMatricula);
    
    AlumnoDetalle getAlumnoDetalle(Long idEmpleado, Long idMatricula, Integer anno) throws Exception;
    
    void setAlumnoObservacion(Long idMatMatricula, String observacion, Long idMatricula);

    Page<Alumnos> getMisAlumnos(Long idEmpleado, Long anno, Integer page, Integer numItems, Long idGrupoActividad, String nombre, String order, Long idCentro);
    
    List<ListaAlumnosGrupoActividad> getListaAlumnosGrupoActividad(Long idGrupoActividad, Long idEmpleado);

    Page<Alumnos> getMisAlumnosByUnidad(Long idEmpleado, Long anno, Integer page, Integer numItems, Long idUnidad, String nombre, String order);
    
    AlumnoDetalle getAlumnosDetalleYMaterias(Long idEmpleado, Long idMatricula, Integer anno) throws Exception;
    
    List<ListaAlumnosGrupoActividad> getListaAlumnosByUnidad(Long idUnidad, Long idEmpleado);
    
    List<HorarioSemanalAlumno> getHorarioSemanalAlumno(Long idMatricula);

	List<AsignaturaAlumno> getAsignaturaAlumno(Long idMatricula);
	
	TutorAlumnoProjection obtenerTutorYHorario(Long idMatricula);
}

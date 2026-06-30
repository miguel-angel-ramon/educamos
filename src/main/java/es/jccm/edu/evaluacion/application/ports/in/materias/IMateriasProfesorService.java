package es.jccm.edu.evaluacion.application.ports.in.materias;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.alumnoMateriasUnidad.AlumnoMateriasUnidad;
import es.jccm.edu.evaluacion.application.domain.materiasProfesor.MateriasProfesor;


public interface IMateriasProfesorService {
	
	List<MateriasProfesor> getMaterias(Long idEmpleado, Integer anno);

	List<AlumnoMateriasUnidad> getAlumnoMateriasUnidad(Long idMateria, Long idUnidad, Long idGrupoActividad);

}

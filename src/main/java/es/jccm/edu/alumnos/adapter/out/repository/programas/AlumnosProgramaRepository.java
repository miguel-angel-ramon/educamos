package es.jccm.edu.alumnos.adapter.out.repository.programas;

import java.util.List;
import java.util.Optional;

import es.jccm.edu.alumnos.application.domain.programas.AlumnoProgramaDTO;
import es.jccm.edu.alumnos.application.domain.programas.projection.AlumnoProgProjection;

public interface AlumnosProgramaRepository {
	
	List<AlumnoProgramaDTO> findAlumnoProgramaByCurso (Long ofertaMatriculacion, int annio, Optional<Long> unidad, Optional <Long> programa);
	
	Long findIdCentro (Long ofertaMatriculacion);
	
	

}

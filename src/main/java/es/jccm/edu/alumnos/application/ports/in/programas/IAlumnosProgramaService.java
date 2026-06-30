package es.jccm.edu.alumnos.application.ports.in.programas;

import java.util.List;
import java.util.Optional;

import es.jccm.edu.alumnos.application.domain.programas.AlumnoProgramaDTO;
import es.jccm.edu.alumnos.application.domain.programas.MateriaAsociada;
import es.jccm.edu.alumnos.application.domain.programas.projection.AlumnoProgProjection;

public interface IAlumnosProgramaService {
	
	List  <AlumnoProgramaDTO> getAlumnosProgama (Long ofertaMatriculacion, int annio, Optional<Long> unidad,
			Optional<Long> programa);
	
	List<MateriaAsociada> getMateriasAsociadas(Long idPrograma,  Long idOfertaMatric, int annio);
	
	

	
	
	
}

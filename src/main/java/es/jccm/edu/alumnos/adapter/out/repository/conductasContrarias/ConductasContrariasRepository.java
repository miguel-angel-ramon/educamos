package es.jccm.edu.alumnos.adapter.out.repository.conductasContrarias;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.conductasContrarias.dto.AlumnadoDTO;
import es.jccm.edu.alumnos.application.domain.conductasContrarias.dto.CondContrariaDTO;

public interface ConductasContrariasRepository {

	List<AlumnadoDTO> findAlumnadoIncidente(Long unidad, Long ofertamatric, Integer anno);
	List<CondContrariaDTO> findCondContrariaUnidad(Long unidad, Long ofertamatric, Integer anno);
	
}

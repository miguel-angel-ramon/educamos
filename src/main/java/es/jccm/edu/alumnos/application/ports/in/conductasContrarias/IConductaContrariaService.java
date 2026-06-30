package es.jccm.edu.alumnos.application.ports.in.conductasContrarias;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.conductasContrarias.dto.AlumnadoDTO;
import es.jccm.edu.alumnos.application.domain.conductasContrarias.dto.CondContrariaDTO;

public interface IConductaContrariaService {
	
	List <AlumnadoDTO> getAlumnadoIncidente(Long unidad,Long ofertamatric,Integer anno) ;
	
	List <CondContrariaDTO> getCondContUnidad(Long unidad, Long ofertamatric, Integer anno);

}

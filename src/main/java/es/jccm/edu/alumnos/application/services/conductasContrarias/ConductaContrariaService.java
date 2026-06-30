package es.jccm.edu.alumnos.application.services.conductasContrarias;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jccm.edu.alumnos.adapter.out.repository.conductasContrarias.ConductasContrariasRepository;
import es.jccm.edu.alumnos.application.domain.conductasContrarias.dto.AlumnadoDTO;
import es.jccm.edu.alumnos.application.domain.conductasContrarias.dto.CondContrariaDTO;
import es.jccm.edu.alumnos.application.ports.in.conductasContrarias.IConductaContrariaService;


@Service
public class ConductaContrariaService implements IConductaContrariaService{

	@Autowired 
	private ConductasContrariasRepository condContrariasRepository;
	
	 
	@Override
	public List<AlumnadoDTO> getAlumnadoIncidente(Long unidad, Long ofertamatric, Integer anno) {
	
		return condContrariasRepository.findAlumnadoIncidente(unidad, ofertamatric, anno);
	}

	
	@Override
	public List<CondContrariaDTO> getCondContUnidad(Long unidad, Long ofertamatric, Integer anno) {
		
		return condContrariasRepository.findCondContrariaUnidad(unidad, ofertamatric, anno);

	}



}

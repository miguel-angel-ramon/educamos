package es.jccm.edu.alumnos.application.services.familiasAlumnado;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.alumnos.adapter.out.repository.familiasAlumnado.FamiliaAlumnadoRepository;
import es.jccm.edu.alumnos.application.domain.familiasAlumnado.TutorFamilia;
import es.jccm.edu.alumnos.application.domain.familiasAlumnado.projection.TutorFamiliaProjection;
import es.jccm.edu.alumnos.application.ports.in.familiasAlumnado.ITutoresCentroService;

@Service
public class TutoresCentroService implements ITutoresCentroService{
	
	@Autowired 
	private FamiliaAlumnadoRepository familiasRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<TutorFamilia> getTutoresAlumnadoCentro(Long idCentro, int annio) {

		List<TutorFamiliaProjection>tutoresAlumno=familiasRepository.findTutoresFamiliaCentro(idCentro, annio);
		
				
		return tutoresAlumno.stream().map(x->modelMapper.map(x, TutorFamilia.class)).collect(Collectors.toList());
	}

}

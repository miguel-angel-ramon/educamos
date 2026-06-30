package es.jccm.edu.alumnos.application.services.acneae;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.alumnos.adapter.out.repository.acenae.AlumnoNEERepository;
import es.jccm.edu.alumnos.application.domain.acneae.AlumnoNEE;
import es.jccm.edu.alumnos.application.domain.acneae.DatosAlumnoNEE;
import es.jccm.edu.alumnos.application.ports.in.acneae.IAlumnoNEEService;

@Service
public class AlumnoNEEService implements IAlumnoNEEService{
	
	@Autowired
	private AlumnoNEERepository alumnoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	@Override
	public List<AlumnoNEE> getAlumnosNEE(Long idCentro,int annio, Long idOfertaMatriculacion, Long unidad) {
		
		List <AlumnoNEE> alumnos=alumnoRepository.findAlumnosNEE(idCentro, annio, idOfertaMatriculacion, unidad).stream()
				.map(x->modelMapper.map(x, AlumnoNEE.class)).collect(Collectors.toList());
		
		return alumnos;
	}



	@Override
	public DatosAlumnoNEE getDatosAlumnoNEE(Long idMatricula) {

		DatosAlumnoNEE datosAlumno=modelMapper.map(alumnoRepository.findDatoAlumno(idMatricula),DatosAlumnoNEE.class);
		
		return datosAlumno;
	}

}

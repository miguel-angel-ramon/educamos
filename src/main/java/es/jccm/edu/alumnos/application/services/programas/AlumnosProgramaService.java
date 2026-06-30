package es.jccm.edu.alumnos.application.services.programas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.alumnos.adapter.out.repository.programas.AlumnosProgramaRepository;
import es.jccm.edu.alumnos.adapter.out.repository.programas.MateriasProgRepository;
import es.jccm.edu.alumnos.application.domain.programas.AlumnoProgramaDTO;
import es.jccm.edu.alumnos.application.domain.programas.MateriaAsociada;
import es.jccm.edu.alumnos.application.domain.programas.MateriaPrograma;
import es.jccm.edu.alumnos.application.ports.in.programas.IAlumnosProgramaService;

@Service
public class AlumnosProgramaService implements IAlumnosProgramaService{

	@Autowired
	private AlumnosProgramaRepository alumnosPrograma;
	@Autowired
	private MateriasProgRepository materiasPrograma;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<AlumnoProgramaDTO> getAlumnosProgama(Long ofertaMatriculacion, int annio, Optional<Long> unidad,
			Optional<Long> programa) {
		
		List<AlumnoProgramaDTO>alumnos=alumnosPrograma.findAlumnoProgramaByCurso(ofertaMatriculacion, annio, unidad, programa);

		if (programa.isPresent()){
			 for (AlumnoProgramaDTO alumno : alumnos) {
				 alumno.setMaterias(
						  materiasPrograma.findMateriasPrograma(programa.get() , alumno.getIdMatricula() , ofertaMatriculacion,annio).stream()
						  .map(x -> modelMapper.map(x, MateriaPrograma.class)).collect(Collectors.toList()));
				}
			
		}
		return alumnos;
	}

	@Override
	public List<MateriaAsociada> getMateriasAsociadas(Long idPrograma,  Long idOfertaMatric, int annio) {
		
		Long idCentro=alumnosPrograma.findIdCentro(idOfertaMatric);
		List <MateriaAsociada> materias=materiasPrograma.findMateriasAsociadas(idPrograma, idCentro, idOfertaMatric, annio).stream()
				.map(x ->modelMapper.map(x, MateriaAsociada.class)).collect(Collectors.toList());
		return materias;
	}

	
}

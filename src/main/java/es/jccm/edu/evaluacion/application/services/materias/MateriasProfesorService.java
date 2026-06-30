package es.jccm.edu.evaluacion.application.services.materias;

import java.util.List;
import java.util.stream.Collectors;

import es.jccm.edu.evaluacion.application.domain.alumnoMateriasUnidad.AlumnoMateriasUnidad;
import es.jccm.edu.evaluacion.application.domain.alumnoMateriasUnidad.projection.AlumnoMateriasUnidadProjection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.out.repositories.materias.MateriasProfesorRepository;
import es.jccm.edu.evaluacion.application.domain.materiasProfesor.MateriasProfesor;
import es.jccm.edu.evaluacion.application.domain.materiasProfesor.projection.MateriasProfesorProjection;
import es.jccm.edu.evaluacion.application.ports.in.materias.IMateriasProfesorService;


@Service
public class MateriasProfesorService implements IMateriasProfesorService {
	
	private static final Logger LOG = LogManager.getLogger(MateriasProfesorService.class);
	
	@Autowired
	private MateriasProfesorRepository materiaRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<MateriasProfesor> getMaterias(Long idEmpleado, Integer anno) {
		LOG.info("Obteniendo materias del usuario ", idEmpleado);
		
		List<MateriasProfesorProjection> materias = materiaRepository.findMateriasProfesor(idEmpleado, anno);
		
		return materias.stream().map(materia -> modelMapper.map(materia, MateriasProfesor.class)).collect(Collectors.toList());
	}

	@Override
	public List<AlumnoMateriasUnidad> getAlumnoMateriasUnidad(Long idMateria, Long idUnidad, Long idGrupoActividad) {
		LOG.info("Obteniendo materias del grupo ", idGrupoActividad);

		List<AlumnoMateriasUnidadProjection> materias = materiaRepository.findAlunosMaterias(idMateria, idUnidad, idGrupoActividad);

		return materias.stream().map(materia -> modelMapper.map(materia, AlumnoMateriasUnidad.class)).collect(Collectors.toList());
	}

}

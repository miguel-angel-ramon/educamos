package es.jccm.edu.alumnos.application.services.alumnosHorario;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.alumnos.adapter.out.repository.alumnosHorario.TutoresRepository;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.TlefDetalle;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.Tutor;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.TlefDetalleProjection;
import es.jccm.edu.alumnos.application.domain.alumnosHorario.projection.TutorProjection;
import es.jccm.edu.alumnos.application.ports.in.alumnosHorario.ITutoresService;

@Service
public class TutoresService implements ITutoresService {

	@Autowired
	private TutoresRepository tutoresRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public List<Tutor> getTutoresByAlumnos(Long idAlumno) {
		List<TutorProjection> tutoresProjection = tutoresRepository.tutoresByAlumno(idAlumno);

		List<Tutor> tutores = tutoresProjection.stream().map(x -> modelMapper.map(x, Tutor.class))
				.collect(Collectors.toList());

		for (Tutor tutorAux : tutores) {
			List<TlefDetalleProjection> telefonos = tutoresRepository.telefonosByTutor(tutorAux.getNumide());

			tutorAux.setListTelf(
					telefonos.stream().map(x -> modelMapper.map(x, TlefDetalle.class)).collect(Collectors.toList()));
		}

		return tutores;
	}

	public List<TlefDetalle> getTelefonosByTutor(String numide) {
		List<TlefDetalleProjection> telefonos = tutoresRepository.telefonosByTutor(numide);

		return telefonos.stream().map(x -> modelMapper.map(x, TlefDetalle.class)).collect(Collectors.toList());
	}

}

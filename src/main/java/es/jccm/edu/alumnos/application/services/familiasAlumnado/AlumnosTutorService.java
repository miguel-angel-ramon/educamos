package es.jccm.edu.alumnos.application.services.familiasAlumnado;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.alumnos.adapter.out.repository.familiasAlumnado.FamiliaAlumnadoRepository;
import es.jccm.edu.alumnos.application.domain.familiasAlumnado.FAlumno;
import es.jccm.edu.alumnos.application.ports.in.familiasAlumnado.IAlumnosTutorService;

@Service
public class AlumnosTutorService implements IAlumnosTutorService{
	
	@Autowired
	private FamiliaAlumnadoRepository familiaAlumnado;
	
	

	@Override
	public List<FAlumno> getAlumnosDeUnTutor(Long idTutor,Optional<Long>idCentro) {
		
		List<FAlumno>alumnos=new ArrayList<FAlumno>();
		
		if (idCentro.isPresent()) {
			alumnos=familiaAlumnado.findDistinctByFamiliaIdTutor1OrFamiliaIdTutor2AndMatriculasIdCentro(idTutor, idTutor, idCentro.get());
		}else {
		alumnos= familiaAlumnado.findByFamiliaIdTutor1OrFamiliaIdTutor2(idTutor, idTutor);
		}
		
		return alumnos;
	}

}

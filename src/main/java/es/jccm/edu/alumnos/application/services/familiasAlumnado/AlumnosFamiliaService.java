package es.jccm.edu.alumnos.application.services.familiasAlumnado;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.alumnos.adapter.out.repository.familiasAlumnado.FamiliaAlumnadoRepository;
import es.jccm.edu.alumnos.application.domain.familiasAlumnado.FAlumno;
import es.jccm.edu.alumnos.application.ports.in.familiasAlumnado.IAlumnosFamiliaService;


 
@Service
public class AlumnosFamiliaService implements IAlumnosFamiliaService{

	@Autowired 
	private FamiliaAlumnadoRepository familiasRepository;
	
	@Override
	public List<FAlumno> getAlumnosDeUnaFamilia(Long idFamilia) {

		List<FAlumno> alumnos=familiasRepository.findByFamiliaId( idFamilia);
		return alumnos;
	}
	
	

}

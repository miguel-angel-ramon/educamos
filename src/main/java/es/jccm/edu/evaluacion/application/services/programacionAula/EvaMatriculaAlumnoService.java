package es.jccm.edu.evaluacion.application.services.programacionAula;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaMatriculaAlumnoRepository;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaMatriculaAlumno;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaMatriculaAlumnoService;

@Service
public class EvaMatriculaAlumnoService implements IEvaMatriculaAlumnoService {

    @Autowired
    private EvaMatriculaAlumnoRepository matriculaAlumnoRepository;

    @Autowired
    private ModelMapper modelMapper;

	@Override
	public EvaMatriculaAlumno findById(Long idMatricula) {
		return matriculaAlumnoRepository.findById(idMatricula).orElse(null);
	}

}
package es.jccm.edu.evaluacion.application.services.programacionAula;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionActividadAlumnoRepository;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionActividadAlumno;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaRelacionActividadAlumnoService;

@Service
public class EvaRelacionActividadAlumnoService implements IEvaRelacionActividadAlumnoService {

    @Autowired
    private EvaRelacionActividadAlumnoRepository relacionActividadAlumnoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
	@Override
	public void guardar(EvaRelacionActividadAlumno relacionActividadAlumno) {
		relacionActividadAlumnoRepository.save(relacionActividadAlumno);
	}

	@Override
	public EvaRelacionActividadAlumno findByActividadIdAndMatriculaId(Long idActividad, Long idMatricula) {
		return relacionActividadAlumnoRepository.findByActividadIdAndMatriculaId(idActividad, idMatricula);
	}
}
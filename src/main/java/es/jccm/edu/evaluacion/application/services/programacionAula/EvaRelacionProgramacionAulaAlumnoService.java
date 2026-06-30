package es.jccm.edu.evaluacion.application.services.programacionAula;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula.EvaRelacionProgramacionAulaAlumnoRepository;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaRelacionProgramacionAulaAlumno;
import es.jccm.edu.evaluacion.application.ports.in.programacionAula.IEvaRelacionProgramacionAulaAlumnoService;

@Service
public class EvaRelacionProgramacionAulaAlumnoService implements IEvaRelacionProgramacionAulaAlumnoService {

    @Autowired
    private EvaRelacionProgramacionAulaAlumnoRepository relacionProgramacionAulaAlumnoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
	@Override
	public void guardar(EvaRelacionProgramacionAulaAlumno relacionProgramacionAulaAlumno) {
    	relacionProgramacionAulaAlumnoRepository.save(relacionProgramacionAulaAlumno);
	}

	@Override
	public EvaRelacionProgramacionAulaAlumno findById(Long idRelacion) {
		return relacionProgramacionAulaAlumnoRepository.findById(idRelacion).orElse(null);
	}
}
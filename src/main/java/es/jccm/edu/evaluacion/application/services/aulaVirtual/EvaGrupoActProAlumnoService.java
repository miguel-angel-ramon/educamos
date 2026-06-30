package es.jccm.edu.evaluacion.application.services.aulaVirtual;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model.GrupoActProAlumnoDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.aulaVirtual.EvaGrupoActProAlumnoRepository;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.EvaGrupoActProAlumno;
import es.jccm.edu.evaluacion.application.ports.in.aulaVirtual.IEvaGrupoActProAlumnoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvaGrupoActProAlumnoService implements IEvaGrupoActProAlumnoService {
	
	@Autowired
	private EvaGrupoActProAlumnoRepository grupoActProAlumnoRepository;
	
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public GrupoActProAlumnoDTO getGrupoActProAlumnoById(Long idGrupoActProAlumno) {
		Optional<EvaGrupoActProAlumno> grupoActProAlumno = grupoActProAlumnoRepository.findById(idGrupoActProAlumno);
		if(grupoActProAlumno.isPresent()) {
			return modelMapper.map(grupoActProAlumno.get(), GrupoActProAlumnoDTO.class);
		} else {
			return null;
		}
	}
}

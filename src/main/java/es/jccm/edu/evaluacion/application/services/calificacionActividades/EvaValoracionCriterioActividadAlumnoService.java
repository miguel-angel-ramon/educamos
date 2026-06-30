package es.jccm.edu.evaluacion.application.services.calificacionActividades;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades.EvaValoracionCriterioActividadAlumnoRepository;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaValoracionCriterioActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.evaluacion.CriterioAlumno;
import es.jccm.edu.evaluacion.application.ports.in.calificacionActividades.IEvaValoracionCriterioActividadAlumnoService;

@Service
public class EvaValoracionCriterioActividadAlumnoService implements IEvaValoracionCriterioActividadAlumnoService {
	
	@Autowired
	private EvaValoracionCriterioActividadAlumnoRepository valoracionCriterioActividadAlumnoRepository;
	
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public void guardar(EvaValoracionCriterioActividadAlumno valoracionCriterioActividadAlumno) {
		valoracionCriterioActividadAlumnoRepository.save(valoracionCriterioActividadAlumno);
	}

	@Override
	public EvaValoracionCriterioActividadAlumno findByRelacionActividadCriterioEvaluacionIdAndRelacionActividadAlumnoId(Long relacionActividadCriterioEvaluacionId, Long relacionActividadAlumnoId) {
		return valoracionCriterioActividadAlumnoRepository.findByRelacionActividadCriterioEvaluacionIdAndRelacionActividadAlumnoId(relacionActividadCriterioEvaluacionId, relacionActividadAlumnoId);
	}

	@Override
	public void eliminar(EvaValoracionCriterioActividadAlumno valoracionCriterioActividadAlumno) {
		valoracionCriterioActividadAlumnoRepository.delete(valoracionCriterioActividadAlumno);		
	}
	
	@Override
	public List<CriterioAlumno> getCriteriosAlumno(Long idMatMatriAlu, Long idConvCentroOmc, Long idUnidadProgramacion) {
		return valoracionCriterioActividadAlumnoRepository.getCriteriosAlumno(idMatMatriAlu, idConvCentroOmc, idUnidadProgramacion).
				stream().map(criterio -> modelMapper.map(criterio, CriterioAlumno.class)).collect(Collectors.toList());
	}

	@Override
	public List<CriterioAlumno> getCriteriosAlumno(Long idMatMatriAlu, Long idActividadMoodle, Long idConvCentroOmc, Long idUnidadProgramacion) {
		return valoracionCriterioActividadAlumnoRepository.getCriteriosAlumno(idMatMatriAlu, idActividadMoodle, idConvCentroOmc, idUnidadProgramacion).
				stream().map(criterio -> modelMapper.map(criterio, CriterioAlumno.class)).collect(Collectors.toList());
	}

	public List<CriterioAlumno> getCriteriosAlumnoActividad(Long idMatricula, Long idActividad) {
		return valoracionCriterioActividadAlumnoRepository.getCriteriosAlumnoActividad(idMatricula, idActividad)
				.stream().map(criterio -> modelMapper.map(criterio, CriterioAlumno.class)).collect(Collectors.toList());
	}

	public List<CriterioAlumno> getCriteriosActividad( Long idActividad) {
		return valoracionCriterioActividadAlumnoRepository.getCriteriosActividad(idActividad)
				.stream().map(criterio -> modelMapper.map(criterio, CriterioAlumno.class)).collect(Collectors.toList());
	}

}

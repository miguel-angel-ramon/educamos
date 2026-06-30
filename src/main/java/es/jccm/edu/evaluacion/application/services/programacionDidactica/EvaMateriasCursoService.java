package es.jccm.edu.evaluacion.application.services.programacionDidactica;

import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaMateriasCentroRepository;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.EvaMateriasCentro;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaMateriasCentroProjection;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaMateriasCentroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EvaMateriasCursoService implements IEvaMateriasCentroService {
	
	@Autowired
	private EvaMateriasCentroRepository evaMateriasCentroRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<EvaMateriasCentro> getMateriasCentroByAnyo(Long codigoCentro, Integer anyo) {
		List<EvaMateriasCentroProjection> materias = evaMateriasCentroRepository.findMateriasCentroByAnyo(codigoCentro, anyo);
		return materias.stream().map(materia -> modelMapper.map(materia, EvaMateriasCentro.class)).collect(Collectors.toList());
	}

	@Override
	public List<EvaMateriasCentro> getMateriasCentroByAnyoAndCurso(Long codigoCentro, Integer anyo, Long idCurso) {
		
		List<EvaMateriasCentroProjection> materias = evaMateriasCentroRepository.findMateriasCentroByAnyoAndCurso(codigoCentro, anyo, idCurso);
		return materias.stream().map(materia -> modelMapper.map(materia, EvaMateriasCentro.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<EvaMateriasCentro> getMateriasCentroACNEAEByAnyoAndCurso(Long codigoCentro, Integer anyo, Long idOfertaMatrig) {
		
		List<EvaMateriasCentroProjection> materias = evaMateriasCentroRepository.findMateriasCentroACNEAEByAnyoAndCurso(codigoCentro, anyo, idOfertaMatrig);
		return materias.stream().map(materia -> modelMapper.map(materia, EvaMateriasCentro.class)).collect(Collectors.toList());
	}
	
	public EvaMateriasCentro getMateriaACNEAE(Long idMateriaOmg, Long idNivelCurricular) {
		EvaMateriasCentroProjection materiaProjection = evaMateriasCentroRepository.findMateriaACNEAE(idMateriaOmg, idNivelCurricular);
		if (materiaProjection != null) {
			return modelMapper.map(materiaProjection, EvaMateriasCentro.class);
		} else {
			return null;
		}
	}

	@Override
	public List<EvaMateriasCentro> getMateriasCentroByNivelCurricular(Long idNivelCurricular) {
		List<EvaMateriasCentroProjection> materias = evaMateriasCentroRepository.findMateriasCentroByNivelCurricular(idNivelCurricular);
		return materias.stream().map(materia -> modelMapper.map(materia, EvaMateriasCentro.class)).collect(Collectors.toList());
	}
}

package es.jccm.edu.evaluacion.application.services.calificacionActividades;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model.MateriaCursoGenericaDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades.EvaMateriaCursoOfertaMatriculaGenericaRepository;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaMateriaCursoOfertaMatriculaGenerica;
import es.jccm.edu.evaluacion.application.ports.in.calificacionActividades.IEvaMateriaCursoOfertaMatriculaGenericaService;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvaMateriaCursoOfertaMatriculaGenericaService implements IEvaMateriaCursoOfertaMatriculaGenericaService {
	
	@Autowired
	private EvaMateriaCursoOfertaMatriculaGenericaRepository materiaCursoOfertaMatriculaGenericaRepository;
	
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public MateriaCursoGenericaDTO getMateria(Long idMateriaOmg) throws EvaluacionException {
		Optional<EvaMateriaCursoOfertaMatriculaGenerica> materiaCursoOfertaMatriculaGenerica = materiaCursoOfertaMatriculaGenericaRepository.findById(idMateriaOmg);
		if(materiaCursoOfertaMatriculaGenerica.isPresent()) {
			return modelMapper.map(materiaCursoOfertaMatriculaGenerica.get().getMateriaCurso(), MateriaCursoGenericaDTO.class);
		} else {
			log.error("No se ha encontrado materia con id: " + idMateriaOmg);
			throw new EvaluacionException("No se ha encontrado materia con id: " + idMateriaOmg);
		}
	}
}

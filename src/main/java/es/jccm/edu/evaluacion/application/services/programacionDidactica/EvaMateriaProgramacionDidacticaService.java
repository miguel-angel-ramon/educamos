package es.jccm.edu.evaluacion.application.services.programacionDidactica;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.NivelCurricularProgramacionAulaDTO;
import es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica.EvaMateriaProgramacionDidacticaRepository;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.NivelCurricularProgramacionAulaProjection;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.EvaMateriaProgramacionDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaMateriaProgramacionDidacticaProjection;
import es.jccm.edu.evaluacion.application.ports.in.programacionDidactica.IEvaMateriaProgramacionDidacticaService;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvaMateriaProgramacionDidacticaService implements IEvaMateriaProgramacionDidacticaService {

	@Autowired
	private EvaMateriaProgramacionDidacticaRepository materiaProgramacionDidacticaRepository;

	@Autowired
	private EvaProgramacionDidacticaService programacionDidacticaService;


	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Page<EvaMateriaProgramacionDidactica> getMateriasProgramacionDidactica(Long codigoCentro, Integer anyo,
			Long idCurso, Long idMateria, int page, int numItems, Long editor, String estado , Boolean perfilEditor) throws EvaluacionException {

		try {
			Pageable paging = PageRequest.of(page, numItems);
			
			Page<EvaMateriaProgramacionDidacticaProjection> materiasProjection = materiaProgramacionDidacticaRepository.findAllByCentroAnyoCursoAndMateria(codigoCentro, anyo, idCurso, idMateria, editor, estado , paging);
			if(perfilEditor){
				Page<EvaMateriaProgramacionDidactica> materias = materiasProjection.map(materia -> modelMapper.map(materia, EvaMateriaProgramacionDidactica.class));
				materias.getContent().forEach( materia -> {
						materia.setListaEditores(programacionDidacticaService.getEditores(materia.getIdOfertaMatrig(),
								materia.getIdMateriaOmg(),
								codigoCentro,
								anyo,
								materia.getIdNivelCurricular() != null ? materia.getIdNivelCurricular(): -1L,-1L));
				});
				return materias;

			}else {
				return materiasProjection.map(materia -> modelMapper.map(materia, EvaMateriaProgramacionDidactica.class));
			}

		} catch (Exception e) {
			log.error("error al obtener las materias del centro con programación didáctica", e);
			throw new EvaluacionException("error al obtener las materias del centro con programación didáctica", e);
		}
		
	}
	
	@Override
	public List<NivelCurricularProgramacionAulaDTO> getNivelCurricular(Integer anno, Long idMateriaOmg, Long idCentro, Long idOfermatrig) throws EvaluacionException {
		try {
	    	List<NivelCurricularProgramacionAulaProjection> nivelCurricular = materiaProgramacionDidacticaRepository.getNivelCurricular(anno, idMateriaOmg, idCentro, idOfermatrig);
	    	return nivelCurricular.stream().map(x -> modelMapper.map(x, NivelCurricularProgramacionAulaDTO.class)).collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Error al obtener el nivel curricular", e);
			throw new EvaluacionException("Error al obtener el nivel curricular", e);
		}
	}

	@Override
	public List<NivelCurricularProgramacionAulaDTO> getNivelCurricularValoracionCriterios(Integer anno, Long idMateriaOmg, Long idCentro, Long idOfermatrig, Long idEmpleado, Optional<Long> direccion) throws EvaluacionException {
		try {
			Long direc = direccion.isPresent()? direccion.get(): (long) 0 ;
			List<NivelCurricularProgramacionAulaProjection> nivelCurricular = materiaProgramacionDidacticaRepository.getNivelCurricularValoracionCriterios(anno, idMateriaOmg, idCentro, idOfermatrig, idEmpleado, direc );
			return nivelCurricular.stream().map(x -> modelMapper.map(x, NivelCurricularProgramacionAulaDTO.class)).collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Error al obtener el nivel curricular", e);
			throw new EvaluacionException("Error al obtener el nivel curricular", e);
		}
	}
}

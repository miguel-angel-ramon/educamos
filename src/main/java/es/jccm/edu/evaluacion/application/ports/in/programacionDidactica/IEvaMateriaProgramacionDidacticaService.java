package es.jccm.edu.evaluacion.application.ports.in.programacionDidactica;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model.NivelCurricularProgramacionAulaDTO;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.EvaMateriaProgramacionDidactica;
import es.jccm.edu.evaluacion.application.ports.out.exceptions.EvaluacionException;

public interface IEvaMateriaProgramacionDidacticaService {

	Page<EvaMateriaProgramacionDidactica> getMateriasProgramacionDidactica(Long codigoCentro, Integer anyo, Long idCurso, Long idMateria, int page, int numItems, Long editor, String estado ,Boolean perfilEditor) throws EvaluacionException;

	List<NivelCurricularProgramacionAulaDTO> getNivelCurricular(Integer anno, Long idMateriaOmg, Long idCentro, Long idOfermatrig) throws EvaluacionException;

	List<NivelCurricularProgramacionAulaDTO> getNivelCurricularValoracionCriterios(Integer anno, Long idMateriaOmg, Long idCentro, Long idOfermatrig, Long idEmpleado, Optional<Long> direccion) throws EvaluacionException;
	
}

package es.jccm.edu.evaluacion.application.ports.in.programacionDidactica;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.materias.model.ConvocatoriasDto;
import es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model.CriterioListDto;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.SaberBasicoDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.CompetenciaEspecificaDidacticaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.RelacionBloqueSaberBasicoMateriaDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.UnidadProgramacionDTO;
import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.UnidadesProgramacionCriterioDTO;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaConvocatoriaCentrosOMC;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionBloqueSaberBasicoMateria;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaUnidadProgramacion;


public interface IEvaUnidadProgramacionService {
	
	List<UnidadProgramacionDTO> getUnidadesProgramacion(Long idProgramacionDidactica);

	EvaUnidadProgramacion insertUnidadProgramacion(Long idProgramacionDidactica, Long idConvCentroOmc, UnidadProgramacionDTO unidadProgramacion);
	
	void updateUnidadProgramacion(Long idConvCentroOmc, UnidadProgramacionDTO unidadProgramacion);
	
	boolean deleteUnidadProgramacion(Long idUnidadProgramacion);
	
	List<UnidadesProgramacionCriterioDTO> getNumUnidadesProgramacionCriterios(List<CriterioListDto> criterios, Long idProgramacionDidactica);
	
	List<CompetenciaEspecificaDidacticaDTO> getCompetenciasEspecificas(Long idMateriaOmg);

	List<CompetenciaEspecificaDidacticaDTO>  getCriteriosEvaluacionAcnee(Long idPonderacion);

	List<ConvocatoriasDto> getConvocatorias(Long anno, Long codigoCentro, Long idMateria);

	List<CompetenciaEspecificaDidacticaDTO> getCompetenciasEspecificasUnidad(Long idUnidadProgramacion, Long idActividad) throws Exception;

	List<RelacionBloqueSaberBasicoMateriaDTO> getBloquesSaberes(Long idMateria);

	List<SaberBasicoDTO> getSaberesBasicosByUnidadProgramacion(Long idUnidadProgramacion);

	EvaConvocatoriaCentrosOMC obtenerConvocatoriaCentroOMC(EvaConvocatoriaCentrosOMC convCentroOMC, Integer anyoOriginal, Integer anyoNuevo);
	
}

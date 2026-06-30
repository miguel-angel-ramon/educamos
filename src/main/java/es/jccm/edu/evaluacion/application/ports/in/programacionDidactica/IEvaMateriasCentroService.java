package es.jccm.edu.evaluacion.application.ports.in.programacionDidactica;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.EvaMateriasCentro;


public interface IEvaMateriasCentroService {
	
	List<EvaMateriasCentro> getMateriasCentroByAnyo(Long codigoCentro, Integer anyo);

	List<EvaMateriasCentro> getMateriasCentroByAnyoAndCurso(Long codigoCentro, Integer anyo, Long idCurso);
	
	List<EvaMateriasCentro> getMateriasCentroACNEAEByAnyoAndCurso(Long codigoCentro, Integer anyo, Long idOfertaMatrig);
	
	EvaMateriasCentro getMateriaACNEAE(Long idMateriaOmg, Long idNivelCurricular);
	
	List<EvaMateriasCentro> getMateriasCentroByNivelCurricular(Long idNivelCurricular);
}

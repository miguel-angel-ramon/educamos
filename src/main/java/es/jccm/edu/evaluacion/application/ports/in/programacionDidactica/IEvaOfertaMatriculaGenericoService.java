package es.jccm.edu.evaluacion.application.ports.in.programacionDidactica;

import java.util.List;

import es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model.OfertaMatriculaGenericoDTO;

public interface IEvaOfertaMatriculaGenericoService {
	
	List<OfertaMatriculaGenericoDTO> getCursosCentro(Long codigoCentro, Integer anyo);

	OfertaMatriculaGenericoDTO getCursoByOfertaMatrig(Long idOfermatrig) throws Exception;
	
	List<OfertaMatriculaGenericoDTO> getCursosACNEAECentro(Long codigoCentro, Integer anyo);
	
	List<OfertaMatriculaGenericoDTO> getNivelesCurricularesACNEAEByCentroAnyoCursoAndMateria(Long codigoCentro, Integer anyo, Long idOfertaMatrig, Long idMateriaOmg);

}

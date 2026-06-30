package es.jccm.edu.documentosGC.application.ports.in.actasevaluacionnoformales;

import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actaevaluacionesnoformales.entities.DirectorTutor;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosnoformales.RegSelDocNoFormales;

public interface IActasEvaluacionNoFormalesService {
	
	List<RegSelDocNoFormales> createRegistrosRegSolDoc(Long idUnidad, 		
			   String localidad, 
			   Long idConvOmc, 
			   Long idDirector,
			   Long idTutor);
	
	DirectorTutor getDirectorTutor(Long idConvOmc, Long idUnidad);

	Long getMateriasCurso(Long idMateriac, Long idOfertamatrig);

}

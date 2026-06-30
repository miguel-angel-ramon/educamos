package es.jccm.edu.documentosGC.application.ports.in.actaevaluacioncf;

import java.text.ParseException;
import java.util.List;

import es.jccm.edu.documentosGC.application.domain.actasevaluacioncf.entities.DirectoresActaPrivadoCF;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioncf.entities.ProfesorActaEvaluacionCF;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoscf.RegSelDocCF;

public interface IActasEvaluacionCFService {
	
	List<ProfesorActaEvaluacionCF> getProfesoresCandidatosActaEvaluacion(Long idCentro, 
																	     Integer cAnno, 
																	     String fSesion,
																	     Long idOfertamatrig, 
																	     Long idUnidad, 
																	     Long idConvUnidad, 
																	     Long idDocumento) throws ParseException;

	List<ProfesorActaEvaluacionCF> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, 
																			Integer cAnno, 
																			String fSesion,
																			Long idOfertamatrig, 
																			Long idUnidad, 
																			Long idConvUnidad, 
																			Long idDocumento) throws ParseException;
	
	List<RegSelDocCF> createRegistrosRegSolDoc(List<RegSelDocCF> regSelDocListIn, 
											   Long idUnidad);
	
	List<DirectoresActaPrivadoCF> getListadoDirectoresPrivado(Long idConvCentro);	
	
}

package es.jccm.edu.documentosGC.application.ports.in.actasevaluacionespa;

import java.text.ParseException;
import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionespa.entities.DirectoresActaPrivadoESPA;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionespa.entities.ProfesorActaEvaluacionESPA;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosespa.RegSelDocESPA;

public interface IActasEvaluacionESPAService {
	
	List<ProfesorActaEvaluacionESPA> getProfesoresCandidatosActaEvaluacion(Long idCentro, 
																		   Integer cAnno, 
																		   String fSesion,
																		   Long idOfertamatrig, 
																		   Long idUnidad, 
																		   Long idConvUnidad, 
																		   Long idDocumento,
																		   String fFirma) throws ParseException;

	List<ProfesorActaEvaluacionESPA> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, 
																			  Integer cAnno, 
																			  String fSesion,
																			  Long idOfertamatrig, 
																			  Long idUnidad, 
																			  Long idConvUnidad, 
																			  Long idDocumento,
																			  String fFirma) throws ParseException;
	
	List<RegSelDocESPA> createRegistrosRegSolDoc(List<RegSelDocESPA> regSelDocListIn, Long idUnidad);
	
	List<DirectoresActaPrivadoESPA> getListadoDirectoresPrivado(Long idConvCentro);	
	
}

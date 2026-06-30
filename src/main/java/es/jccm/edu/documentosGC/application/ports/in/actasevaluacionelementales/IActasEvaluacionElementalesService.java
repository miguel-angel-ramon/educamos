package es.jccm.edu.documentosGC.application.ports.in.actasevaluacionelementales;

import java.text.ParseException;
import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionelementales.entities.DirectoresActaPrivadoElementales;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionelementales.entities.ProfesorActaEvaluacionElementales;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoselementales.RegSelDocElementales;


public interface IActasEvaluacionElementalesService {
	
	List<ProfesorActaEvaluacionElementales> getProfesoresCandidatosActaEvaluacion(Long idCentro, 
																				  Integer cAnno, 
																				  String fSesion,
																				  Long idCursoEtapa, 
																				  Long idUnidad, 
																				  String f_fecfinconomc, 
																				  String f_fecfincon) throws ParseException;

	List<ProfesorActaEvaluacionElementales> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, 
																					 Integer cAnno, 
																					 String fSesion,
																					 Long idCursoEtapa, 
																					 Long idUnidad, 
																					 String f_fecfinconomc, 
																					 String f_fecfincon) throws ParseException;
	
	List<RegSelDocElementales> createRegistrosRegSolDoc(List<RegSelDocElementales> regSelDocListIn);
	
	List<DirectoresActaPrivadoElementales> getListadoDirectoresPrivado(Long idConvCentro);	
	
}

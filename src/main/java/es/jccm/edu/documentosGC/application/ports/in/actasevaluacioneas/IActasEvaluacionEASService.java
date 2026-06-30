package es.jccm.edu.documentosGC.application.ports.in.actasevaluacioneas;

import java.text.ParseException;
import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneas.entities.DirectoresActaPrivadoEAS;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneas.entities.ProfesorActaEvaluacionEAS;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoseas.RegSelDocEAS;

public interface IActasEvaluacionEASService {
	
	List<ProfesorActaEvaluacionEAS> getProfesoresCandidatosActaEvaluacion(Long idCentro, 
																		  Integer cAnno, 
																		  String fSesion,
																		  Long idOfertamatrig, 
																		  String f_fecfinconomc,
																		  String f_fecfincon,
																		  Long idMateriac) throws ParseException;

	List<ProfesorActaEvaluacionEAS> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, 
																			 Integer cAnno, 
																			 String fSesion,
																			 Long idOfertamatrig, 
																			 String f_fecfinconomc, 
																			 String f_fecfincon,
																			 Long idMateriac) throws ParseException;
	
	List<RegSelDocEAS> createRegistrosRegSolDoc(List<RegSelDocEAS> regSelDocListIn,Long idCurso);
	
	List<DirectoresActaPrivadoEAS> getListadoDirectoresPrivado(Long idConvCentro);

	Integer getEtapaModalidad(Long idCurso);	
	
}

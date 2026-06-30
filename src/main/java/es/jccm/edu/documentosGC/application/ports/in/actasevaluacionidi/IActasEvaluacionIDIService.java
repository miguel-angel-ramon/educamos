package es.jccm.edu.documentosGC.application.ports.in.actasevaluacionidi;

import java.text.ParseException;
import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionidi.entities.DirectoresActaPrivadoIDI;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionidi.entities.ProfesorActaEvaluacionIDI;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosidi.RegSelDocIDI;

public interface IActasEvaluacionIDIService {
	
	List<ProfesorActaEvaluacionIDI> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException;

	List<ProfesorActaEvaluacionIDI> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException;
	
	List<RegSelDocIDI> createRegistrosRegSolDoc(List<RegSelDocIDI> regSelDocListIn);
	
	List<DirectoresActaPrivadoIDI> getListadoDirectoresPrivado(Long idConvCentro);	
	
}

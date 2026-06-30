package es.jccm.edu.documentosGC.application.ports.in.actasevaluaciondeportiva;

import java.text.ParseException;
import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actasevaluaciondeportiva.entities.DirectoresActaPrivadoDeportiva;
import es.jccm.edu.documentosGC.application.domain.actasevaluaciondeportiva.entities.ProfesorActaEvaluacionDeportiva;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentodeportiva.RegSelDocDeportiva;

public interface IActasEvaluacionDeportivaService {
	
	List<ProfesorActaEvaluacionDeportiva> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;

	List<ProfesorActaEvaluacionDeportiva> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;
	
	List<RegSelDocDeportiva> createRegistrosRegSolDoc(List<RegSelDocDeportiva> regSelDocListIn);

	List<DirectoresActaPrivadoDeportiva> getListadoDirectoresPrivado(Long idConvCentro);

}

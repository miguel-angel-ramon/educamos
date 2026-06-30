package es.jccm.edu.documentosGC.application.ports.in.actasevaluacioneso;

import java.text.ParseException;
import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneso.entities.ProfesorActaEvaluacionESO;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneso.entities.DirectoresActaPrivadoESO;
import es.jccm.edu.documentosGC.application.domain.resgistrosdocumentoseso.RegSelDocESO;

public interface IActasEvaluacionESOService {
	
	List<ProfesorActaEvaluacionESO> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException;

	List<ProfesorActaEvaluacionESO> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException;
	
	List<RegSelDocESO> createRegistrosRegSolDoc(List<RegSelDocESO> regSelDocListIn, Long idUnidad);
	
	List<DirectoresActaPrivadoESO> getListadoDirectoresPrivado(Long idConvCentro);	
	
}

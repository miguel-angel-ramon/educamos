package es.jccm.edu.documentosGC.application.ports.in.actasevaluacionprimaria;

import java.text.ParseException;
import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionprimaria.entities.DirectoresActaPrivadoPrimaria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionprimaria.entities.ProfesorActaEvaluacionPrimaria;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosprimaria.RegSelDocPrimaria;

public interface IActasEvaluacionPrimariaService {
	
	List<ProfesorActaEvaluacionPrimaria> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;

	List<ProfesorActaEvaluacionPrimaria> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;
	
	List<RegSelDocPrimaria> createRegistrosRegSolDoc(List<RegSelDocPrimaria> regSelDocListIn);

	List<DirectoresActaPrivadoPrimaria> getListadoDirectoresPrivado(Long idConvCentro);

}

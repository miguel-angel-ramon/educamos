package es.jccm.edu.documentosGC.application.ports.in.actasevaluacioneini;

import java.text.ParseException;
import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneini.entities.DirectoresActaPrivadoEINI;
import es.jccm.edu.documentosGC.application.domain.actasevaluacioneini.entities.ProfesorActaEvaluacionEINI;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoseini.RegSelDocEINI;

public interface IActasEvaluacionEINIService {
	
	List<ProfesorActaEvaluacionEINI> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;

	List<ProfesorActaEvaluacionEINI> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;
	
	List<RegSelDocEINI> createRegistrosRegSolDoc(List<RegSelDocEINI> regSelDocListIn);
	
	List<DirectoresActaPrivadoEINI> getListadoDirectoresPrivado(Long idConvCentro);

	List<ProfesorActaEvaluacionEINI> getCandidatosActaEINIPendientes(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;	
	
	List<ProfesorActaEvaluacionEINI> getSeleccionadosActaEINIPendientes(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;

	List<ProfesorActaEvaluacionEINI> getProfesoresSeleccionadosActaEvaluacionEINII(Long idCentro, Long idUnidad,
			Integer dia, String mes, Integer cAnno) throws ParseException;

	Long getIdConvocatoriaOmcByConvocatoriaCentro(Long idConvocatoria, Long idCentro, Long idOfertamatrig);	

}

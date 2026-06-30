package es.jccm.edu.documentosGC.application.ports.in.actasevaluacionbachillerato;

import java.text.ParseException;
import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.entities.ConvocatoriasExtraordinaria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.entities.DirectoresActaPrivadoBachillerato;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionbachillerato.entities.ProfesorActaEvaluacionBachillerato;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosbachillerato.RegSelDocBachillerato;

public interface IActasEvaluacionBachilleratoService {
	
	List<ProfesorActaEvaluacionBachillerato> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;

	List<ProfesorActaEvaluacionBachillerato> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;
	
	List<RegSelDocBachillerato> createRegistrosRegSolDoc(List<RegSelDocBachillerato> regSelDocListIn);
	
	List<DirectoresActaPrivadoBachillerato> getListadoDirectoresPrivado(Long idConvCentro,
																        String fSesion,
																        String f_fecfinconomc, 
																        String f_fecfincon);

	List<ConvocatoriasExtraordinaria> getListadoConvocatoriasExtraordinaria(Long idCentro, Long cAnno);	

	List<ProfesorActaEvaluacionBachillerato> getCandidatosActaBachilleratoPendientes(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;	
	
	List<ProfesorActaEvaluacionBachillerato> getSeleccionadosActaBachilleratoPendientes(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;	

}

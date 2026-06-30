package es.jccm.edu.documentosGC.application.ports.in.actaevaluacionarte;

import java.text.ParseException;
import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionarte.entities.DirectoresActaPrivadoArte;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionarte.entities.ProfesorActaEvaluacionArte;
import es.jccm.edu.documentosGC.application.domain.registrodocumentosarte.RegSelDocArte;


public interface IActasEvaluacionArteService {
	
	List<ProfesorActaEvaluacionArte> getCandidatosCombosObraFinal(Long idCentro,
																	String fSesion) throws ParseException;

	List<ProfesorActaEvaluacionArte> getProfesoresCandidatosActaEvaluacion(Long idCentro, 
																		   Integer cAnno, 
																		   String fSesion,
																		   Long idOfertamatrig, 
																		   Long idUnidad,
																		   String f_fecfinconomc, 
																		   String f_fecfincon) throws ParseException;

	List<ProfesorActaEvaluacionArte> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, 
																			  Integer cAnno, 
																			  String fSesion,
																			  Long idOfertamatrig, 
																			  Long idUnidad,
																			  String f_fecfinconomc, 
																			  String f_fecfincon) throws ParseException;
	List<ProfesorActaEvaluacionArte> getVocalesCandidatosActaEvaluacion(Long idCentro,
																	   Integer cAnno,
																	   String fSesion)throws ParseException;
	
	List<RegSelDocArte> createRegistrosRegSolDoc(List<RegSelDocArte> regSelDocListIn);
	
	List<DirectoresActaPrivadoArte> getListadoDirectoresPrivado(Long idConvCentro);

	List<ProfesorActaEvaluacionArte> getCandidatosActaArtePendientes(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;	
	
	List<ProfesorActaEvaluacionArte> getSeleccionadosActaArtePendientes(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, String f_fecfinconomc, String f_fecfincon) throws ParseException;	

}

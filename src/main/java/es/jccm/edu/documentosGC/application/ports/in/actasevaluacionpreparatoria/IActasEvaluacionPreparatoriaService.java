package es.jccm.edu.documentosGC.application.ports.in.actasevaluacionpreparatoria;

import java.text.ParseException;
import java.util.List;

import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.entities.DirectoresActaPrivadoPreparatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.entities.ProfesorActaEvaluacionPreparatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacionpreparatoria.entities.ProfesorFirmante;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentospreparatoria.RegSelDocPreparatoria;

public interface IActasEvaluacionPreparatoriaService {
	
	List<ProfesorActaEvaluacionPreparatoria> getProfesoresCandidatosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException;

	List<ProfesorActaEvaluacionPreparatoria> getProfesoresSeleccionadosActaEvaluacion(Long idCentro, Integer cAnno, String fSesion,
			Long idOfertamatrig, Long idUnidad, Long idConvUnidad) throws ParseException;
	
	List<RegSelDocPreparatoria> createRegistrosRegSolDoc(List<RegSelDocPreparatoria> regSelDocListIn, Long idUnidad);
	
	List<DirectoresActaPrivadoPreparatoria> getListadoDirectoresPrivado(Long idConvCentro);

	Integer getOfertaMatriculaCurso(Long idOfertamatrig, Long idCentro);

	List<ProfesorFirmante> getFirmantesProfesoradoPreparatorios(Long cAnno, 	
																Long xCentro, 
																String idUnidad, 
																Long idCurso,
																String sesion);	
	
}

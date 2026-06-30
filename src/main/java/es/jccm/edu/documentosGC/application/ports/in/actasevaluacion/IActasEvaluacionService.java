package es.jccm.edu.documentosGC.application.ports.in.actasevaluacion;

import java.util.List;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.ActaEvaluacionList;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.ConvocatoriaCentro;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.ConvocatoriasCorrespondencia;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.CursoConvocatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.DatosCentroActa;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.DocumentoActa;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.FechasConvocatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.FirmantesPartes;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.OtrosFirmantes;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.TlDocumento;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.UnidadConvocatoria;
import es.jccm.edu.documentosGC.application.domain.actasevaluacion.entities.VistaPartesActas;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ConvocatoriaUnidadList;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoDocumentoGC;


public interface IActasEvaluacionService {
	
	List<ConvocatoriaCentro> getConvocatoriasCentro(Long idCentro, 
													Long cAnno, 
													Long idTipo);

	List<CursoConvocatoria> getCursosConvocatoria(Long idCentro, 
												  Long cAnno, 
												  Long idTipo, 
												  Long idConvCentro);

	List<UnidadConvocatoria> getUnidadesConvocatoria(Long idCentro, 
												     Long cAnno, 
												     Long idTipo, 
												     Long idConvCentro,
												     Long idConvCentroOmc);
	
	List<EstadoDocumentoGC> getEstadosActasEvaluacion(Long idPerfil,
													  Long cAnno, 
													  String dsAbrevPadre);

	List<ActaEvaluacionList> getAllActasEvaluacion(Long idCentro, 
												   Long cAnno, 
												   Long idTipo, 
												   Long idConvCentro,
												   Long idOfertamatric, 
												   Long idUnidad, 
												   Long idEstado,
												   Long idDocumento,
												   Long idMateriac,
												   Long idEtapa,
												   Long xEmpleado);
	
	List<ActaEvaluacionList> getAllActasEvaluacionInspCentro(Long idCentro, 
															 Long cAnno, 
															 Long idTipo, 
															 Long idConvCentro,
															 Long idOfertamatric, 
															 Long idUnidad, 
															 Long idEstado,
															 Long idProvincia, 
														     Long idMunicipio,
															 Long xEmpleado,
															 String fTomapos,
															 Long idDocumento);
	
	List<ActaEvaluacionList> getAllActasEvaluacionZona(Long idCentro, 
													   Long cAnno, 
													   Long idTipo,
													   Long idConvCentro, 
													   Long idOfertamatric, 
													   Long idUnidad, 
													   Long idEstado,
										               Long idPerfil,
										               Long idProvincia,
										               Long idMunicipio,
													   Long idUsuario,
													   Long idDocumento);
	
	List<ActaEvaluacionList> getAllActasEvaluacionProvincial(Long idCentro, 
															 Long cAnno, 
															 Long idTipo,
															 Long idConvCentro, 
															 Long idOfertamatric, 
															 Long idUnidad, 
															 Long idEstado,
															 Long idProvincia,
															 Long idMunicipio,
															 Long idPerfil,
															 Long idUsuario,
															 Long idCentroProvincia,
															 Long idDocumento);
	
	List<ActaEvaluacionList> getAllActasEvaluacionConsejeria(Long idCentro, 
															 Long cAnno, 
															 Long idTipo,
															 Long idConvCentro, 
															 Long idOfertamatric, 
															 Long idUnidad, 
															 Long idEstado,
															 Long idProvincia,
															 Long idMunicipio,
															 Long idDocumento);


	List<ConvocatoriaUnidadList> getConvocatoriasEvaluacion(Long idCentro, 
															Long cAnno, 
															Long idTipo);

	DatosCentroActa getLocalidadCentro(Long idCentro, Long cAnno);

	String getNombreReport(Long idDocumento);

	FechasConvocatoria getFechasConvocatoria(Long idConvUnidad, Long idConvocatoria, Long idEtapa);
	
	List<TlDocumento> getDocumentosByIdCentroAndCAnnoAndIdConvunidad(Long idCentro, Integer cAnno, Long idConvunidad);

	Integer getActaEvaluacionGenerada(Integer cAnno, 	
									  Long idCentro, 
									  Long idTipoDocumento, 
									  Long idConvUnidad,
									  Long idDocumento,
									  Long idCurso,
									  Long idMateria,
									  Long idConvCentro);

	List<DocumentoActa> getDocumentosCurso(Long idCentro, Long cAnno, Long idOfertamatric);
	
	FechasConvocatoria getFechasConvocatoriaCurso(Long idConvCentro,
												  Long idOfertamatrig,	
												  Long idCentro);
	
	List<ConvocatoriasCorrespondencia> getConvocatoriasCorrespondencia(Long idCentro, Long cAnno, Long idOfertamatrig, Long idConvunidad);

	List<DocumentoActa> getMateriasCurso(Long idOfertamatric, String tipo);

	Integer getNumOfertasDocumento(Long idCentro, Long cAnno, Long idDocumento);

	List<DocumentoActa> getEtapasActas(Long idCentro, Long cAnno, Long idDocumento, Long idCurso);

	List<OtrosFirmantes> getOtrosFirmantes(Long idCentro, Long idUnidad, Integer dia, String mes, Integer anno,
			Long idDirector, Integer annoAca);

	List<OtrosFirmantes> getOtrosFirmantesNoFormales(Long idCentro, Long idUnidad, Long idDirector, Integer annoAca);

	VistaPartesActas getCentroVistaPartesActas(Long idCentro);

	List<FirmantesPartes> getFirmantesPartes(Long idCentro);

}

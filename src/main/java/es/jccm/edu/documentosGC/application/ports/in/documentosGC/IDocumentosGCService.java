package es.jccm.edu.documentosGC.application.ports.in.documentosGC;

import java.sql.SQLException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.ListNombreAdjuntosDto;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.AdjuntosListDetalle;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ContadoresInicio;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ConvocatoriaReunionList;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.CursoProjection;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DetalleAdjuntos;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentoGCNuevo;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGCList;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.HistDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.NuevoParte;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.ParteGeneradoDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.PartesMensualesDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.PlazosEntregaDocumentosGC;
import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.TipoAdjuntosList;
import es.jccm.edu.documentosGC.application.domain.documentosGC.projection.InformacionEstadoPojection;
import es.jccm.edu.documentosGC.application.ports.in.rodal.RodalExceptionDgc;

public interface IDocumentosGCService {
	
	List<ContadoresInicio> getContadoresInicio(Long idCentro, 
		       Integer anno, 
			   Long idPerfil,
			   Long xEmpleado,
			   String fTomapos);	
		
	List<DocumentosGCList> getAllDocumentosGC(Long idCentro, 
											  Integer anno, 
											  Long idTipo, 
											  Long IdEstado, 
											  Long idProvincia, 
											  Long idMunicipio,
											  String abrev, 
											  Long idPerfil,
											  Long xEmpleado);
	
	List<DocumentosGCList> getAllDocumentosGCZona(Long idCentro, 
												  Integer anno, 
												  Long idTipo, 
												  Long idEstado, 
												  Long idProvincia, 
												  Long idMunicipio,
												  Long idPerfil, 
												  Long idUsuario,
												  String abrev,
												  Long xEmpleado);
	
	List<DocumentosGCList> getAllDocumentosGCInspectorCentro(Long idCentro, 
														     Integer anno, 
														     Long idTipo, 
														     Long idEstado, 
															 Long idProvincia, 
															 Long idMunicipio, 
															 Long xEmpleado, 
															 String fTomapos,
															 String abrev);

	List<DocumentosGCList> getAllDocumentosGCProvincial(Long idCentro, 
														Integer anno, 
														Long idTipo, 
													    Long idEstado, 
													    Long idProvincia, 
													    Long idMunicipio, 
													    Long idPerfil, 
													    Long idUsuario,
													    String abrev, 
													    Long idCentroProvincia,
													    Long xEmpleado);
	
	DocumentosGC createDocumentoGC(DocumentoGCNuevo documento, List<MultipartFile> file, List<Long> listAdjuntos ) throws RodalExceptionDgc, Exception;
	
	DocumentosGC getDocumentoId(Long idDocumento);
	
	List<InformacionEstadoPojection> getInformacionEstadoDocumento(Long idDocumento, Long idHistorial);
	
	List<HistDocumentosGC> getHistDocumentosGC(Long idDocumento);
	
	int deleteDocumento(Long idDocumento) throws RodalExceptionDgc, Exception;

	List<DocumentosGCList> getAllDocumentosAR(Long idCentro, 
											  Integer anno, 
											  Long idTipo, 
											  Long idEstado,
											  Long xEmpleado);

	List<ConvocatoriaReunionList> getConvocatorias(Long idCentro, Long anno, Long idTipo);
	
	Integer getNumeroDocumentosSinCrear(Long idCentro, Long anno, String pdsAbrev);
	
	List<PlazosEntregaDocumentosGC> getDocumentosNoCreados(Long idCentro, Long anno, String pdsAbrev);

	List<PlazosEntregaDocumentosGC> getMensajesPlazosEntregaFinalizado(Long idCentro, Long anno, String pdsAbrev);

	List<PartesMensualesDocumentosGC> getPartesMensuales(Long idCentro, Long anno);
	
	List<ParteGeneradoDocumentosGC> getParteGenerados(Long cAnno, Long xCentro);

	List<CursoProjection> getMesCurso(Long cAnno);
	
	Integer getNumeroPartesCreados(Long idCentro, Long anno, Long mes);

	Integer createParte(NuevoParte parte) throws SQLException;

	List<TipoAdjuntosList> getInformacionTiposAdjuntosDoc(Long anno, Long idTipDoc, Long idPerfil, Long idFlujo);

	List<AdjuntosListDetalle> getInformacionAdjuntosDetalle(Long idDocumento, Long idPerfil);

	Integer updateAdjuntosDocumentoGC(DetalleAdjuntos adjuntos, List<MultipartFile> files, Long idEmpleado) throws RodalExceptionDgc, Exception;

	String getAdjuntoFirmable(Long idAjunto, Long idEmpleado);

	List<Integer> getNombreDocumentoEnUso(ListNombreAdjuntosDto lista);

}

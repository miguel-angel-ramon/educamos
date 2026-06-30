package es.jccm.edu.documentosGC.application.ports.in.estadodoc;

import java.util.List;

import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoFlujoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.ListadoEstadoTipoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.estadodoc.projection.LineaEstadoProjection;

public interface IEstadosDocumentosGCService {
	
	List<EstadoDocumentoGC> estadosDocumentosGC(Long idPerfil, Long cAnno, String dsAbrevPadre);
	
	List<EstadoFlujoDocumentoGC> estadoInicialDocumentosGC(Long idPerfil, Long idTipodoc, Long cAnno);
	
	EstadoDocumentoGC findById(Long idEstado);
	
	EstadoDocumentoGC estadoActualDocumentosGC(Long idDocumento);
	
	Long getEstadoId(String abrevEstado);

	List<LineaEstadoProjection> lineaEstadosDocumentosGC(Long idDocumento, Long idTipo);

	List<EstadoFlujoDocumentoGC> estadosPosiblesDocumentosGC(Long idPerfil, Long idTipodoc, Long idDocumento);

	List<ListadoEstadoTipoDocumentoGC> listadoEstadosDocumentosGC(Long cAnno);

	void deleteEstadoDocumentoGC(Long idEstado);	
	
	EstadoDocumentoGC createEstadoDocumento(EstadoDocumentoGC conveniosFct);

}

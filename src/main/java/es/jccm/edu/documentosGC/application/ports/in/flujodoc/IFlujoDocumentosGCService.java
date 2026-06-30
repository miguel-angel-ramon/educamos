package es.jccm.edu.documentosGC.application.ports.in.flujodoc;

import java.util.List;

import es.jccm.edu.documentosGC.application.domain.estadodoc.entities.EstadoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.flujodoc.entities.FlujoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.perfiles.entities.PerfilGC;

public interface IFlujoDocumentosGCService {
	
	FlujoDocumentoGC findById(Long id);
	
	FlujoDocumentoGC flujoSiguienteDocumentosGC(Long idEstadoOri, Long idEstadoDes, Long idPerfil, Long idTipoDoc);

	List<FlujoDocumentoGC> getEstadosFlujosByIdTipoDocumento(Long idTipoDocumento);

	List<EstadoDocumentoGC> getEstadosOrigen();

	List<EstadoDocumentoGC> getEstadosDestino(Long idEstadoOrigen);

	List<PerfilGC> getPerfilesEstadosFlujo();

	List<FlujoDocumentoGC> createEstadoFlujoDocumento(List<FlujoDocumentoGC> flujoDocumentoGCListIn, Long idTipoDocumento);

	void deleteEstadoFlujoDocumento(List<FlujoDocumentoGC> flujoDocumentoGCListIn);

	Integer countDocumentosSinAdjuntosByIdFlujo(Long idFlujo);

	Integer countDocumentosAdjuntosByIdFlujo(Long idFlujo);

}

package es.jccm.edu.documentosGC.application.ports.in.tipdocadjunto;

import java.util.List;
import es.jccm.edu.documentosGC.application.domain.tipodocadjunto.entities.TipoDocumentoAdjuntoGC;

public interface ITipoDocumentoAdjuntoGCService {

	TipoDocumentoAdjuntoGC createTipoDocumentoAdjunto(TipoDocumentoAdjuntoGC tipoDocumentoAdjunto) throws Exception;

	TipoDocumentoAdjuntoGC getTipoDocumentoAdjuntosGCById(Long idTipoDocumentoAdjunto);

	List<TipoDocumentoAdjuntoGC> getTipoDocumentoAdjuntosByIdTipoDocumento(Long idTipoDocumento, Integer cAnno);

	void deleteTipoDocumentoAdjunto(Long idTipoDocumentoAdjunto);

	Integer getSiguienteOrdenTipoDocumentoAdjuntosGC(Long idTipoDocumento);

}

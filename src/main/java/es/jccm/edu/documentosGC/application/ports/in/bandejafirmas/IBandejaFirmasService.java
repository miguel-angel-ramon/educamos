package es.jccm.edu.documentosGC.application.ports.in.bandejafirmas;

import java.util.List;

import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.BandejaFirmasList;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.EstadosBandeja;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.TipoAdjuntoBandeja;
import es.jccm.edu.documentosGC.application.domain.bandejafirmas.entities.TipoDocumentoBandeja;

public interface IBandejaFirmasService {

	List<TipoDocumentoBandeja> getTiposDocumentoBandeja(Long cAnno);

	List<TipoAdjuntoBandeja> getTiposAdjuntosBandeja(Long cAnno, Long idTipoDocumento);

	List<EstadosBandeja> getEstadosDocumentoBandeja(Long cAnno, Long idTipoDocumento);

	List<BandejaFirmasList> getAllDocumentosBandeja(Long cAnno, 
													Long idTipoDocumento, 
													Long idTipoAdjunto, 
													Long idEstado, 
													String miFirma,  
													Long idEmpleado,
													String fechaGeneracion,
													String fechaFirma,
													String perfil);

}

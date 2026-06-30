package es.jccm.edu.documentosGC.application.ports.in.historicodoc;

import java.util.List;

import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGC;
import es.jccm.edu.documentosGC.application.domain.historicodoc.entities.HistoricoDocumentoGC;

public interface IHistoricoDocumentoGCService {
	
	public void save (HistoricoDocumentoGC historico) ;
	
	public void delete (HistoricoDocumentoGC historico) ;
	
	List<HistoricoDocumentoGC> getHistoricoDocumentoId(DocumentosGC documento);
	
	HistoricoDocumentoGC findById(Long id);

}

package es.jccm.edu.documentosGC.application.ports.in.centrodoc;

import es.jccm.edu.documentosGC.application.domain.centrodoc.entities.CentroDocumentosGC;

public interface ICentroDocGCService {
	
	CentroDocumentosGC findById( Long idCentro);

}

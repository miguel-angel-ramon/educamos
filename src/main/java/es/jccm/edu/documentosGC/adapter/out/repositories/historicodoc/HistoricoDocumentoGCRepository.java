package es.jccm.edu.documentosGC.adapter.out.repositories.historicodoc;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.List;

import es.jccm.edu.documentosGC.application.domain.documentosGC.entities.DocumentosGC;
import es.jccm.edu.documentosGC.application.domain.historicodoc.entities.HistoricoDocumentoGC;
import es.jccm.edu.documentosGC.application.domain.historicodoc.entities.QHistoricoDocumentoGC;

public interface HistoricoDocumentoGCRepository extends AbstractRepository<HistoricoDocumentoGC, Long, QHistoricoDocumentoGC> {

	List<HistoricoDocumentoGC> findAllByDocumento(DocumentosGC documento);

}

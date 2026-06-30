package es.jccm.edu.documentosGC.adapter.out.repositories.adjuntosdocumento;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import es.jccm.edu.documentosGC.application.domain.adjuntosdocumento.entities.AdjuntosDocumento;
import es.jccm.edu.documentosGC.application.domain.adjuntosdocumento.entities.QAdjuntosDocumento;
import es.jccm.edu.documentosGC.application.domain.tipodocadjunto.entities.TipoDocumentoAdjuntoGC;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface AdjuntosDocumentoRepository extends AbstractRepository<AdjuntosDocumento, Long, QAdjuntosDocumento> {

	@Transactional
	void deleteAdjuntosDocumentoByHistorialId(Long idHistorial);

	List<AdjuntosDocumento> findAdjuntosDocumentoByHistorialId(Long idHistorial);

}

package es.jccm.edu.documentosGC.adapter.out.repositories.plazosentrega;

import java.util.Optional;

import es.jccm.edu.documentosGC.application.domain.plazoentrega.entities.PlazosEntrega;
import es.jccm.edu.documentosGC.application.domain.plazoentrega.entities.QPlazosEntrega;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface PlazosEntregaRepository extends AbstractRepository<PlazosEntrega, Long, QPlazosEntrega> {

	Optional<PlazosEntrega> findBycAnnoAndTipoId(Integer cAnno, Long idTipoDocumento);

}



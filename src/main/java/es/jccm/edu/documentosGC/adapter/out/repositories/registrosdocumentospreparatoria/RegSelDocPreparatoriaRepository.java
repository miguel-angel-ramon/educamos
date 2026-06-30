package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentospreparatoria;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.documentosGC.application.domain.registrosdocumentospreparatoria.QRegSelDocPreparatoria;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentospreparatoria.RegSelDocPreparatoria;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocPreparatoriaRepository extends AbstractRepository<RegSelDocPreparatoria, Long, QRegSelDocPreparatoria>  {

	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 
	
}

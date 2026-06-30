package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentoscf;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.documentosGC.application.domain.registrosdocumentoscf.QRegSelDocCF;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoscf.RegSelDocCF;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocCFRepository extends AbstractRepository<RegSelDocCF, Long, QRegSelDocCF>  {

	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 
	
}

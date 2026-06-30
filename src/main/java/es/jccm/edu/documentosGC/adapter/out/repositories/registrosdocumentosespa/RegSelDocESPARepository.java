package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentosespa;

import org.springframework.data.jpa.repository.Query;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosespa.QRegSelDocESPA;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosespa.RegSelDocESPA;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocESPARepository extends AbstractRepository<RegSelDocESPA, Long, QRegSelDocESPA>  {

	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 
	
}

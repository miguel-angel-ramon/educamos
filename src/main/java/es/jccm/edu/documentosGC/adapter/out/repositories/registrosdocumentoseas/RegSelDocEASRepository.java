package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentoseas;

import org.springframework.data.jpa.repository.Query;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoseas.QRegSelDocEAS;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoseas.RegSelDocEAS;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocEASRepository extends AbstractRepository<RegSelDocEAS, Long, QRegSelDocEAS>  {
	
	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 

}

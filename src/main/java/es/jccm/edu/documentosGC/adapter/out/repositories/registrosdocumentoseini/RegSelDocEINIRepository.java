package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentoseini;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.documentosGC.application.domain.registrosdocumentoseini.QRegSelDocEINI;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoseini.RegSelDocEINI;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocEINIRepository extends AbstractRepository<RegSelDocEINI, Long, QRegSelDocEINI>  {

	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 
	
}

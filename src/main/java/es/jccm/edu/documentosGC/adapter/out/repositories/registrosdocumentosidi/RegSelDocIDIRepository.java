package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentosidi;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.documentosGC.application.domain.registrosdocumentosidi.QRegSelDocIDI;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosidi.RegSelDocIDI;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocIDIRepository extends AbstractRepository<RegSelDocIDI, Long, QRegSelDocIDI>  {

	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 
	
}

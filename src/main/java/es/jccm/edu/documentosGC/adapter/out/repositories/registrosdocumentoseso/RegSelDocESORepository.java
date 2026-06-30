package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentoseso;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.documentosGC.application.domain.resgistrosdocumentoseso.QRegSelDocESO;
import es.jccm.edu.documentosGC.application.domain.resgistrosdocumentoseso.RegSelDocESO;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocESORepository extends AbstractRepository<RegSelDocESO, Long, QRegSelDocESO>  {

	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 
	
}

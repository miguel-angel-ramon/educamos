package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentosnoformales;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.documentosGC.application.domain.registrosdocumentosnoformales.QRegSelDocNoFormales;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosnoformales.RegSelDocNoFormales;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocNoFormalesRepository extends AbstractRepository<RegSelDocNoFormales, Long, QRegSelDocNoFormales>  {
	
	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 

}

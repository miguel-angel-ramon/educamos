package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentoselementales;

import org.springframework.data.jpa.repository.Query;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoselementales.QRegSelDocElementales;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentoselementales.RegSelDocElementales;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocElementalesRepository extends AbstractRepository<RegSelDocElementales, Long, QRegSelDocElementales>  {

	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 
	
}

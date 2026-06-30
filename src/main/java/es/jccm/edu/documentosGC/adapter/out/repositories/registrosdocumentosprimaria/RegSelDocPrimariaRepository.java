package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentosprimaria;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.documentosGC.application.domain.registrosdocumentosprimaria.QRegSelDocPrimaria;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosprimaria.RegSelDocPrimaria;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocPrimariaRepository extends AbstractRepository<RegSelDocPrimaria, Long, QRegSelDocPrimaria>  {

	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 
	
}

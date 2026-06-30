package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentosdeportiva;

import org.springframework.data.jpa.repository.Query;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentodeportiva.QRegSelDocDeportiva;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentodeportiva.RegSelDocDeportiva;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocDeportivaRepository extends AbstractRepository<RegSelDocDeportiva, Long, QRegSelDocDeportiva>  {

	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 
	
}

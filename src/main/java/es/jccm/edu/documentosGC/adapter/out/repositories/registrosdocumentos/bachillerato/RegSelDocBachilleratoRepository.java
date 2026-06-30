package es.jccm.edu.documentosGC.adapter.out.repositories.registrosdocumentos.bachillerato;

import org.springframework.data.jpa.repository.Query;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosbachillerato.QRegSelDocBachillerato;
import es.jccm.edu.documentosGC.application.domain.registrosdocumentosbachillerato.RegSelDocBachillerato;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocBachilleratoRepository extends AbstractRepository<RegSelDocBachillerato, Long, QRegSelDocBachillerato>  {

	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 
	
}

package es.jccm.edu.documentosGC.adapter.out.repositories.registrodocumentosarte;


import org.springframework.data.jpa.repository.Query;


import es.jccm.edu.documentosGC.application.domain.registrodocumentosarte.RegSelDocArte;
import es.jccm.edu.documentosGC.application.domain.registrodocumentosarte.QRegSelDocArte;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface RegSelDocArteRepository extends AbstractRepository<RegSelDocArte, Long, QRegSelDocArte>  {

	@Query(value ="SELECT TLS_RSDXIDENTIFICADOR.nextval FROM dual", nativeQuery = true)
	Long getNextvalTlregseldoc(); 
	
}

package es.jccm.edu.marcajes.adapter.out.repositories.marcajes;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.marcajes.application.domain.Marcaje;
import es.jccm.edu.marcajes.application.domain.QMarcaje;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface MarcajesRepository extends AbstractRepository<Marcaje, Long, QMarcaje>{

	@Query(value = "select DECODE (COUNT(1),0,'no','si') data "
			+ " from TLMARCAJES MA WHERE MA.IDEVENTO = ?1 "
			+ "AND MA.URLMARCAJE = ?2", nativeQuery = true)
	String getExisteMarcaje(String idEvento, String urlMarcaje) ;
	

	List<Marcaje> findByIdEvento(String idEvento);
	
	@Query(value = "select * "
			+ " from TLMARCAJES MA WHERE MA.IDEVENTO = ?1 "
			+ "AND MA.URLMARCAJE = ?2", nativeQuery = true)
	Marcaje findByIdEventoUrlMarcaje(String idEvento, String urlMarcajeString);
	
	
	
}

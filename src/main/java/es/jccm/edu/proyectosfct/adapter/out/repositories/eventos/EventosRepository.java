package es.jccm.edu.proyectosfct.adapter.out.repositories.eventos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.jccm.edu.proyectosfct.application.domain.eventos.Eventos;
import es.jccm.edu.proyectosfct.application.domain.eventos.QEventos;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EventosRepository extends AbstractRepository<Eventos, Long, QEventos> {

	
    @Query(value = "SELECT NVL(MAX(MESSAGE_ORDER),0) FROM DELPHOS_MODACC.AU_OUTBOX ", nativeQuery = true)
	Integer getMaxOrder();		
	

}

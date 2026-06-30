package es.jccm.edu.proyectosfct.adapter.out.repositories.firmantesdigital;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConveniosFct;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.QConveniosFct;

public interface AdjuntosFirmantesFCTRepository extends AbstractRepository<ConveniosFct, Long, QConveniosFct>  {

	@Query(value="select T_VALPAR from tlpargen where t_nompar = 'VALCERFIR'",nativeQuery = true)
	List<String> getEntornoFirma();
	
	
	@Query(value="select T_VALPAR from tlpargen where t_nompar = 'VALENVMAIL'",nativeQuery = true)
	List<String> getEntornoMail();
	
}

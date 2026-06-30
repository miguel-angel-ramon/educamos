package es.jccm.edu.pdc.adapter.out.repositories.planActuacion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.pdc.application.domain.planActuacion.tablas.QInformacionImpactoPDC;
import es.jccm.edu.pdc.application.domain.planActuacion.tablas.InformacionImpactoPDC;
import es.jccm.edu.pdc.application.domain.planActuacion.tablas.InformacionSeguimientoPDC;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface InformacionImpactoPDCRepository extends AbstractRepository<InformacionImpactoPDC, Long, QInformacionImpactoPDC> {
	
	@Query(value = "SELECT * FROM DELPHOS_SEGEDU.TLPDCINFIMPAC IMPAC   "
			+ "WHERE IMPAC.X_CENTRO = :x_centro "
			+ "AND IMPAC.C_ANNO = :anno AND IMPAC.X_CUEPUB = :x_cuepub", nativeQuery = true)
	InformacionImpactoPDC findMejorasGlobal(@Param("x_centro") Integer x_centro,
			@Param("x_cuepub") Integer x_cuepub, @Param("anno") Integer anno);
}

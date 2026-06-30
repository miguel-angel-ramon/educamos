package es.jccm.edu.pdc.adapter.out.repositories.planActuacion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.pdc.application.domain.planActuacion.tablas.QInformacionSeguimientoPDC;
import es.jccm.edu.pdc.application.domain.planActuacion.tablas.InformacionSeguimientoPDC;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface InformacionSeguimientoPDCRepository extends AbstractRepository<InformacionSeguimientoPDC, Long, QInformacionSeguimientoPDC> {

	@Query(value = "SELECT * FROM DELPHOS_SEGEDU.TLPDCINFSEG SEG  "
			+ "WHERE SEG.X_COMPETENCIA  = :x_competencia AND SEG.X_CENTRO = :x_centro  "
			+ "AND SEG.C_ANNO = :anno AND SEG.X_CUEPUB = :x_cuepub", nativeQuery = true)
	InformacionSeguimientoPDC findByXCompetencia(@Param("x_competencia") Integer x_competencia,@Param("x_centro") Integer x_centro,
			@Param("x_cuepub") Integer x_cuepub, @Param("anno") Integer anno);


}


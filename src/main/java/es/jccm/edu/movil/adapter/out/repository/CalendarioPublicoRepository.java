package es.jccm.edu.movil.adapter.out.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.movil.application.domain.CalendarioPublico;
import es.jccm.edu.movil.application.domain.QCalendarioPublico;
import es.jccm.edu.movil.application.domain.projection.CalendarioPublicoProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface CalendarioPublicoRepository extends AbstractRepository<CalendarioPublico, Long, QCalendarioPublico>{

	
	
	@Modifying
	@Query(value= "SELECT cal.C_ANNO AS anyo, cal.X_IDENTIFICADOR AS id, cal.F_FIESTA AS fechaFiesta , cal.D_FIESTA AS descFiesta , cal.AMBITO  AS ambito , cal.L_AFEDOC AS afectaDocente , cal.L_AFENODOC AS noAfectaDocente " +
			"FROM TLCALENDARIO_V cal " +
			"LEFT JOIN TLDATOSCEN dc ON cal.ambito = 'Provincial' AND cal.x_identificador = dc.c_provincia AND dc.x_centro IN (:id) " +
			"WHERE cal.ambito = 'Autonómico' OR cal.ambito = 'Localidad' AND cal.x_identificador IN (:id) " +
			"ORDER BY cal.f_fiesta DESC ", nativeQuery = true)
	List<CalendarioPublicoProjection> getFestivosByIdentificador(Long id);


	@Query(value= "SELECT cal.C_ANNO AS anyo, cal.X_IDENTIFICADOR id, cal.F_FIESTA fechaFiesta , cal.D_FIESTA descFiesta , cal.AMBITO ambito , cal.L_AFEDOC afectaDocente , cal.L_AFENODOC noAfectaDocente "
			+ " FROM TLCALENDARIO_V cal WHERE (EXTRACT(YEAR FROM cal.F_FIESTA) = :añoActual OR EXTRACT(YEAR FROM cal.F_FIESTA) = :añoActual + 1 ) AND cal.AMBITO = 'Autonómico'", nativeQuery = true)
	List<CalendarioPublicoProjection> findCalendarioPublicoDelUltimoAnio( @Param("añoActual") Long añoActual);
}


package es.jccm.edu.horarios.adapter.out.repositories.calendario;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.horarios.application.domain.calendario.Calendario;
import es.jccm.edu.horarios.application.domain.calendario.QCalendario;
import es.jccm.edu.horarios.application.domain.calendario.projection.CalendarioProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface CalendarioRepository extends AbstractRepository<Calendario, Long, QCalendario>{

	
	
	@Modifying
	@Query(value= "select DISTINCT cal.x_identificador as id, cal.c_anno as anyo, cal.f_fiesta as fechaFiesta, cal.d_fiesta as tipoFiesta, cal.ambito as ambito, cal.c_usucreacion as usuarioCreacion"
			+ " FROM TLCALENDARIO_V cal"
			+ " WHERE   ((ambito = 'Localidad' AND X_IDENTIFICADOR in (:id)) OR"
			+ " (ambito = 'Provincial'  AND X_IDENTIFICADOR = (SELECT C_PROVINCIA FROM TLDATOSCEN WHERE X_CENTRO in (:id))) OR"
			+ " (ambito = 'Autonómico'))"
			+ " ORDER BY F_FIESTA desc", nativeQuery = true)
	List<CalendarioProjection> getFestivosByIdentificador(Long id);
	
	@Query(value="SELECT DISTINCT cal.X_IDENTIFICADOR as id, cal.C_ANNO as anyo, cal.F_FIESTA as fechaFiesta, cal.D_FIESTA as tipoFiesta, "
    		+ "cal.AMBITO as ambito, cal.C_USUCREACION as usuarioCreacion, cal.C_USUACTUALIZA usuarioActualiza, cal.F_ACTUALIZA fechaActualizacion "
    		+ "FROM DELPHOS_SEGEDU.TLCALENDARIO_V cal "
    		+ "WHERE (cal.C_ANNO = :anyo AND cal.AMBITO = 'Nacional') "
    		+ "OR (cal.C_ANNO = :anyo AND cal.AMBITO = 'Castilla-La Mancha') "
    		+ "OR (cal.C_ANNO = :anyo "
    		+ "AND cal.AMBITO = 'Provincial' "
    		+ "AND cal.X_IDENTIFICADOR = (SELECT C_PROVINCIA FROM DELPHOS_SEGEDU.TLDATOSCEN dc WHERE dc.X_CENTRO = :idCentro )) "
    		+ "OR (cal.C_ANNO = :anyo AND cal.AMBITO = 'Localidad' "
    		+ "AND cal.X_IDENTIFICADOR = :idCentro ) "
    		+ "ORDER BY cal.F_FIESTA", nativeQuery = true)
    List<CalendarioProjection> getDiasFestivos(@Param("anyo") Long anyo, @Param("idCentro") Long idCentro);
	
}

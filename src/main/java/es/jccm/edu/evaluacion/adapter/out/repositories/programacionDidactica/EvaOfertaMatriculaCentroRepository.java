package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaOfertaMatriculaCentro;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaOfertaMatriculaCentro;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaOfertaMatriculaCentroRepository extends AbstractRepository<EvaOfertaMatriculaCentro, Long, QEvaOfertaMatriculaCentro> {
    
	Optional<EvaOfertaMatriculaCentro> findByCentroIdAndOfertaMatriculaGenericoId(Long centroId, Long ofertaMatriculaGenericoId);
	
	@Query(value = "SELECT omc.* FROM DELPHOS.TLOFEMATRCEN omc "
			+ "INNER JOIN TLCENTROS cen ON cen.X_CENTRO = omc.X_CENTRO "
			+ "INNER JOIN TLPERIODOSOMC pomc ON pomc.X_OFERTAMATRIC = omc.X_OFERTAMATRIC "
			+ "WHERE omc.X_OFERTAMATRIG = :idOfertaMatrig AND cen.C_CODIGO = :codigoCentro "
			+ "AND pomc.C_ANNO <= :anyo AND (pomc.C_ANNOPUEDETERMINAR IS NULL OR pomc.C_ANNOPUEDETERMINAR >= :anyo)", nativeQuery = true)
	EvaOfertaMatriculaCentro getCursoByCentroOfertamatrigAndAnyo(@Param("codigoCentro") Long codigoCentro, @Param("idOfertaMatrig") Long idOfertaMatrig, @Param("anyo") Integer anyo);
	
}
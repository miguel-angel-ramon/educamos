package es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades;

import java.util.List;
import java.util.Optional;

import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.SistemaCalificacionProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaCalificacion;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.QEvaCalificacion;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaCalificacionRepository extends AbstractRepository<EvaCalificacion, Long, QEvaCalificacion> {

	@Query("SELECT cal FROM EvaCalificacion cal " +
			"INNER JOIN EvaRelacionSistemaCalificacionEtapaEducativa rsiseta ON rsiseta.idSistCal = cal.sistema " +
			"WHERE rsiseta.idEtapa = :idEtapa AND cal.numero = :nota")
	Optional<EvaCalificacion> findByIdEtapaAndNota(@Param("idEtapa") Long idEtapa, @Param("nota") Integer nota);

	@Query(value = "select cal.X_CALIFICA idCalifica, cal.X_SISTCAL idSistCal,   " +
			"cal.N_NUMERO nota, cal.D_CALIFICA descripcion, cal.T_ABREV descCal, cal.L_APRUEBA aprueba, sis.S_SISTCAL descSis, DECODE(sis.l_numerico, 'S', 'true', 'false') numerico   " +
			"from tlofematrgen ofg   " +
			"INNER JOIN TLSISCAL sis ON sis.x_sistcal = ofg.x_sistcal   " +
			"INNER JOIN TLCALIFICACIONES CAL ON CAL.x_sistcal = sis.x_sistcal    " +
			"INNER JOIN TLMATALU MAT ON MAT.X_OFERTAMATRIG = OFG.X_OFERTAMATRIG AND MAT.X_MATRICULA = :idMatricula " +
			"WHERE CAL.N_NUMERO IS NOT NULL ", nativeQuery = true)
	List<SistemaCalificacionProjection> getSistemaCalificacionNotaMateriaByIdMatricula(@Param("idMatricula") Long idMatricula);

	@Query(value = "select cal.X_CALIFICA idCalifica, cal.X_SISTCAL idSistCal,   " +
			"cal.N_NUMERO nota, cal.D_CALIFICA descripcion, cal.T_ABREV descCal, cal.L_APRUEBA aprueba, sis.S_SISTCAL descSis, DECODE(sis.l_numerico, 'S', 'true', 'false') numerico   " +
			"from tlofematrgen ofg   " +
			"INNER JOIN TLSISCAL sis ON sis.x_sistcal = ofg.x_sistcal   " +
			"INNER JOIN TLCALIFICACIONES CAL ON CAL.x_sistcal = sis.x_sistcal  " +
			"INNER JOIN TLMATMATRIALU MATMATRI ON MATMATRI.X_MATMATRICULA = :idMatMatricula " +
			"INNER JOIN TLMATALU MAT ON mat.X_MATRICULA = MATMATRI.X_MATRICULA AND MAT.X_OFERTAMATRIG = OFG.X_OFERTAMATRIG " +
			"WHERE CAL.N_NUMERO IS NOT NULL ", nativeQuery = true)
	List<SistemaCalificacionProjection> getSistemaCalificacionNotaMateriaByIdMatMatricula(@Param("idMatMatricula") Long idMatMatricula);
}
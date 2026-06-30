package es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.CriterioEvaluacionConPorcentajeYPesoProjection;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.ValoracionTemporalCompetenciaEspecificaAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalCompetenciaEspecificaAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalCriterioEvaluacionAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.ValoracionTemporalCompetenciaEspecificaAlumnoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.QEvaValoracionTemporalCompetenciaEspecificaAlumno;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface EvaValoracionTemporalCompetenciaEspecificaAlumnoRepository extends AbstractRepository<EvaValoracionTemporalCompetenciaEspecificaAlumno, Long, QEvaValoracionTemporalCompetenciaEspecificaAlumno> {

	@Query(value = "SELECT RELPONCOMESP.X_COMESP comEsp, COMVALUTEMP.X_VALCOMALUTEMP idCompetencia, COMVALUTEMP.X_MATMATRICULA matMatricula, " +
			"COMVALUTEMP.X_PONDERACION idPonderacion, cal.S_CALIFICA descCal, NVL(cal.N_NUMERO, cal.N_ORDEN) nota, cal.L_APRUEBA aprueba " +
			"FROM TLRELPONCOMESP RELPONCOMESP " +
			"LEFT OUTER JOIN EVA_VALCOMALUTEMP COMVALUTEMP ON COMVALUTEMP.X_COMESP = RELPONCOMESP.X_COMESP  " +
			"AND COMVALUTEMP.X_PONDERACION = RELPONCOMESP.X_PONDERACION  " +
			"AND COMVALUTEMP.X_PONDERACION = RELPONCOMESP.X_PONDERACION  " +
			"AND COMVALUTEMP.X_MATMATRICULA = :idMatMatricula " +
			"LEFT OUTER JOIN TLCALIFICACIONES CAL ON COMVALUTEMP.X_CALIFICA = CAL.X_CALIFICA  " +
			"WHERE RELPONCOMESP.X_PONDERACION = :idPonderacion", nativeQuery = true)
	List<ValoracionTemporalCompetenciaEspecificaAlumnoProjection> findAllByIdMatMatriculaAndIdPonderacion(@Param("idMatMatricula") Long idMatMatricula,
																										@Param("idPonderacion") Long idPonderacion);
	
	@Query(value = "SELECT vcoat.* FROM DELPHOS.EVA_VALCOMALUTEMP vcoat "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcoat.X_MATMATRICULA "
			+ "INNER JOIN DELPHOS.TLMATALU mua ON mua.X_MATRICULA = mma.X_MATRICULA "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag ON uag.X_UNIDAD = mua.X_UNIDAD AND uag.X_MATERIAOMG = mma.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU grua ON grua.X_GRUACTPROALU = uag.X_GRUACTPROALU AND grua.C_ANNO = mua.C_ANNO "
			+ "WHERE uag.X_MATERIAOMG = :idMateriaOmg AND uag.X_UNIDAD = :idUnidad AND vcoat.X_PONDERACION = :idPonderacion AND grua.X_EMPLEADO = :idEmpleado", nativeQuery = true)
	List<EvaValoracionTemporalCompetenciaEspecificaAlumno> findAllByIdMateriaOmgAndIdUnidadAndIdPonderacionAndIdEmpleado(@Param("idMateriaOmg") Long idMateriaOmg,
																											@Param("idUnidad") Long idUnidad,
																											@Param("idPonderacion") Long idPonderacion,
																											@Param("idEmpleado") Long idEmpleado);

	@Query(value = "SELECT VALCRIALUTEMP.X_CRIEVA idCrieva, VALCRIALUTEMP.X_VALCRIALUTEMP idValCriAluTemp, VALCRIALUTEMP.X_CALIFICA idCalifica,  " +
			"CAL.N_NUMERO numero, RELPONCRIEVA.PESO, RELPONCRIEVA.PORCENTAJE  " +
			"FROM EVA_VALCRIALUTEMP VALCRIALUTEMP  " +
			"INNER JOIN TLRELPONCRIEVA RELPONCRIEVA ON RELPONCRIEVA.X_PONDERACION = VALCRIALUTEMP.X_PONDERACION   " +
			" AND RELPONCRIEVA.X_CRIEVA = VALCRIALUTEMP.X_CRIEVA    " +
			"INNER JOIN TLCRIEVA CRIEVA ON CRIEVA.X_CRIEVA = RELPONCRIEVA.X_CRIEVA   " +
			" AND CRIEVA.X_COMESP = :idCompetenciaEspecifica  " +
			"INNER JOIN TLCALIFICACIONES CAL ON CAL.X_CALIFICA = VALCRIALUTEMP.X_CALIFICA   " +
			"WHERE VALCRIALUTEMP.X_PONDERACION = :idPonderacion  " +
			"AND VALCRIALUTEMP.X_MATMATRICULA = :idMatMatricula", nativeQuery = true)
	List<CriterioEvaluacionConPorcentajeYPesoProjection> findAllNotasCriterioByIdCompetenciaEspecificaAndIdPonderacionAndIdMatMatricula(
			@Param("idCompetenciaEspecifica") Long idCompetenciaEspecifica,
			@Param("idPonderacion") Long idPonderacion,
			@Param("idMatMatricula") Long idMatMatricula);

	EvaValoracionTemporalCompetenciaEspecificaAlumno findByIdPonderacionAndComEspAndMatMatricula(Long idPonderacion, Long idCompetenciaEspecifica, Long idMatMatricula);
	
	List<EvaValoracionTemporalCompetenciaEspecificaAlumno> findByIdPonderacionAndMatMatricula(Long idPonderacion, Long idMatMatricula);
	
	@Query(value = "SELECT ROUND(AVG(NVL(cal.N_NUMERO, cal.N_ORDEN))) notaMedia "
			+ "FROM DELPHOS.TLDESOPERATIVO dop "
			+ "INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO "
			+ "INNER JOIN DELPHOS.EVA_VALCOMALUTEMP vcomalut ON vcomalut.X_COMESP = rdoce.X_COMESP "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalut.X_MATMATRICULA "
			+ "INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalut.X_CALIFICA "
			+ "INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA "
			+ "WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATRICULA = :idMatricula", nativeQuery = true)
	Integer getNotaMediaRedondeadaByIdDescriptorOperativoAndIdMatricula(@Param("idDescriptorOperativo") Long idDescriptorOperativo, @Param("idMatricula") Long idMatricula);
	
	@Query(value = "SELECT ROUND(AVG(NVL(nota.numero, nota.orden))) notaMedia FROM "
			+ "(SELECT cal.N_NUMERO numero, cal.N_ORDEN orden "
			+ "FROM DELPHOS.TLDESOPERATIVO dop "
			+ "INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO "
			+ "INNER JOIN DELPHOS.EVA_VALCOMALUTEMP vcomalut ON vcomalut.X_COMESP = rdoce.X_COMESP "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalut.X_MATMATRICULA "
			+ "INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalut.X_CALIFICA "
			+ "INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA "
			+ "WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATRICULA = :idMatricula AND mma.X_MATMATRICULA NOT IN (:idsMatMatriculaACNEE) "
			+ "UNION ALL "
			+ "SELECT CASE WHEN cal.N_ORDEN > 2 AND siseta.X_ETAPA = :idEtapaAdaptacion THEN (SELECT cal2.N_NUMERO FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) ELSE (SELECT cal2.N_NUMERO FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) END numero, "
			+ "CASE WHEN cal.N_ORDEN > 2 AND siseta.X_ETAPA = :idEtapaAdaptacion THEN (SELECT cal2.N_ORDEN FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) ELSE (SELECT cal2.N_ORDEN FROM DELPHOS.TLCALIFICACIONES cal2 WHERE cal2.X_SISTCAL = cal.X_SISTCAL AND cal2.N_ORDEN = 2) END orden "
			+ "FROM DELPHOS.TLDESOPERATIVO dop "
			+ "INNER JOIN DELPHOS.TLRELDESOPECOMESP rdoce ON rdoce.X_DESOPERATIVO = dop.X_DESOPERATIVO "
			+ "INNER JOIN DELPHOS.EVA_VALCOMALUTEMP vcomalut ON vcomalut.X_COMESP = rdoce.X_COMESP "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcomalut.X_MATMATRICULA "
			+ "INNER JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcomalut.X_CALIFICA "
			+ "INNER JOIN DELPHOS.TLRELSISETA siseta ON siseta.X_SISTCAL = cal.X_SISTCAL AND siseta.X_ETAPA = dop.X_ETAPA "
			+ "WHERE rdoce.X_DESOPERATIVO = :idDescriptorOperativo AND mma.X_MATMATRICULA IN (:idsMatMatriculaACNEE)) nota", nativeQuery = true)
	Integer getNotaMediaRedondeadaACNEEByIdDescriptorOperativoAndIdMatriculaAndIdEtapaAdaptacionAndIdsMatMatriculaACNEE(@Param("idDescriptorOperativo") Long idDescriptorOperativo, @Param("idMatricula") Long idMatricula, @Param("idEtapaAdaptacion") Long idEtapaAdaptacion, @Param("idsMatMatriculaACNEE") Long[] idsMatMatriculaACNEE);
}

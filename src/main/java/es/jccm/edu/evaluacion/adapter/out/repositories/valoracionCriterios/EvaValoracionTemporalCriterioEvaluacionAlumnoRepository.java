package es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.AlumnosActividadCalcularAllCriteriosProjection;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.DatosCalcularAllCriteriosProjection;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.ValoracionTemporalCriterioEvaluacionAlumnoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.QEvaValoracionTemporalCriterioEvaluacionAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalCriterioEvaluacionAlumno;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface EvaValoracionTemporalCriterioEvaluacionAlumnoRepository extends AbstractRepository<EvaValoracionTemporalCriterioEvaluacionAlumno, Long, QEvaValoracionTemporalCriterioEvaluacionAlumno> {

	@Query("SELECT vcrat FROM EvaValoracionTemporalCriterioEvaluacionAlumno vcrat "
			+ "INNER JOIN EvaCriterioEvaluacion criEva ON criEva.id = vcrat.criEva "
			+ "WHERE vcrat.idPonderacion = :idPonderacion AND vcrat.matMatricula = :idMatMatriAlu AND criEva.competenciaEspecifica.id = :idComEsp "
			+ "ORDER BY criEva.orden")
	List<EvaValoracionTemporalCriterioEvaluacionAlumno> findAllByIdPonderacionAndIdMatMatriAluAndIdComEsp(@Param("idPonderacion") Long idPonderacion, @Param("idMatMatriAlu") Long idMatMatriAlu, @Param("idComEsp") Long idComEsp);

	@Query(value = "SELECT CRIVALUTEMP.X_VALCRIALUTEMP id, RELPONCRIEVA.X_CRIEVA crieva, CRIVALUTEMP.X_MATMATRICULA matMatricula, " +
			"CRIVALUTEMP.X_PONDERACION idPonderacion, cal.S_CALIFICA descCal, NVL(cal.N_NUMERO, cal.N_ORDEN) nota, cal.L_APRUEBA aprueba, cal.x_califica idCalifica " +
			"FROM TLRELPONCRIEVA RELPONCRIEVA " +
			"INNER JOIN TLCRIEVA CRIEVA ON CRIEVA.X_CRIEVA = RELPONCRIEVA.X_CRIEVA AND CRIEVA.X_COMESP = :idComEsp " +
			"LEFT OUTER JOIN EVA_VALCRIALUTEMP CRIVALUTEMP ON CRIVALUTEMP.X_CRIEVA = RELPONCRIEVA.X_CRIEVA  " +
			"AND CRIVALUTEMP.X_PONDERACION = RELPONCRIEVA.X_PONDERACION  " +
			"AND CRIVALUTEMP.X_PONDERACION = RELPONCRIEVA.X_PONDERACION  " +
			"AND CRIVALUTEMP.X_MATMATRICULA = :idMatMatriAlu " +
			"LEFT OUTER JOIN TLCALIFICACIONES CAL ON CRIVALUTEMP.X_CALIFICA = CAL.X_CALIFICA  " +
			"WHERE RELPONCRIEVA.X_PONDERACION = :idPonderacion", nativeQuery = true)
	List<ValoracionTemporalCriterioEvaluacionAlumnoProjection> findAllByIdMatMatriAluAndIdComEspAndIdPonderacion(@Param("idMatMatriAlu") Long idMatMatriAlu,
																												@Param("idComEsp") Long idComEsp,
																												@Param("idPonderacion") Long idPonderacion);
	
	EvaValoracionTemporalCriterioEvaluacionAlumno findByIdPonderacionAndCriEvaAndMatMatricula(Long idPonderacion, Long IdCriterio, Long MatMatriAlu);
	
	@Query(value = "SELECT vcrat.* FROM DELPHOS.EVA_VALCRIALUTEMP vcrat "
			+ "INNER JOIN DELPHOS.TLMATMATRIALU mma ON mma.X_MATMATRICULA = vcrat.X_MATMATRICULA "
			+ "INNER JOIN DELPHOS.TLMATALU mua ON mua.X_MATRICULA = mma.X_MATRICULA "
			+ "INNER JOIN DELPHOS.TLUNIAFEGRUACTPRO uag ON uag.X_UNIDAD = mua.X_UNIDAD AND uag.X_MATERIAOMG = mma.X_MATERIAOMG "
			+ "INNER JOIN DELPHOS.TLGRUACTPROALU grua ON grua.X_GRUACTPROALU = uag.X_GRUACTPROALU AND grua.C_ANNO = mua.C_ANNO "
			+ "WHERE uag.X_MATERIAOMG = :idMateriaOmg AND uag.X_UNIDAD = :idUnidad AND vcrat.X_PONDERACION = :idPonderacion AND grua.X_EMPLEADO = :idEmpleado", nativeQuery = true)
	List<EvaValoracionTemporalCriterioEvaluacionAlumno> findAllByIdMateriaOmgAndIdUnidadAndIdPonderacionAndIdEmpleado(@Param("idMateriaOmg") Long idMateriaOmg,
																									@Param("idUnidad") Long idUnidad,
																									@Param("idPonderacion") Long idPonderacion,
																									@Param("idEmpleado") Long idEmpleado);

	@Query(value = "select calif  " +
			"from (select ca.N_numero calif " +
			"    from eva_valcriactalu val, eva_relactalum acal, " +
			"    eva_actividad act, eva_relprogunidad erp, eva_relprogdidpond erpo, tlrelponcrieva rpc, " +
			"    eva_reluniprogcrieva ruc, eva_relactcrieva acr, tlcalificaciones ca, eva_unidadprog eup " +
			"    where val.id_relactalum=acal.id_relactalum " +
			"    and val.id_relactcrieva=acr.id_relactcrieva " +
			"    and acal.x_matricula= :idMatricula " +
			"    and acal.id_actividad=act.id_actividad " +
			"    and ruc.id_unidadprog=act.id_unidadprog " +
			"    and ruc.x_crieva=acr.x_crieva " +
			"    and acr.id_actividad=acal.id_actividad " +
			"    and ruc.id_unidadprog=erp.id_unidadprog " +
			"    and erp.id_progdidac=erpo.id_progdidac " +
			"    and rpc.x_ponderacion=erpo.x_ponderacion " +
			"    and rpc.x_ponderacion=:idPonderacion " +
			"    and val.x_califica=ca.x_califica " +
			"    and rpc.x_crieva=ruc.x_crieva " +
			"    and ruc.x_crieva=:idCriterio " +
			"    and eup.id_unidadprog=ruc.id_unidadprog " +
			"    order by eup.nu_ordenpres desc, act.nu_ordenpres desc) " +
			"where rownum=1", nativeQuery = true)
	Long getNotaUltimoValor(@Param("idMatricula") Long idMatricula,
							@Param("idPonderacion") Long idPonderacion,
							@Param("idCriterio") Long idCriterio);

	@Query(value = "SELECT DISTINCT ACTCRIEVA.X_CRIEVA " +
			"FROM EVA_VALCRIACTALU VALCRIACT " +
			"INNER JOIN EVA_RELACTCRIEVA ACTCRIEVA ON ACTCRIEVA.ID_RELACTCRIEVA = VALCRIACT.ID_RELACTCRIEVA  " +
			"INNER JOIN EVA_ACTIVIDAD ACT ON ACT.ID_ACTIVIDAD = ACTCRIEVA.ID_ACTIVIDAD  " +
			"INNER JOIN EVA_RELACTALUM RELACTALUM ON RELACTALUM.ID_ACTIVIDAD = ACT.ID_ACTIVIDAD AND RELACTALUM.X_MATRICULA = :idMatricula " +
			"INNER JOIN EVA_RELPROGAULACT RELPROGACT ON RELPROGACT.ID_ACTIVIDAD = ACT.ID_ACTIVIDAD AND RELPROGACT.ID_PROGAULA = :idProgramacionAula", nativeQuery = true)
	List<Long> getAllCriteriosAlumnoByIdProgramacionAula(@Param("idProgramacionAula") Long idProgramacionAula,
							@Param("idMatricula") Long idMatricula);

	@Query(value = "SELECT RELPROGPOND.X_PONDERACION idPonderacion, PROGAULA.ID_PROGAULA idProgramacionAula, PROGDIDAC.NU_ANNO anno,  " +
			"(SELECT sisEta.X_SISTCAL idSistemaCalifica   " +
			"FROM EVA_PROGAULA PROGAULA2   " +
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = PROGAULA2.ID_PROGDIDAC    " +
			"INNER JOIN TLMATOFEMATRG mat ON mat.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG   " +
			"INNER JOIN TLMATERIASCURSO matCurso ON mat.X_MATERIAC = matCurso.X_MATERIAC     " +
			"INNER JOIN TLCURSOMODA modalidad ON matCurso.x_cursomod = modalidad.x_cursomod     " +
			"INNER JOIN TLETAPAS curso ON curso.x_etapa = modalidad.x_etapa     " +
			"INNER JOIN TLETAPAS ciclo ON curso.X_ETAPADEPENDEDE = ciclo.X_ETAPA     " +
			"INNER JOIN TLETAPAS etapa ON ciclo.X_ETAPADEPENDEDE = etapa.X_ETAPA   " +
			"INNER JOIN TLRELSISETA sisEta ON sisEta.X_ETAPA = ciclo.X_ETAPADEPENDEDE     " +
			"WHERE PROGAULA2.ID_PROGAULA = PROGAULA.ID_PROGAULA   " +
			"AND (etapa.D_ETAPA like '%(LOMLOE)%' OR ciclo.D_ETAPA like '%(LOMLOE)%')) idSistemaCalifica   " +
			"FROM EVA_ACTIVIDAD ACT    " +
			"INNER JOIN EVA_RELPROGAULACT RELPROGACT ON RELPROGACT.ID_ACTIVIDAD = ACT.ID_ACTIVIDAD  " +
			"INNER JOIN EVA_PROGAULA PROGAULA ON PROGAULA.ID_PROGAULA = RELPROGACT.ID_PROGAULA  " +
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC  " +
			"INNER JOIN EVA_RELPROGDIDPOND RELPROGPOND ON RELPROGPOND.ID_PROGDIDAC = PROGDIDAC.ID_PROGDIDAC  " +
			"WHERE ACT.ID_ACTIVIDAD = :idActividad", nativeQuery = true)
	DatosCalcularAllCriteriosProjection getDatosParaCalculoAllCriterios(@Param("idActividad") Long idActividad);

	@Query(value = "SELECT RELPROGALUM.X_MATRICULA idMatricula, MATMATRI.X_MATMATRICULA idMatMatriAlu   " +
			"FROM EVA_RELACTALUM RELPROGALUM " +
			"INNER JOIN EVA_RELPROGAULACT RELPROGACT ON RELPROGACT.ID_ACTIVIDAD = RELPROGALUM.ID_ACTIVIDAD  " +
			"INNER JOIN EVA_PROGAULA PROGAULA ON PROGAULA.ID_PROGAULA = RELPROGACT.ID_PROGAULA  " +
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = PROGAULA.ID_PROGDIDAC  " +
			"INNER JOIN TLMATMATRIALU MATMATRI ON MATMATRI.X_MATRICULA = RELPROGALUM.X_MATRICULA AND MATMATRI.X_MATERIAOMG = PROGDIDAC.X_MATERIAOMG  " +
			"WHERE RELPROGALUM.ID_ACTIVIDAD = :idActividad", nativeQuery = true)
	List<AlumnosActividadCalcularAllCriteriosProjection> getAlumnosActividad(@Param("idActividad") Long idActividad);

}

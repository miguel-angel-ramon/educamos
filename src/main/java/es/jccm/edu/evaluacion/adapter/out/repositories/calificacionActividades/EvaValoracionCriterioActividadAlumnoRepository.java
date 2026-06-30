package es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaValoracionCriterioActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.QEvaValoracionCriterioActividadAlumno;
import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.NotaCriterioProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaValoracionCriterioActividadAlumnoRepository extends AbstractRepository<EvaValoracionCriterioActividadAlumno, Long, QEvaValoracionCriterioActividadAlumno> {

	EvaValoracionCriterioActividadAlumno findByRelacionActividadCriterioEvaluacionIdAndRelacionActividadAlumnoId(Long relacionActividadCriterioEvaluacionId, Long relacionActividadAlumnoId);

	Long countByRelacionActividadCriterioEvaluacionId(Long relacionActividadCriterioEvaluacionId);

	Long countByRelacionActividadAlumnoId(Long relacionActividadAlumnoId);

	void deleteAllByRelacionActividadCriterioEvaluacionId(Long id);

	@Query(value = "SELECT actCri.X_CRIEVA idCriterio , criAlu.X_CALIFICA idCalifica, cal.N_NUMERO nota, cal.T_ABREV descCal, " +
			" cal.L_APRUEBA aprueba, actcri.ID_ACTIVIDAD idActividad, act.ID_UNIDADPROG idUnidadProgramacion, act.LG_VIENEMOODLE lprocedeMoodle,  " +
			"cri.T_ABREV abreviatura, criAlu.C_USUCREACION usuCreacion  " +
			"FROM EVA_ACTIVIDAD act   " +
			"INNER JOIN EVA_RELACTCRIEVA actCri ON actcri.ID_ACTIVIDAD = act.ID_ACTIVIDAD   " +
			"INNER JOIN EVA_RELACTALUM actAlu ON ACTALU.ID_ACTIVIDAD = actcri.ID_ACTIVIDAD   " +
			"INNER JOIN TLCRIEVA cri ON cri.X_CRIEVA = ACTCRI.X_CRIEVA   " +
			"LEFT JOIN EVA_VALCRIACTALU criAlu ON CRIALU.ID_RELACTCRIEVA = ACTCRI.ID_RELACTCRIEVA AND CRIALU.ID_RELACTALUM = ACTALU.ID_RELACTALUM   " +
			"LEFT JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = criAlu.X_CALIFICA   " +
			"WHERE actAlu.X_MATRICULA = :idMatriAlu " +
			"and(:idUnidadProgramacion = -1 OR act.ID_UNIDADPROG = :idUnidadProgramacion)  " +
			"AND (:idConvCentroOmc = -1 OR act.X_CONVCENTROOMC = :idConvCentroOmc)", nativeQuery = true)
	List<NotaCriterioProjection> getCriteriosAlumno(@Param("idMatriAlu") Long idMatriAlu,
													@Param("idConvCentroOmc") Long idConvCentroOmc,
													@Param("idUnidadProgramacion") Long idUnidadProgramacion);

	@Query(value = "SELECT actCri.X_CRIEVA idCriterio , criAlu.X_CALIFICA idCalifica, cal.N_NUMERO nota, cal.T_ABREV descCal, " +
			" cal.L_APRUEBA aprueba, actcri.ID_ACTIVIDAD idActividad, act.ID_UNIDADPROG idUnidadProgramacion, act.LG_VIENEMOODLE lprocedeMoodle,  " +
			"cri.T_ABREV abreviatura  " +
			"FROM EVA_ACTIVIDAD act   " +
			"INNER JOIN EVA_RELACTCRIEVA actCri ON actcri.ID_ACTIVIDAD = act.ID_ACTIVIDAD   " +
			"INNER JOIN EVA_RELACTALUM actAlu ON ACTALU.ID_ACTIVIDAD = actcri.ID_ACTIVIDAD   " +
			"INNER JOIN TLCRIEVA cri ON cri.X_CRIEVA = ACTCRI.X_CRIEVA   " +
			"LEFT JOIN EVA_VALCRIACTALU criAlu ON CRIALU.ID_RELACTCRIEVA = ACTCRI.ID_RELACTCRIEVA AND CRIALU.ID_RELACTALUM = ACTALU.ID_RELACTALUM   " +
			"LEFT JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = criAlu.X_CALIFICA   " +
			"WHERE actAlu.X_MATRICULA = :idMatriAlu " +
			"AND act.ID_ACTIVIDAD_MOODLE = :idActividadMoodle " +
			"and(:idUnidadProgramacion = -1 OR act.ID_UNIDADPROG = :idUnidadProgramacion)  " +
			"AND (:idConvCentroOmc = -1 OR act.X_CONVCENTROOMC = :idConvCentroOmc)", nativeQuery = true)
	List<NotaCriterioProjection> getCriteriosAlumno(@Param("idMatriAlu") Long idMatriAlu,
													@Param("idActividadMoodle") Long idActividadMoodle,
													@Param("idConvCentroOmc") Long idConvCentroOmc,
													@Param("idUnidadProgramacion") Long idUnidadProgramacion);

	@Query(value = "SELECT actCri.X_CRIEVA idCriterio, actCri.NU_PESO peso, criAlu.X_CALIFICA idCalifica, cal.N_NUMERO nota, cal.T_ABREV descCal, "
			+ "cal.L_APRUEBA aprueba, actcri.ID_ACTIVIDAD idActividad, act.ID_UNIDADPROG idUnidadProgramacion, act.LG_VIENEMOODLE lprocedeMoodle, criAlu.C_USUCREACION usuCreacion " +
			"FROM EVA_ACTIVIDAD act " +
			"INNER JOIN EVA_RELACTCRIEVA actCri ON actcri.ID_ACTIVIDAD = act.ID_ACTIVIDAD  " +
			"INNER JOIN EVA_RELACTALUM actAlu ON ACTALU.ID_ACTIVIDAD = actcri.ID_ACTIVIDAD  " +
			"LEFT JOIN EVA_VALCRIACTALU criAlu ON CRIALU.ID_RELACTCRIEVA = ACTCRI.ID_RELACTCRIEVA AND CRIALU.ID_RELACTALUM = ACTALU.ID_RELACTALUM  " +
			"LEFT JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = criAlu.X_CALIFICA  " +
			"WHERE ACTALU.X_MATRICULA = :idMatricula AND act.ID_ACTIVIDAD = :idActividad", nativeQuery = true)
	List<NotaCriterioProjection> getCriteriosAlumnoActividad(@Param("idMatricula") Long idMatricula, @Param("idActividad") Long idActividad);

	@Query(value = "SELECT actCri.X_CRIEVA idCriterio, actCri.NU_PESO peso, criAlu.X_CALIFICA idCalifica, cal.N_NUMERO nota, cal.T_ABREV descCal, " +
			"cal.L_APRUEBA aprueba, actcri.ID_ACTIVIDAD idActividad, act.LG_VIENEMOODLE lprocedeMoodle " +
			"FROM EVA_ACTIVIDAD act  " +
			"INNER JOIN EVA_RELACTCRIEVA actCri ON actcri.ID_ACTIVIDAD = act.ID_ACTIVIDAD  " +
			"INNER JOIN EVA_RELACTALUM actAlu ON ACTALU.ID_ACTIVIDAD = actcri.ID_ACTIVIDAD  " +
			"LEFT JOIN EVA_VALCRIACTALU criAlu ON CRIALU.ID_RELACTCRIEVA = ACTCRI.ID_RELACTCRIEVA AND CRIALU.ID_RELACTALUM = ACTALU.ID_RELACTALUM  " +
			"LEFT JOIN TLCALIFICACIONES cal ON cal.X_CALIFICA = criAlu.X_CALIFICA  " +
			"WHERE act.ID_ACTIVIDAD = :idActividad", nativeQuery = true)
	List<NotaCriterioProjection> getCriteriosActividad(@Param("idActividad") Long idActividad);
}
package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection.CriterioEvaluacionConPorcentajeYPesoProjection;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalCompetenciaEspecificaAlumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaPonderacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaValoracionCompetenciaEspecificaAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaValoracionCompetenciaEspecificaAlumno;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

import java.util.List;


@Repository
public interface EvaValoracionCompetenciaEspecificaAlumnoRepository extends AbstractRepository<EvaValoracionCompetenciaEspecificaAlumno, Long, QEvaValoracionCompetenciaEspecificaAlumno> {

	void deleteAllByPonderacion(EvaPonderacion ponderacion);

	@Query(value = "SELECT VCA.X_VALCOMALU  " +
			"FROM TLVALCOMALU VCA " +
			"WHERE VCA.X_PONDERACION = :idPonderacion " +
			"AND VCA.X_COMESP = :idCompetenciaEspecifica " +
			"AND VCA.X_MATMATRICULA = :idMatMatricula " +
			"AND VCA.X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
	Long findByIdPonderacionAndIdCompetenciaEspecificaAndIdMatMatriculaAndIdConvCentroOmc(
			@Param("idPonderacion") Long idPonderacion,
			@Param("idCompetenciaEspecifica") Long idCompetenciaEspecifica,
			@Param("idMatMatricula") Long idMatMatricula,
			@Param("idConvCentroOmc") Long idConvCentroOmc);

	@Query(value = "SELECT VCA.X_CRIEVA idCrieva, VCA.X_VALCRIALU idValCriAlu, VCA.X_CALIFICA idCalifica,    " +
			"CAL.N_NUMERO numero, RELPONCRIEVA.PESO, RELPONCRIEVA.PORCENTAJE    " +
			"FROM TLVALCRIALU VCA " +
			"INNER JOIN TLRELPONCRIEVA RELPONCRIEVA ON RELPONCRIEVA.X_PONDERACION = VCA.X_PONDERACION     " +
			" AND RELPONCRIEVA.X_CRIEVA = VCA.X_CRIEVA      " +
			"INNER JOIN TLCRIEVA CRIEVA ON CRIEVA.X_CRIEVA = RELPONCRIEVA.X_CRIEVA     " +
			" AND CRIEVA.X_COMESP = :idCompetenciaEspecifica    " +
			"INNER JOIN TLCALIFICACIONES CAL ON CAL.X_CALIFICA = VCA.X_CALIFICA     " +
			"WHERE VCA.X_PONDERACION = :idPonderacion    " +
			"AND VCA.X_MATMATRICULA = :idMatMatricula " +
			"AND VCA.X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
	List<CriterioEvaluacionConPorcentajeYPesoProjection> findAllNotasCriterioByIdCompetenciaEspecificaAndIdPonderacionAndIdMatMatriculaAndIdConvCentroOmc(
			@Param("idCompetenciaEspecifica") Long idCompetenciaEspecifica,
			@Param("idPonderacion") Long idPonderacion,
			@Param("idMatMatricula") Long idMatMatricula,
			@Param("idConvCentroOmc") Long idConvCentroOmc);
	
}
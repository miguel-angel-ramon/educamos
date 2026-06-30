package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalCriterioEvaluacionAlumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaPonderacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaValoracionCriterioEvaluacionAlumno;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaValoracionCriterioEvaluacionAlumno;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaValoracionCriterioEvaluacionAlumnoRepository extends AbstractRepository<EvaValoracionCriterioEvaluacionAlumno, Long, QEvaValoracionCriterioEvaluacionAlumno> {

	void deleteAllByPonderacion(EvaPonderacion ponderacion);

	@Query(value = "SELECT VCA.X_VALCRIALU " +
			"FROM TLVALCRIALU VCA " +
			"WHERE VCA.X_PONDERACION = :idPonderacion " +
			"AND VCA.X_CRIEVA = :idCriterio " +
			"AND VCA.X_MATMATRICULA = :idMatMatricula " +
			"AND VCA.X_CONVCENTROOMC = :idConvCentroOmc", nativeQuery = true)
	Long findByIdPonderacionAndCriteriosEvaluacionAndIdMatMatriculaAndIdConvCentroOmc(
			@Param("idPonderacion") Long idPonderacion,
			@Param("idCriterio") Long idCriterio,
			@Param("idMatMatricula") Long idMatMatricula,
			@Param("idConvCentroOmc") Long idConvCentroOmc);
}
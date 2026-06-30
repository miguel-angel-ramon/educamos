package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaRelacionPonderacionCriteriosEvaluacionRepository extends AbstractRepository<EvaRelacionPonderacionCriteriosEvaluacion, Long, QEvaRelacionPonderacionCriteriosEvaluacion> {

	@Transactional
	void deleteAllByPonderacion(EvaPonderacion ponderacion);

	List<EvaRelacionPonderacionCriteriosEvaluacion> getAllByPonderacion(EvaPonderacion ponderacion);
	
	@Query("SELECT rpc "
			+ "FROM EvaUnidadProgramacion up "
			+ "INNER JOIN EvaRelacionProgramacionDidacticaUnidadProgramacion rpu ON rpu.unidadProgramacion = up "
			+ "INNER JOIN EvaRelacionUnidadProgramacionCriteriosEvaluacion rupce ON rupce.unidadProgramacion = up "
			+ "INNER JOIN ProgramacionDidactica pd ON pd = rpu.programacionDidactica "
			+ "INNER JOIN EvaCriterioEvaluacion ce ON ce = rupce.criterioEvaluacion "
			+ "INNER JOIN EvaRelacionProgramacionDidacticaPonderacion rpdp ON rpdp.programacionDidactica = pd "
			+ "INNER JOIN EvaPonderacion pond ON pond = rpdp.ponderacion "
			+ "INNER JOIN EvaRelacionPonderacionCriteriosEvaluacion rpc ON rpc.criteriosEvaluacion = ce AND rpc.ponderacion = pond "
			+ "WHERE up = :unidadProgramacion AND ce = :criterioEvaluacion")
	EvaRelacionPonderacionCriteriosEvaluacion findByUnidadProgramacionAndCriterioEvaluacion(@Param("unidadProgramacion") EvaUnidadProgramacion unidadProgramacion, @Param("criterioEvaluacion") EvaCriterioEvaluacion criterioEvaluacion);

	@Query(value = "SELECT relponcri.* " +
			"FROM TLRELPONCRIEVA relponcri " +
			"INNER JOIN TLPONDERACION pond ON pond.X_PONDERACION = relponcri.X_PONDERACION AND pond.X_PONDERACION = :idPonderacion " +
			"INNER JOIN TLCRIEVA crieva ON crieva.X_CRIEVA = relponcri.X_CRIEVA AND crieva.X_COMESP = :idCompetenciaEspecifica", nativeQuery = true)
	List<EvaRelacionPonderacionCriteriosEvaluacion> buscarCriteriosByCompetenciaEspecificaYPonderacion(@Param("idPonderacion") long idPonderacion,
																						   @Param("idCompetenciaEspecifica") long idCompetenciaEspecifica);
}
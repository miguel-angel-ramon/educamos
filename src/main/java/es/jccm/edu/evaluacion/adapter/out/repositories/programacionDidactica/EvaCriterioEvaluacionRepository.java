package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaRelacionUnidadProgramacionCriteriosEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaCriterioEvaluacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaCompetenciaEspecificaDidacticaProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaCriterioEvaluacionRepository extends AbstractRepository<EvaCriterioEvaluacion, Long, QEvaCriterioEvaluacion> {
    
    
    @Query(value = "SELECT DISTINCT COM.X_COMESP id, COM.D_COMESP descripcion, COM.T_ABREV abrev, "
    		+ "COM.X_CICLO idCiclo, COM.N_ORDENPRES nOrdenPres "
    		+ "FROM TLRELCOMPESMAT REL "
    		+ "INNER JOIN TLCOMESP COM ON COM.X_COMESP = REL.X_COMESP " 
    		+ "WHERE REL.X_MATERIAOMG = :idMateriaOmg ORDER BY nOrdenPres", nativeQuery = true)
    List<EvaCompetenciaEspecificaDidacticaProjection> getCompetenciasEspecificas(@Param("idMateriaOmg") Long idMateriaOmg);

	List<EvaCriterioEvaluacion> findAllByCompetenciaEspecificaId(Long competenciaEspecificaId);
	
	@Query("SELECT crieva "
			+ "FROM EvaProgramacionDidactica pd "
			+ "INNER JOIN EvaRelacionProgramacionDidacticaPonderacion rpdp ON rpdp.programacionDidactica = pd "
			+ "INNER JOIN EvaPonderacion pond ON pond = rpdp.ponderacion "
			+ "INNER JOIN EvaRelacionPonderacionCriteriosEvaluacion rpce ON rpce.ponderacion = pond "
			+ "INNER JOIN EvaCriterioEvaluacion crieva ON crieva = rpce.criteriosEvaluacion "
			+ "INNER JOIN EvaCompetenciaEspecificaDidactica comesp ON comesp = crieva.competenciaEspecifica "
			+ "WHERE pd.id = :idProgramacionDidactica "
			+ "ORDER BY comesp.nOrdenPres, crieva.orden")
	List<EvaCriterioEvaluacion> findAllByIdProgramacionDidactica(@Param("idProgramacionDidactica") Long idProgramacionDidactica);

	Optional<EvaCriterioEvaluacion> findByAbreviatura(String abrev);
	
	@Query("SELECT DISTINCT crieva FROM EvaProgramacionAula pa " +
			"INNER JOIN EvaProgramacionDidactica pd ON pd = pa.programacionDidactica " +
			"INNER JOIN EvaRelacionProgramacionAulaActividad rpaact ON rpaact.programacionAula = pa " +
			"INNER JOIN EvaRelacionProgramacionDidacticaUnidadProgramacion rpdup ON rpdup.programacionDidactica = pd " +
			"INNER JOIN EvaUnidadProgramacion up ON up = rpdup.unidadProgramacion " +
			"INNER JOIN EvaActividad act ON act = rpaact.actividad AND act.unidadProgramacion = up " +
			"INNER JOIN EvaRelacionUnidadProgramacionCriteriosEvaluacion rupcrieva ON rupcrieva.unidadProgramacion = up " +
			"INNER JOIN EvaRelacionActividadCriterioEvaluacion ractce ON ractce.actividad = act " +
			"INNER JOIN EvaCriterioEvaluacion crieva ON crieva = ractce.criterioEvaluacion AND crieva = rupcrieva.criterioEvaluacion " +
			"INNER JOIN EvaCompetenciaEspecificaDidactica comesp ON comesp = crieva.competenciaEspecifica " +
			"WHERE pa.id = :idProgramacionAula AND " + 
			"(:idCompetenciaEspecifica IS NULL OR comesp.id = :idCompetenciaEspecifica) " +
			"ORDER BY crieva.orden")
	List<EvaCriterioEvaluacion> findAllByIdProgramacionAulaAndIdCompetenciaEspecifica(@Param("idProgramacionAula") Long idProgramacionAula, @Param("idCompetenciaEspecifica") Long idCompetenciaEspecifica);
}
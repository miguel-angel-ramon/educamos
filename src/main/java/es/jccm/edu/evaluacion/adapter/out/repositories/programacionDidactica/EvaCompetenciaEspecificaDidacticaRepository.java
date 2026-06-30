package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaCompetenciaEspecificaDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaCompetenciaEspecificaDidactica;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaCompetenciaEspecificaDidacticaRepository extends AbstractRepository<EvaCompetenciaEspecificaDidactica, Long, QEvaCompetenciaEspecificaDidactica> {

	@Query("SELECT DISTINCT comesp FROM EvaProgramacionAula pa " +
			"INNER JOIN EvaProgramacionDidactica pd ON pd = pa.programacionDidactica " +
			"INNER JOIN EvaRelacionProgramacionAulaActividad rpaact ON rpaact.programacionAula = pa " +
			"INNER JOIN EvaRelacionProgramacionDidacticaUnidadProgramacion rpdup ON rpdup.programacionDidactica = pd " +
			"INNER JOIN EvaUnidadProgramacion up ON up = rpdup.unidadProgramacion " +
			"INNER JOIN EvaActividad act ON act = rpaact.actividad AND act.unidadProgramacion = up " +
			"INNER JOIN EvaRelacionActividadCriterioEvaluacion ractce ON ractce.actividad = act " +
			"INNER JOIN EvaCriterioEvaluacion crieva ON crieva = ractce.criterioEvaluacion " +
			"INNER JOIN EvaCompetenciaEspecificaDidactica comesp ON comesp = crieva.competenciaEspecifica " +
			"WHERE pa.id = :idProgramacionAula")
	List<EvaCompetenciaEspecificaDidactica> findAllByIdProgramacionAula(@Param("idProgramacionAula") Long idProgamacionAula);
	
}
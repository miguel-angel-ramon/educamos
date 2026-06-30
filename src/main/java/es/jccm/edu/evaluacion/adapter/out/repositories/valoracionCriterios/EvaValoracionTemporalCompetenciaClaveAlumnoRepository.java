package es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalCompetenciaClaveAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.QEvaValoracionTemporalCompetenciaClaveAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.ValoracionTemporalCompetenciaClaveAlumnoProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface EvaValoracionTemporalCompetenciaClaveAlumnoRepository
        extends AbstractRepository<EvaValoracionTemporalCompetenciaClaveAlumno, Long, QEvaValoracionTemporalCompetenciaClaveAlumno> {
	
	List<EvaValoracionTemporalCompetenciaClaveAlumno> findAllByIdMatricula(@Param("idMatricula") Long idMatricula);
	
	@Query(value = "SELECT vcct.ID_VALCOMCLAALUTEMP id, ccl.X_COMCLAVE idCompetenciaClave, vcct.X_MATRICULA idMatricula, vcct.X_CALIFICA idCalifica, "
    		+ "ccl.T_ABREV nombreCompetenciaClave, cal.T_ABREV descCal, cal.D_CALIFICA descDetCal, cal.n_numero nota, cal.L_APRUEBA aprueba "
    		+ "FROM DELPHOS.TLCOMCLAVE ccl "
    		+ "LEFT JOIN DELPHOS.EVA_VALCOMCLAALUTEMP vcct ON vcct.X_COMCLAVE = ccl.X_COMCLAVE AND vcct.X_MATRICULA = :idMatricula "
    		+ "LEFT JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vcct.X_CALIFICA "
    		+ "ORDER BY ccl.N_ORDENPRES", nativeQuery = true)
	List<ValoracionTemporalCompetenciaClaveAlumnoProjection> getValoracionesByIdMatricula(@Param("idMatricula") Long idMatricula);
	
}


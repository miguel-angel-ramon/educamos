package es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.EvaValoracionTemporalDescriptorOperativoAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.entidades.QEvaValoracionTemporalDescriptorOperativoAlumno;
import es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection.ValoracionTemporalDescriptorOperativoAlumnoProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface EvaValoracionTemporalDescriptorOperativoAlumnoRepository
        extends AbstractRepository<EvaValoracionTemporalDescriptorOperativoAlumno, Long, QEvaValoracionTemporalDescriptorOperativoAlumno> {
	
	@Query(value = "SELECT vdoat.ID_VALDESOPEALUTEMP id, dop.X_DESOPERATIVO idDescriptorOperativo, vdoat.X_MATRICULA idMatricula, vdoat.X_CALIFICA idCalifica, "
    		+ "dop.T_ABREV nombreDescriptorOperativo, cal.T_ABREV descCal, cal.D_CALIFICA descDetCal, cal.n_numero nota, cal.L_APRUEBA aprueba "
    		+ "FROM DELPHOS.TLDESOPERATIVO dop "
    		+ "LEFT JOIN DELPHOS.EVA_VALDESOPEALUTEMP vdoat ON vdoat.X_DESOPERATIVO = dop.X_DESOPERATIVO AND vdoat.X_MATRICULA = :idMatricula "
    		+ "LEFT JOIN DELPHOS.TLCALIFICACIONES cal ON cal.X_CALIFICA = vdoat.X_CALIFICA "
    		+ "WHERE dop.X_COMCLAVE = :idCompetenciaClave AND dop.X_ETAPA = :idEtapa "
    		+ "ORDER BY dop.N_ORDENPRES", nativeQuery = true)
	List<ValoracionTemporalDescriptorOperativoAlumnoProjection> findAllByIdCompetenciaClaveAndIdEtapaAndIdMatricula(@Param("idCompetenciaClave") Long idCompetenciaClave, @Param("idEtapa") Long idEtapa, @Param("idMatricula") Long idMatricula);

}


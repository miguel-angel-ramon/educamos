package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaConvocatoriaCentrosOMC;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaConvocatoriaCentrosOMC;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaConvocatoriaCentrosOMCRepository extends AbstractRepository<EvaConvocatoriaCentrosOMC, Long, QEvaConvocatoriaCentrosOMC> {

    @Query(value = "SELECT cco2.* FROM DELPHOS.TLCONVCENOMC cco1 " +
            "INNER JOIN DELPHOS.TLCONVCENTROS cce1 ON cce1.X_CONVCENTRO = cco1.X_CONVCENTRO " +
            "INNER JOIN DELPHOS.TLCONVCENTROS cce2 ON cce2.X_CENTRO = cce1.X_CENTRO " +
            "AND (cce2.X_CONVOGENERAL = cce1.X_CONVOGENERAL AND cce2.X_CONVOPARCGEN IS NULL OR cce2.X_CONVOGENERAL IS NULL AND cce2.X_CONVOPARCGEN = cce1.X_CONVOPARCGEN) " +
            "INNER JOIN DELPHOS.TLCONVCENOMC cco2 ON cco2.X_CONVCENTRO = cce2.X_CONVCENTRO AND cco2.X_OFERTAMATRIC = cco1.X_OFERTAMATRIC " +
            "WHERE cco1.X_CONVCENTROOMC = :idConvCentroOmc AND cce2.C_ANNO = :anno", nativeQuery = true)
    EvaConvocatoriaCentrosOMC getConvocatoriaCentroOMCEquivalenteCurso(@Param("idConvCentroOmc") Long idConvCentroOmc, @Param("anno") Integer anno);
}
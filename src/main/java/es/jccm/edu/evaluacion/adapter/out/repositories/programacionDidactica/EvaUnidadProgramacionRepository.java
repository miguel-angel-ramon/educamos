package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.evaluacion.projection.ConvocatoriaProjection;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.EvaUnidadProgramacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.entidades.QEvaUnidadProgramacion;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaUnidadesProgramacionCriterioProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaUnidadProgramacionRepository extends AbstractRepository<EvaUnidadProgramacion, Long, QEvaUnidadProgramacion> {

	@Query(value = "SELECT relUniCri.X_CRIEVA As idCriterio, COUNT(*) As numUnidadesProgramacion "
			+ " FROM EVA_RELUNIPROGCRIEVA relUniCri "
			+ " INNER JOIN EVA_UNIDADPROG uni ON uni.ID_UNIDADPROG = relUniCri.ID_UNIDADPROG "
			+ " INNER JOIN EVA_RELPROGUNIDAD relProUni ON relProUni.ID_UNIDADPROG = uni.ID_UNIDADPROG "
			+ " INNER JOIN EVA_PROGDIDAC prog ON prog.ID_PROGDIDAC = relProUni.ID_PROGDIDAC "
			+ " WHERE relUniCri.X_CRIEVA IN (:criteriosIds) "
			+ " AND prog.ID_PROGDIDAC = :idProgramacionDidactica "
			+ " GROUP BY relUniCri.X_CRIEVA", nativeQuery = true)
	List<EvaUnidadesProgramacionCriterioProjection> getNumUnidadesProgramacionCriterios(@Param("criteriosIds") List<Long> criteriosIds, @Param("idProgramacionDidactica") Long idProgramacionDidactica);

	@Query(value = "SELECT cce.X_CONVCENTRO idConvCentro, cco.X_CONVCENTROOMC idConvCentroOmc, cce.x_convocatoria idTipoConv, cce.D_CONVOCATORIA convocatoria, "
			+ "cce.F_FECINICON fechaInicio, cce.F_FECFINCON fechaFin "
			+ "FROM TLCENTROS cen "
			+ "INNER JOIN TLCONVCENTROS cce ON cce.X_CENTRO = cen.X_CENTRO "
			+ "INNER JOIN TLCONVCENOMC cco ON cco.X_CONVCENTRO = cce.X_CONVCENTRO "
			+ "INNER JOIN TLOFEMATRCEN ofc ON ofc.X_OFERTAMATRIC = cco.X_OFERTAMATRIC AND ofc.X_CENTRO = cen.X_CENTRO "
			+ "INNER JOIN TLOFEMATRGEN ofg ON ofg.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG "
			+ "INNER JOIN TLMATOFEMATRG mog ON mog.X_OFERTAMATRIG = ofg.X_OFERTAMATRIG "
			+ "WHERE cen.C_CODIGO = :codigoCentro AND cce.C_ANNO = :anno AND mog.X_MATERIAOMG = :idMateria "
			+ "ORDER BY cce.N_ORDEN", nativeQuery = true)
    List<ConvocatoriaProjection> getConvocatorias(@Param("anno") Long anno, @Param("codigoCentro") Long codigoCentro, @Param("idMateria") Long idMateria);
    
}
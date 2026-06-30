package es.jccm.edu.evaluacion.adapter.out.repositories.programacionAula;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.EvaUnidadCentro;
import es.jccm.edu.evaluacion.application.domain.programacionAula.entidades.QEvaUnidadCentro;
import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.UnidadesPorMateriaProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaUnidadCentroRepository extends AbstractRepository<EvaUnidadCentro, Long, QEvaUnidadCentro> {
	
	@Query(value="SELECT DISTINCT uag.x_unidad idUnidad, UNICEN.T_NOMBRE nombreUnidad, UNICEN.X_CENTRO idCentro " +
			"FROM tluniafegruactpro uag " +
			"INNER JOIN TLGRUACTPROALU GRUACT ON GRUACT.X_GRUACTPROALU = UAG.X_GRUACTPROALU AND GRUACT.X_EMPLEADO = :idEmpleado AND GRUACT.C_ANNO = :anno AND GRUACT.X_CENTRO = :codigoCentro " +
			"INNER JOIN EVA_PROGDIDAC PROGDIDAC ON PROGDIDAC.ID_PROGDIDAC = :idDidac AND PROGDIDAC.X_MATERIAOMG = UAG.X_MATERIAOMG AND PROGDIDAC.NU_ANNO = GRUACT.C_ANNO " +
			"INNER JOIN TLUNIDADESCEN UNICEN ON UNICEN.X_UNIDAD = UAG.X_UNIDAD " +
			"ORDER BY unicen.T_NOMBRE ASC", nativeQuery = true)
    List<UnidadesPorMateriaProjection> findUnidadesByProgramacionDidactica(@Param("idEmpleado") Long idEmpleado, @Param("idDidac") Long idDidac, @Param("codigoCentro") Long codigoCentro, @Param("anno") Long anno);
    
}
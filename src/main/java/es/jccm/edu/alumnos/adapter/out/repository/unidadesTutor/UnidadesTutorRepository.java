package es.jccm.edu.alumnos.adapter.out.repository.unidadesTutor;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.alumnos.application.domain.unidadesTutor.QUnidadesTutor;
import es.jccm.edu.alumnos.application.domain.unidadesTutor.UnidadesTutor;
import es.jccm.edu.alumnos.application.domain.unidadesTutor.projection.UnidadesTutorProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface UnidadesTutorRepository extends AbstractRepository<UnidadesTutor, Integer, QUnidadesTutor> {

	@Query(value = "SELECT uni.X_UNIDAD idUnidad, uni.T_NOMBRE nombreUnidad, PTO.F_TOMAPOS fechaTomaPosesion, PTO.F_CESE fechaCese, 'N' sustituye, CEN.C_CODIGO codigoCentro "
			+ "FROM TLUNIDADESCEN UNI, DELPHOS_SEGEDU.TLPTOTRAEMP PTO, TLCENTROS CEN  "
			+ "WHERE UNI.X_EMPLEADO = PTO.X_EMPLEADO "
			+ "AND UNI.F_TOMAPOS = TRUNC(PTO.F_TOMAPOS) "
			+ "AND PTO.X_EMPLEADO IN (:idEmpleados) "
			+ "AND CEN.X_CENTRO = UNI.X_CENTRO "
			+ "AND C_ANNO = :anno "
			+ "AND TLF_INTERSECPER(PTO.F_TOMAPOSREA, F_CESE, SYSDATE, SYSDATE) = 1 "
			+ "UNION ALL "
			+ "SELECT uni.X_UNIDAD idUnidad, uni.T_NOMBRE nombreUnidad, PTO.F_TOMAPOS_SUSTITUYE fechaTomaPosesion, PTO.F_CESE fechaCese, 'S' sustituye, CEN.C_CODIGO codigoCentro   "
			+ "FROM TLUNIDADESCEN UNI, DELPHOS_SEGEDU.TLPTOTRAEMP PTO, TLCENTROS CEN "
			+ "WHERE pto.X_EMPLEADO_SUSTITUYE IN (:idEmpleados) "
			+ "AND UNI.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE "
			+ "AND CEN.X_CENTRO = UNI.X_CENTRO "
			+ "AND UNI.F_TOMAPOS = TRUNC(PTO.F_TOMAPOS_SUSTITUYE) "
			+ "AND TLF_INTERSECPER(PTO.F_TOMAPOSREA, F_CESE, SYSDATE, SYSDATE) = 1 "
			+ "AND C_ANNO = :anno", nativeQuery = true)
	List<UnidadesTutorProjection> tutoresByUnidad(@Param("idEmpleados") List<Long> idEmpleados, @Param("anno") Long anno);

}

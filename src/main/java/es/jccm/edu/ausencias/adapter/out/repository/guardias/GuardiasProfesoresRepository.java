package es.jccm.edu.ausencias.adapter.out.repository.guardias;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.ausencias.application.domain.guardias.projection.DatosProfesoresGuardiasProjection;
import es.jccm.edu.ausencias.application.domain.guardias.projection.GuardiasProfesoresProjection;
import es.jccm.edu.shared.adapter.out.jpamodel.QRootEntity;
import es.jccm.edu.shared.adapter.out.jpamodel.RootEntity;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface GuardiasProfesoresRepository extends AbstractRepository<RootEntity, Integer, QRootEntity>{
	
	@Modifying
	@Query(value= "SELECT HOR.X_TRAMO idTramo, HOR.N_DIASEMANA diaSemana, MIN(HOR.F_INICIO) fechaInicio,  MAX(HOR.F_FIN) fechaFin, convierte_hora(hor.n_horini) horaInicio, "
			+ "convierte_hora(hor.n_horfin) horaFin, COUNT(HOR.X_EMPLEADO) numEmpleados, HOR.N_HORINI "
			+ "FROM TLUSUARIOS USU, TLACTIVIDADES ACT, TLHORARIOSR HOR "
			+ "WHERE  HOR.X_EMPLEADO = USU.X_EMPLEADO "
			+ "AND HOR.X_ACTIVIDAD = ACT.X_ACTIVIDAD "
			+ "AND USU.L_ACTIVO = 'S' "
			+ "AND USU.L_BLOQUEADO = 'N' "
			+ "AND ACT.T_ABREVIATURA = 'GU' "
			+ "AND HOR.C_ANNO = :anno "
			+ "AND EXISTS (SELECT 1 FROM TLPTOTRAEMP PTE, TLPUEORIPER PUE, TLCENTROS CEN "
			+ "WHERE USU.X_USUARIO = PUE.X_USUARIO "
			+ "AND PTE.X_EMPLEADO = PUE.X_EMPLEADO "
			+ "AND PTE.F_TOMAPOS = PUE.F_TOMAPOS "
			+ "AND TRUNC(PTE.F_TOMAPOS) <= TRUNC(SYSDATE) "
			+ "AND NVL(TRUNC(PTE.F_CESE), TRUNC(SYSDATE)) >= TRUNC(SYSDATE) "
			+ "AND PTE.X_CENTRO = CEN.X_CENTRO "
			+ "AND CEN.C_CODIGO = :codCentro "
			+ "AND PUE.X_PERFIL = '2') "
			+ "GROUP BY HOR.X_TRAMO, HOR.N_DIASEMANA, HOR.N_HORINI, hor.n_horfin "
			+ "ORDER BY diaSemana, HOR.N_HORINI", nativeQuery = true)
	List<GuardiasProfesoresProjection> getGuardiasProfesores(@Param("codCentro") Long codCentro,  @Param("anno") Integer anno);
	
	@Modifying
	@Query(value= "SELECT USU.X_USUARIO idUsuario, USU.USUARIO oIdUsuario, tlf_nombreempleado(USU.X_EMPLEADO) nombre, EMP.telefono telefono, EMP.T_CORREO_E correo, hor.x_tramo, hor.f_inicio, hor.f_fin " + 
			"FROM TLEMPLEADOS EMP, TLPTOTRAEMP PTE, TLCENTROS CEN, TLUSUARIOS USU, tlhorariosr HOR, tlactividades ACT " + 
			"WHERE EMP.X_EMPLEADO = PTE.X_EMPLEADO  " + 
			"AND EMP.X_EMPLEADO = USU.X_EMPLEADO  " + 
			"AND PTE.X_CENTRO = cen.X_CENTRO  " + 
			"AND PTE.X_EMPLEADO = HOR.X_EMPLEADO  " + 
			"AND HOR.X_ACTIVIDAD = ACT.X_ACTIVIDAD  " + 
			"AND act.t_abreviatura = 'GU' " + 
			"AND hor.n_diasemana = :diaSemana " + 
			"AND hor.x_tramo = :idTramo " + 
			"AND CEN.C_CODIGO = :codCentro " + 
			"AND USU.L_ACTIVO = 'S' " + 
			"AND USU.L_BLOQUEADO = 'N' " + 
			"AND TRUNC(SYSDATE) BETWEEN PTE.F_TOMAPOS AND NVL(PTE.F_CESE,TRUNC(SYSDATE)) " + 
			"AND NOT EXISTS (SELECT 1 FROM TLAUSENCIAS AUS WHERE AUS.X_EMPLEADO = USU.X_EMPLEADO  " + 
			"AND TRUNC(SYSDATE) BETWEEN AUS.F_INICIO AND AUS.F_FINAL)", nativeQuery = true)
	List<DatosProfesoresGuardiasProjection> getDatosProfesoresGuardias(@Param("codCentro") Long codCentro, @Param("idTramo") Long idTramo, @Param("diaSemana") Integer diaSemana);
	
}
